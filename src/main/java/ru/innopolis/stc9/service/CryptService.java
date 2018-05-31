package ru.innopolis.stc9.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CryptService {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private CryptService() {
    }

    public static String crypting(String pswrd) {
        return passwordEncoder.encode(pswrd);
    }

    public static boolean isMatched(String pswrd, String hash) {
        return passwordEncoder.matches(pswrd, hash);
    }
}
