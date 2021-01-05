package main.initializers;

import main.dao.DBRegionDAO;
import main.dao.DBRoleDAO;
import main.models.*;
import main.parsers.*;
import main.services.EntityService;
import main.services.RegionService;
import main.services.RoleService;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Iterator;
import java.util.List;

public class FromXLSInitializer implements Initializer {

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

        List<Region> regions = XLSParser.<Region>Parse((String)arg, ( row) -> {
           Iterator<Cell> cells = row.cellIterator();
            cells.next();
            Cell cell = cells.next();
            var builder = new RegionBuilder();

            return builder.WithName(cell.getStringCellValue()).WithCapital(cells.next().getStringCellValue()).Build();
        });

        RegionService<DBRegionDAO> regionService = new RegionService<>(DBRegionDAO::new);

        regions.remove(0);

        try
        {
            SaveByUsingService(regionService, regions);
        }
        catch (ExceptionInInitializerError ex){

        }
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
        List<Role> roles = XLSParser.<Role>Parse((String)arg, ( row) -> {
            Iterator<Cell> cells = row.cellIterator();
            Cell cell = cells.next();

            return new Role(cells.next().getStringCellValue());
        });

        if(roles == null){
            throw new NullPointerException("No writable roles available");
        }

        RoleService<DBRoleDAO> roleService = new RoleService<DBRoleDAO>(DBRoleDAO::new);

        roles.remove(0);

        try {
            SaveByUsingService(roleService, roles);
        }
        catch (ExceptionInInitializerError ex){

        }
    }


    private <T> void SaveByUsingService( EntityService<T> service, List<T> items) throws ExceptionInInitializerError{
        if(!service.findAll().isEmpty()){
            throw new ExceptionInInitializerError("Already initialized");
        }

        for (var item : items){
            try{
                service.save(item);
            }
            catch (Exception ex){
                var text = ex.getMessage();
            }
        }
    }
}
