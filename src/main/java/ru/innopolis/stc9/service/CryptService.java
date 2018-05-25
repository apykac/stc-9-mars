package ru.innopolis.stc9.service;

import org.mindrot.jbcrypt.BCrypt;

public class CryptService {
    private CryptService() {
    }

    public static String crypting(String pswrd) {
        return BCrypt.hashpw(pswrd, BCrypt.gensalt());
    }

    public static boolean isMatched(String pswrd, String hash) {
        return BCrypt.checkpw(pswrd, hash);
    }
}
