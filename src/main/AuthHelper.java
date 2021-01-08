package main;

import main.Hasher;

public class AuthHelper {
    public static String hashPassword ( final String password){
        return new Hasher().hash(password.toCharArray());
    }

    public static boolean isPasswordValid(final String password, final String tokenFromBD){
        return new Hasher().checkPassword(password.toCharArray(), tokenFromBD);
    }
}
