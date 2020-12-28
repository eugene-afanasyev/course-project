package main.dao;

import main.models.User;

import java.util.List;

public interface DataAccessObject<T> {
    T findById ( int id);
    void save(T arg);
    void remove(T arg);
    void update(T arg);
    List<T> findAll();
}
