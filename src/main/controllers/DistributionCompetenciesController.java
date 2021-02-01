package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.AuthManager;
import main.dao.DBChampionshipDAO;
import main.dao.DBDisciplineDAO;
import main.models.Discipline;
import main.models.User;
import main.services.ChampionshipService;
import main.services.DisciplineService;
import tableModels.VoluunterCompetence;

import java.util.List;

public class DistributionCompetenciesController {
    @FXML
    private ComboBox<String> competenciesComboBox;

    @FXML
    private TableView<VoluunterCompetence> voluunterTable;

    @FXML
    private TableColumn<VoluunterCompetence, CheckBox> checkColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> nameColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> sexColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> ageColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> regionColumn;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> secondCompetenciesComboBox;

    @FXML
    private Button secondSearchButton;

    @FXML
    private TableView<VoluunterCompetence> secondVoluunterTable;

    @FXML
    private TableColumn<VoluunterCompetence, CheckBox> secondCheckColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> secondNameColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> secondSexColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> secondAgeColumn;

    @FXML
    private TableColumn<VoluunterCompetence, String> secondRegionColumn;

    void fillComboBox(ComboBox<String> comboBox, List<Discipline> disciplines) {
        for(var discipline: disciplines) {
            comboBox.getItems().add(discipline.getName());
        }
        comboBox.setValue(disciplines.get(0).getName());
    }

    void fillTable(TableView<VoluunterCompetence> tableView, List<User> volunteers) {
        for(var voluunter : volunteers) {
            var sex = voluunter.isMale()? "male": "female";
            tableView.getItems().add(new VoluunterCompetence(
                    voluunter.getFirstName(), voluunter.getBirthdayDate().toString(), sex, voluunter.getRegion().getName()));
        }
    }

    @FXML
    public void initialize() {
        checkColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, CheckBox>("check"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("age"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("sex"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("region"));


        var disciplineService = new DisciplineService<>(DBDisciplineDAO::new);
        var service = new ChampionshipService<DBChampionshipDAO>(DBChampionshipDAO::new);

        var allDisciplines = disciplineService.findAll();

        fillComboBox(competenciesComboBox, allDisciplines);
        fillComboBox(secondCompetenciesComboBox, allDisciplines);
//
//        for(var discipline: allDisciplines) {
//            competenciesComboBox.getItems().add(discipline.getName());
//        }
//        competenciesComboBox.setValue(allDisciplines.get(0).getName());

        searchButton.setOnAction(actionEvent -> {
            var currentDiscipline = disciplineService.findByName(competenciesComboBox.getValue());
            var currentUser = AuthManager.Current.getUser();
            var volunteers = service.findAllByRole("Volunteer",
                    currentUser.getChampionship(), currentDiscipline);

            fillTable(voluunterTable , volunteers);
//            for(var voluunter : volunteers) {
//                var sex = voluunter.isMale()? "male": "female";
//                voluunterTable.getItems().add(new VoluunterCompetence(
//                        voluunter.getFirstName(), voluunter.getBirthdayDate().toString(), sex, voluunter.getRegion().getName()));
//            }
        });

        secondSearchButton.setOnAction(actionEvent -> {
            var currentDiscipline = disciplineService.findByName(competenciesComboBox.getValue());
            var currentUser = AuthManager.Current.getUser();
            var volunteers = service.findAllByRole("Volunteer",
                    currentUser.getChampionship(), currentDiscipline);

            fillTable(secondVoluunterTable , volunteers);
        });

    }
}
