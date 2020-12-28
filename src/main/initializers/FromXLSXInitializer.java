package main.initializers;

import main.dao.DBRoleDAO;
import main.dao.RoleDAO;
import main.models.*;
import main.parsers.*;
import main.services.RoleService;

import java.util.Enumeration;
import java.util.List;

public class FromXLSXInitializer implements Initializer {

    @Override
    public void initializeUsers ( Object arg ) {
        String path = (String)arg;

    }

    @Override
    public void initializeRegions ( Object arg ) {
        String path = (String)arg;

    }

    @Override
    public void initializeResults ( Object arg ) {
        String path = (String)arg;

    }

    @Override
    public void initializeDisciplines ( Object arg ) {
        String path = (String)arg;

    }

    @Override
    public void initializeChampionships ( Object arg ) {
        String path = (String)arg;

    }

    @Override
    public void initializeRoles ( Object arg ) {
        List<Role> roles = (List<Role>) RolesParser.Parse((String) arg);
        if(roles == null){
            throw new NullPointerException("No writable roles available");
        }

        RoleService<DBRoleDAO> roleService = new RoleService<DBRoleDAO>(DBRoleDAO::new);

        for (Role role: roles) {
            roleService.save(role);
        }
    }
}
