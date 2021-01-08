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
        if(AuthHelper.isPasswordValid(password, user.getPassword())){

        }
        
        return false;
    }

    private boolean isAuthorized;
    private Role userRole;
    private User user;

    public boolean isAuthorized ( ) {
        return isAuthorized;
    }

    public void setAuthorized ( boolean authorized ) {
        isAuthorized = authorized;
    }

    public Role getUserRole ( ) {
        return userRole;
    }

    public void setUserRole ( Role userRole ) {
        this.userRole = userRole;
    }

    public User getUser ( ) {
        return user;
    }

    public void setUser ( User user ) {
        this.user = user;
    }
}
