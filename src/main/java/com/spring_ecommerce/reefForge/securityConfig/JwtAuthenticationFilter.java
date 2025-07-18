package com.spring_ecommerce.reefForge.securityConfig;

import com.spring_ecommerce.reefForge.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Service
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;
    Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        logger.info("Initializing doFilterInternal");

//        if (request.getServletPath().contains("/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        final String authHeader = request.getHeader("Authorization");
         String jwt = null;
         String email = null;



        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            email = jwtService.extractUsername(jwt);

        } else if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    email = jwtService.extractUsername(jwt);
                    System.out.println("token: " + jwt);
                    System.out.println("email: " + email);

                    break;
                }
            }
        }

//        if (authHeader == null || !authHeader.startsWith("Bearer")){
//            filterChain.doFilter(request, response);
//            return;
//        }

        // If still no JWT, continue filter chain
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }



        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            logger.info("User Details: " + userDetails.getUsername());


            if (jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails ,
                        null,
                        userDetails.getAuthorities()
                );
                logger.info("Setting details");
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
