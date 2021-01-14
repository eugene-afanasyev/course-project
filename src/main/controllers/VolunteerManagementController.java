package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dao.DBDisciplineDAO;
import main.models.Voluunter;
import main.services.DisciplineService;

public class VolunteerManagementController {
    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> competenciesComboBox;

    @FXML
    private TableView<Voluunter> volunteerTableView;

    @FXML
    private TableColumn<Voluunter, Integer> idColumn;

    @FXML
    private TableColumn<Voluunter, String> nameColumn;

    @FXML
    private TableColumn<Voluunter, String> sexColumn;

    @FXML
    private TableColumn<Voluunter, String> regionColumn;

    @FXML
    private TableColumn<Voluunter, String> competenceColumn;

    @FXML
    void initialize(){
        var disciplineService = new DisciplineService<>(DBDisciplineDAO::new);
        var allDisciplines = disciplineService.findAll();

        for(var discipline: allDisciplines) {
            competenciesComboBox.getItems().add(discipline.getRuName());
        }
        competenciesComboBox.setValue(allDisciplines.get(0).getRuName());


        idColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("name"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("sex"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("region"));
        competenceColumn.setCellValueFactory(new PropertyValueFactory<Voluunter, String>("competence"));


        searchButton.setOnAction(actionEvent -> {

        });
    }

}
