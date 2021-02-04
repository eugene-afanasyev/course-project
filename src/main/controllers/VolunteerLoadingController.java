package main.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.AuthHelper;
import main.AuthManager;
import main.dao.DBRegionDAO;
import main.dao.DBRoleDAO;
import main.dao.DBUserDAO;
import main.models.Discipline;
import main.models.Region;
import main.models.Role;
import main.models.User;
import main.parsers.XLSParser;
import main.parsers.XLSXParser;
import main.services.RegionService;
import main.services.RoleService;
import main.services.UserService;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class VolunteerLoadingController {

    @FXML
    private Button fileOpenButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button cancelButton;

    @FXML
    private VBox afterLoadingInfoArea;

    @FXML
    private Label total;

    @FXML
    private Label newWritesCount;

    @FXML
    private Label rewritesCount;

    @FXML
    private TextArea fileName;

    private final FileChooser fileChooser = new FileChooser();
    private final UserService<DBUserDAO> userService = new UserService<>(DBUserDAO::new);
    private final RoleService<DBRoleDAO> roleService = new RoleService<>(DBRoleDAO::new);
    private final RegionService<DBRegionDAO> regionService = new RegionService<>(DBRegionDAO::new);
//
//    private final Executor exec = Executors.newCachedThreadPool(runnable -> {
//        Thread t = new Thread(runnable);
//        t.setDaemon(true);
//        return t ;
//    });

    private int rewriteCount = 0;
    private int totalCount = 0;
    private int newCount = 0;
    @FXML
    public void initialize(){
        SignedUserHeaderController.viewPath = "/Views/volunteerManagement.fxml";

        fileOpenButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileName.clear();
                var file = fileChooser.showOpenDialog(afterLoadingInfoArea.getScene().getWindow());

                if(file != null){
                    fileName.setText(file.getAbsolutePath());
                }
            }
        });


        loadButton.setOnMouseClicked((event) -> {
            afterLoadingInfoArea.setVisible(false);


            var parseTask = new Task<List<User>>(){
                @Override
                protected List<User> call ( ) throws Exception {

                    rewriteCount = 0;
                    newCount = 0;

                    if(fileName.getText().isEmpty()){
                        return null;
                    }
                    List<User> volunteersList = new XLSParser().Parse(fileName.getText(), ( row) -> {
                        Iterator<Cell> cells = row.cellIterator();
                        int id = 0;
                        try{
                            id = (int)cells.next().getNumericCellValue();
                        }
                        catch (Exception e){
                            return null;
                        }

                        var firstName = cells.next().getStringCellValue();
                        var secondName = cells.next().getStringCellValue();
                        var regionCode = (int)cells.next().getNumericCellValue();
                        var isMale= cells.next().getStringCellValue().equals("M");

                        var user = userService.find((u) ->
                                u.getRegion().getId() == regionCode &&
                                        u.isMale() == isMale &&
                                        u.getFirstName().equals(firstName) &&
                                        u.getLastName().equals(secondName)
                        );

                        var volunteerRole = roleService.findByName("Volunteer");
                        var userRegion = regionService.find(regionCode);
                        if(user == null){
                            user = new User(firstName, isMale, secondName, new Date(),
                                    null,
                                    AuthHelper.hashPassword("123456"),
                                    null,
                                    "val@email.com",
                                    null,
                                    null,
                                    null,
                                    null);

                            userService.save(user);

                            newCount++;

                            userService.updateChampionship(user, AuthManager.Current.getUser().getChampionship());
                            userService.updateRole(user, volunteerRole);
                            userService.updateRegion(user, userRegion);
                        }
                        else{
                            userService.updateRole(user, volunteerRole);
                            userService.updateChampionship(user, AuthManager.Current.getUser().getChampionship());
                            userService.updateDiscipline(user, null);

                            rewriteCount++;
                        }
                        return user;
                    });

                    return volunteersList;
                }
            };

            parseTask.setOnSucceeded((ev) -> {
                afterLoadingInfoArea.setVisible(true);
                total.setText(String.format("%d",newCount + rewriteCount));
                newWritesCount.setText(String.format("%d",newCount));
                rewritesCount.setText(String.format("%d", rewriteCount));
            });

            parseTask.setOnFailed((ev) -> {
                afterLoadingInfoArea.setVisible(true);
                total.setText(String.format("%d",newCount + rewriteCount));
                newWritesCount.setText(String.format("%d",newCount));
                rewritesCount.setText(String.format("%d", rewriteCount));
            });

            Executor exec = Executors.newCachedThreadPool(runnable -> {
                        Thread t = new Thread(runnable);
                        t.setDaemon(true);
                        return t ;
            });

            exec.execute(parseTask);
        });
    }
}