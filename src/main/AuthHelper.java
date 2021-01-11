package main;

import main.Hasher;

public class AuthHelper {
    public static String hashPassword ( final String password){
        return new Hasher().hash(password.toCharArray());
    }

    public static boolean isPasswordsEqual ( final String password, final String tokenFromBD){
        return new Hasher().checkPassword(password.toCharArray(), tokenFromBD);
    }

    public static boolean isPasswordValid(final String password){

        if(password.isEmpty() || password.isBlank()){
            return false;
        }

        return true;
    }

    public static boolean isLoginValid(final String login){

        if(login.isEmpty() || login.isBlank()){
            return false;
        }

        return true;
    }

}
