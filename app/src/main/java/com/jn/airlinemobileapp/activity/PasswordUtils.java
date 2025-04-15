package com.jn.airlinemobileapp.activity;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String passwordEncryption(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
