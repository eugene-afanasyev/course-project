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
import main.dao.DBUserDAO;
import main.models.Discipline;
import main.models.Role;
import main.models.User;
import main.parsers.XLSParser;
import main.parsers.XLSXParser;
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
    private final Executor exec = Executors.newCachedThreadPool(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t ;
    });

    @FXML
    public void initialize(){

        var parseTask = new Task<List<User>>(){
            @Override
            protected List<User> call ( ) throws Exception {
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

                    return new User(firstName,
                            isMale,
                            secondName,
                            new Date(),
                            null,
                            AuthHelper.hashPassword("123456"),
                            null,
                            null,
                            null,
                            null);
                });

                return volunteersList;
            }
        };

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

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle ( ActionEvent actionEvent ) {
                exec.execute(parseTask);
            }
        });
    }
}