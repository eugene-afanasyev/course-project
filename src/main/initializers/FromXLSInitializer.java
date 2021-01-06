package main.initializers;

import main.dao.DBChampionshipDAO;
import main.dao.DBDisciplineDAO;
import main.dao.DBRegionDAO;
import main.dao.DBRoleDAO;
import main.models.*;
import main.parsers.*;
import main.services.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FromXLSInitializer implements Initializer {

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
        List<Discipline> disciplines = XLSParser.<Discipline>Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            Cell cell = cells.next();
            var builder = new DisciplineBuilder();

            return builder.withName(cell.getStringCellValue()).withDescription(cells.next().getStringCellValue()).Build();
        });

        DisciplineService<DBDisciplineDAO> disciplineService = new DisciplineService<DBDisciplineDAO>(DBDisciplineDAO::new);

        try {
            SaveByUsingService(disciplineService, disciplines);
        }
        catch (ExceptionInInitializerError ex){

        }

    }

    @Override
    public void initializeChampionships ( Object arg ) {
        List<Championship> championships = XLSParser.Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            cells.next();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

            var name = cells.next().getStringCellValue();
            var dateStr = cells.next().getStringCellValue();

            String[] dates;

            if(dateStr.contains("-")){
                dates = dateStr.split("-");
            }
            else{
                dates = new String[]{dateStr};
            }
            Date dateFrom;
            Date dateTo;

            if(dates.length == 1){
                try {
                    dateTo = dateFrom = formatter.parse(dates[0]);
                }
                catch(ParseException ex){
                    dateTo = dateFrom = new Date(1, Calendar.JANUARY, 1);
                }
            }
            else{
                try {
                    dateFrom = formatter.parse(dates[0]);
                    dateTo = formatter.parse(dates[1]);
                }
                catch (ParseException ex){
                    dateTo = dateFrom = new Date(1, Calendar.JANUARY, 1);
                }
            }

            String address = "";
            String link = "";
            var city = cells.next().getStringCellValue();
            var country = cells.next().getStringCellValue();
           /* if(nextCell != null){
                link = nextCell.getStringCellValue();
            }
            nextCell = cells.next();
            if(nextCell != null && nextCell.getCellType() != Cell.CELL_TYPE_BLANK){
                address = nextCell.getStringCellValue();
            }*/
            try{
                link = cells.next().getStringCellValue();
                address = cells.next().getStringCellValue();
            }
            catch (Exception e){

            }

            return new Championship(name, dateFrom, dateTo, city, country, address);
        });

        championships.remove(0);

        ChampionshipService<DBChampionshipDAO> championshipService = new ChampionshipService<>(DBChampionshipDAO::new);

        try {
            SaveByUsingService(championshipService, championships);
        }
        catch (ExceptionInInitializerError ex){

        }
    }

    @Override
    public void initializeRoles ( Object arg ){
        List<Role> roles = XLSParser.<Role>Parse((String)arg, ( row) -> {
            Iterator<Cell> cells = row.cellIterator();
            Cell cell = cells.next();

            return new Role(cells.next().getStringCellValue());
        });

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

    public static boolean isCellEmpty(final Cell cell) {
        if (cell == null) { // use row.getCell(x, Row.CREATE_NULL_AS_BLANK) to avoid null cells
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().isEmpty()) {
            return true;
        }

        return false;
    }
}