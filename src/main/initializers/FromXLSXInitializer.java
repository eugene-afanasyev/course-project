package main.initializers;

import main.dao.DBRoleDAO;
import main.dao.RoleDAO;
import main.models.*;
import main.parsers.*;
import main.services.RoleService;
import org.apache.poi.ss.formula.functions.T;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class FromXLSXInitializer implements Initializer {

    private static class RegionTableValue{
        public String value;
    }

    @Override
    public void initializeUsers ( Object arg ) {
        String path = (String)arg;

    }


    @Override
    public void initializeRegions ( Object arg ) {
        String path = (String)arg;

        List<Region> regions = RolesParser.<Region>Parse((String)arg, (row) -> {
           Iterator<Cell> cells = row.cellIterator();
            cells.next();
            Cell cell = cells.next();

            var name = cell.getStringCellValue();
            cell = cells.next();

            var capital = cell.getStringCellValue();

            return new Region(name, capital);
        });

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
    public void initializeRoles ( Object arg ){

        List<Role> roles = RolesParser.<Role>Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            Cell cell = cells.next();

            return new Role(cells.next().getStringCellValue());
        });

        if(roles == null){
            throw new NullPointerException("No writable roles available");
        }

        RoleService<DBRoleDAO> roleService = new RoleService<DBRoleDAO>(DBRoleDAO::new);

        if(!roleService.findAll().isEmpty()) {
            //throw new Exception("already initialized");
            return;
        }
        roles.remove(0);
        for (Role role: roles) {
            roleService.save(role);
        }
    }
}
