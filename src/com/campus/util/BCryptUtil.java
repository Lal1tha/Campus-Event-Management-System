package com.campus.util;

import org.mindrot.jbcrypt.BCrypt;

public final class BCryptUtil {
    private BCryptUtil() {
    }

    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean verify(String plainPassword, String hash) {
        return plainPassword != null && hash != null && BCrypt.checkpw(plainPassword, hash);
    }
}
