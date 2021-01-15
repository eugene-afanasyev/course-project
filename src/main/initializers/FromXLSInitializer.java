package main.initializers;

import main.AuthHelper;
import main.dao.*;
import main.models.*;
import main.parsers.*;
import main.services.*;

import org.apache.poi.ss.usermodel.Cell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class FromXLSInitializer implements Initializer {

    private final UserService<DBUserDAO> userService = new UserService<>(DBUserDAO::new);
    public void initializeVolunteers(){

    }

    @Override
    public void initializeUsers ( Object arg ) {

        List<User> users = XLSParser.Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            cells.next();
            Cell cell = cells.next();

                var email = cell.getStringCellValue();
                var password = cells.next().getStringCellValue();
                var firstName = cells.next().getStringCellValue();
                var lastName = cells.next().getStringCellValue();
                var roleStr = cells.next().getStringCellValue();
                String role;
                var isMale = cells.next().getStringCellValue().contains("Male");
                Date birthday = new Date(1, Calendar.JANUARY, 1);
                int region_id = 0;

                try{
                    birthday = cells.next().getDateCellValue();
                    region_id = (int)cells.next().getNumericCellValue();
                }
                catch (Exception ex){

                }

            role = switch (roleStr) {
                case "E" -> "Expert";
                case "P" -> "Press";
                case "V" -> "Volunteer";
                case "A" -> "Administrator";
                case "O" -> "Coordinator";
                default -> "Competitor";
            };

            var roleService = new RoleService<DBRoleDAO>(DBRoleDAO::new);
            var currentRole = roleService.findByName(role);

            var regionService = new RegionService<DBRegionDAO>(DBRegionDAO::new);

            var offset = regionService.findAll().get(0).getId();
            var currentRegion = regionService.find(region_id + offset - 1);


            password = AuthHelper.hashPassword(password);

            return new User(firstName, isMale, lastName, birthday, null, password, email, email, currentRole, currentRegion);
        });

        var userService = new UserService<DBUserDAO>(DBUserDAO::new);

        users.remove(0);

        try{
            SaveByUsingService(userService, users);
        }catch (ExceptionInInitializerError ex){

        }
    }


    @Override
    public void initializeRegions ( Object arg ) {
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

    /**
     * Инициализирует таблицу результатов пользователей,
     * таблицу дисциплин и таблицу со связями чемпионатов и дисциплин, чемпионатов и пользователей
     * @param arg путь до xls-файла
     */
    @Override
    public void initializeResults ( Object arg ) {
        String path = (String)arg;

        List<Result> results = XLSParser.Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();

            var disciplineService = new DisciplineService<DBDisciplineDAO>(DBDisciplineDAO::new);
            var championshipService = new ChampionshipService<DBChampionshipDAO>(DBChampionshipDAO::new);
            var userService = new UserService<DBUserDAO>(DBUserDAO::new);

            var cell = cells.next();
            if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                return null;
            }

            var userId = (int)cell.getNumericCellValue();

            // так как пользователи в таблице идут не с 1 id, то необходимо вычислить смещение для того, чтобы правильно найти пользователя

            var userOffset = 266;
            //var userOffset = userService.findAll().get(0).getId();
            User user = null;
            try {
                user = userService.find(userId + userOffset - 1);
            }
            catch (Exception e){
                var msg = e.getMessage();
            }
            var nextCell = cells.next();
            String compCode;
            try{
                compCode = nextCell.getStringCellValue();
            }
            catch (Exception ex){
                compCode = String.format("%d", (int)nextCell.getNumericCellValue());
            }
            var compName = cells.next().getStringCellValue();

            // так как у нас нет достаточных данных для связи чемпионата, пользователя и результата, то просто рандомно выбираем id чемпионата
            var champCode = (int)cells.next().getNumericCellValue();
            var champCount = championshipService.findAll().size();
            var minChampId = championshipService.findAll().get(0).id;

            var champ_id = (int)(Math.random() * champCount) + minChampId;
            var champ = championshipService.find(champ_id);

            var userMark = cells.next().getNumericCellValue();
            nextCell = cells.next();
            String userModules;
            try {
                userModules = nextCell.getStringCellValue();
            } catch(Exception ex){
                userModules = String.format("%f", nextCell.getNumericCellValue());
            }

            var discipline = disciplineService.findByName(compName);

            var clientGroupCode = user.getRole().getName().substring(0,1);
            if(user.getRole().getName().equals("Coordinator")){
                clientGroupCode = "O";
            }

            if(discipline == null){
                discipline = new Discipline(compName,null, null, compCode);
                disciplineService.save(discipline);
            }

            championshipService.addUser(champ, user);

            // в остальные иницилизаторах нет достаточных данных для установка логина пользователя, поэтому делаем это тут
            var login = String.format("%tY%3s%08d%1s", user.getBirthdayDate(),compCode, user.getId(), clientGroupCode).replace(" ", "0");

            userService.updateLogin(user.getId(), login);
            championshipService.addDiscipline(champ, discipline);

            System.out.printf("\n code = %s || login = %s || modules %s || \r\n", compCode, login, userModules);


            var resultService = new ResultService<>(DBResultDAO::new);
            var result = new Result(user, champ, discipline, userMark, userModules);
            resultService.save(result);

            return result;
        });

        results.remove(0);

        var resultService = new ResultService<>(DBResultDAO::new);
        try {
         //   SaveByUsingService(resultService, results);
        }
        catch (ExceptionInInitializerError ex){

        }
    }

    @Override
    public void initializeDisciplines ( Object arg ) {
        List<Discipline> disciplines = XLSParser.<Discipline>Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();

            try {
                var ruName = cells.next().getStringCellValue();
                var ruDescription = cells.next().getStringCellValue();
                var enName = cells.next().getStringCellValue();
                var enDescription = cells.next().getStringCellValue();

                var numberPattern = Pattern.compile("[0-9]+");
                //var icode = enName.substring(numberPattern.matcher(enName).start(), numberPattern.matcher(enName).end());

                var codeLetter = enName.toCharArray()[0];
                var code = String.format("%d" , Integer.parseInt(enName.split("\\D+")[1]));
                if (!enName.contains("WSI")) {
                    code = codeLetter + code;
                }


                var disciplineService = new DisciplineService<DBDisciplineDAO>(DBDisciplineDAO::new);
                var discipline = disciplineService.findByCode(code);
                var result = enName.replaceAll("[0-9]" , "");

                if (result.startsWith("WSI ")) {
                    result = result.replaceFirst("WSI " , "");
                } else if (result.startsWith("R ")) {
                    result = result.replaceFirst("R" , "");
                } else if (result.startsWith("D ")) {
                    result = result.replaceFirst("D" , "");
                }

                if (discipline == null) {
                    disciplineService.save(new Discipline(result , ruDescription , code , ruName));
                } else {
                    disciplineService.changeRuName(discipline , ruName);
                    disciplineService.changeDescription(discipline , ruDescription);
                }
            }
            catch (Exception ex){

            }

            return null;
        });

        DisciplineService<DBDisciplineDAO> disciplineService = new DisciplineService<DBDisciplineDAO>(DBDisciplineDAO::new);

        try {
           // SaveByUsingService(disciplineService, disciplines);
        }
        catch (ExceptionInInitializerError ex){

        }

    }

    @Override
    public void initializeChampionships ( Object arg ) {
        List<Championship> championships = XLSParser.Parse((String)arg, (row) -> {
            Iterator<Cell> cells = row.cellIterator();
            int orderNumber = -1;
            try {
                orderNumber = Integer.parseInt(cells.next().getStringCellValue().replace(".", ""));
            }
            catch (Exception e){
                return null;
            }
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

            try{
                link = cells.next().getStringCellValue();
                address = cells.next().getStringCellValue();
            }
            catch (Exception e){

            }

            return new Championship(name, dateFrom, dateTo, city, country, address, orderNumber);
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
