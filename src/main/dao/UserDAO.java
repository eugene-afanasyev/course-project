package main.dao;

import main.models.User;

import java.util.Enumeration;
import java.util.List;

public interface UserDAO extends DataAccessObject<User>{
    void updateLogin(int id, String login);
    User findByLogin(String login);
    void updatePassword(int id, String password);
    List<User> findByName(String firstName, String lastName);
}
