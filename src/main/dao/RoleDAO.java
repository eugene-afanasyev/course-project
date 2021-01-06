package main.dao;

import main.models.Role;
import main.models.User;

import java.util.List;

public interface RoleDAO extends DataAccessObject<Role> {
    Role findByName(String name);
}
