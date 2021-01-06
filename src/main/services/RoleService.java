package main.services;

import main.dao.RoleDAO;
import main.models.Role;
import main.models.User;

import java.util.List;
import java.util.function.Supplier;

public class RoleService<T extends RoleDAO> implements EntityService<Role>{
    private final Supplier<T> supplier;

    public RoleService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Role find( int id) {
        return supplier.get().findById(id);
    }

    public Role findByName(String name){
        return supplier.get().findByName(name);
    };

    @Override
    public void save(Role role) {
        supplier.get().save(role);
    }

    @Override
    public void delete( Role role) {
        supplier.get().remove(role);
    }

    @Override
    public void update(Role role) {
        supplier.get().update(role);
    }

    @Override
    public List<Role> findAll() {
        return supplier.get().findAll();
    }
}
