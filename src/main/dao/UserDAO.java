package main.dao;

import main.models.User;

import java.util.Enumeration;

public interface UserDAO {
    User findById ( int id);
    void save(User user);
    void remove(User user);
    void update(User user);
    Enumeration<User> findAll();
}
