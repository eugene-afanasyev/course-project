package main.services;

import main.dao.UserDAO;
import main.models.User;

import java.util.Enumeration;
import java.util.function.Supplier;

public class UserService<T extends UserDAO> {
    private final Supplier<T> supplier;

    public UserService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public User findUser( int id) {
        return supplier.get().findById(id);
    }

    public void saveUser(User user) {
        supplier.get().save(user);
    }

    public void deleteUser(User user) {
        supplier.get().remove(user);
    }

    public void updateUser(User user) {
        supplier.get().update(user);
    }

    public Enumeration<User> findAllUsers() {
        return supplier.get().findAll();
    }
}
