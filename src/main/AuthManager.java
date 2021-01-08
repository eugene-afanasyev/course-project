package main;

import main.dao.DBUserDAO;
import main.models.Role;
import main.models.User;
import main.services.UserService;

public class AuthManager {
    public static AuthManager Current = new AuthManager();

    private final UserService<DBUserDAO> userService = new UserService<>(DBUserDAO::new);

    private AuthManager()
    {

    }


    /*
    *
    * Проводит попытку авторизации пользователя
    * В случае успеха возвращает true, неуспеха - false;
    * */
    public boolean authorize(String login, String password){
        var user = userService.findByLogin(login);

        if(user == null || !AuthHelper.isPasswordsEqual(password, user.getPassword())){
            return false;
        }

        this.isAuthorized = true;
        this.userRole = user.getRole();
        this.user = user;

        return true;
    }

    private boolean isAuthorized = false;
    private Role userRole = null;
    private User user = null;

    public boolean isAuthorized ( ) {
        return isAuthorized;
    }

    public Role getUserRole ( ) {
        return userRole;
    }

    public User getUser ( ) {
        return user;
    }
}
