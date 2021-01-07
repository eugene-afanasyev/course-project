package main.dao;

import main.models.User;

import java.util.Enumeration;
import java.util.List;

public interface UserDAO extends DataAccessObject<User>{
    void updateLogin(int id, String login);
}
