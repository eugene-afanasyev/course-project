package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.AuthManager;
import main.dao.DBChampionshipDAO;
import main.dao.DBDisciplineDAO;
import main.services.ChampionshipService;
import main.services.DisciplineService;
import tableModels.VoluunterCompetence;

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
    public void initialize() {
        checkColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, CheckBox>("check"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("age"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("sex"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<VoluunterCompetence, String>("region"));


        var disciplineService = new DisciplineService<>(DBDisciplineDAO::new);
        var service = new ChampionshipService<DBChampionshipDAO>(DBChampionshipDAO::new);

        var allDisciplines = disciplineService.findAll();

        for(var discipline: allDisciplines) {
            competenciesComboBox.getItems().add(discipline.getRuName());
        }
        competenciesComboBox.setValue(allDisciplines.get(0).getRuName());

        searchButton.setOnAction(actionEvent -> {
            var currentDiscipline = disciplineService.findByName(competenciesComboBox.getValue());

            var currentUser = AuthManager.Current.getUser();

            var volunteers = service.findAllByRole("Volunteer",
                    currentUser.getChampionship(), currentDiscipline);

            for(var voluunter : volunteers) {
                var sex = voluunter.isMale()? "male": "female";
                voluunterTable.getItems().add(new VoluunterCompetence(
                        voluunter.getFirstName(), voluunter.getBirthdayDate().toString(), sex, voluunter.getRegion().getName()));
            }
        });

    }
}
