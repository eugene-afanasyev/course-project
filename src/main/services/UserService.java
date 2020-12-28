package main.services;

import main.dao.DBUserDAO;
import main.dao.UserDAO;
import main.models.User;

import java.util.Enumeration;
import java.util.List;
import java.util.function.Supplier;

public class UserService<T extends UserDAO> implements EntityService<User> {
    private final Supplier<T> supplier;

    public UserService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public User find( int id) {
        return supplier.get().findById(id);
    }
    @Override
    public void save(User user) {
        var dao = supplier.get();

        supplier.get().save(user);
    }

    @Override
    public void delete(User user) {
        supplier.get().remove(user);
    }

    @Override
    public void update(User user) {
        supplier.get().update(user);
    }

    @Override
    public List<User> findAll() {
        return supplier.get().findAll();
    }
}
