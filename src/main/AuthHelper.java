package main;

import main.Hasher;

public class AuthHelper {
    public static String HashPassword(final String password){
        return new Hasher().hash(password.toCharArray());
    }
}
