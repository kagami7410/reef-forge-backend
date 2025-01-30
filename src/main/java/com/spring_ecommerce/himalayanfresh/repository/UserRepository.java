package com.spring_ecommerce.himalayanfresh.repository;

import com.spring_ecommerce.himalayanfresh.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);
        boolean existsByEmail(String email);
        }
