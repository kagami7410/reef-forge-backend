package com.spring_ecommerce.reefForge.security;

import java.security.SecureRandom;

public class RandomIdGenerator {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int length) {
        return RANDOM.ints(length, 0, CHARS.length())
                .mapToObj(CHARS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static void main(String[] args) {
        System.out.println(generate(8));  // e.g. "4Gt9Lp2Q"
    }
}