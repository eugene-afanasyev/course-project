package main.initializers;

import main.models.*;
import main.parsers.*;

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
        String path = (String)arg;

        List<Role> roles = (List<Role>) RolesParser.Parse(path);
        if(roles == null){
            throw new NullPointerException("No writable roles available");
        }

        for (Role role: roles) {

        }
    }
}
