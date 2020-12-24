package main.dao;

import main.models.User;

import java.util.Enumeration;
import java.util.List;

public interface UserDAO {
    User findById ( int id);
    void save(User user);
    void remove(User user);
    void update(User user);
    List<User> findAll();
    // результаты на каком-то сорвеновании
    // результаты на всех соревнованиях
    // все соревнования
    //
}
