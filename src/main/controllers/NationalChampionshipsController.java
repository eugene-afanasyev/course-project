package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.dao.DBChampionshipDAO;
import main.models.Champ;
import main.services.ChampionshipService;

public class NationalChampionshipsController {



    @FXML
    private TableView<Champ> championshipsTable;


    @FXML
    private TableColumn<Champ, Integer> numberColumn;

    @FXML
    private TableColumn<Champ, String> yearColumn;

    @FXML
    private TableColumn<Champ, String> fieldColumn;

    @FXML
    private TableColumn<Champ, Integer> countParticipantColumn;

    @FXML
    void initialize() {

        var champService = new ChampionshipService<>(DBChampionshipDAO::new);
        var championships = champService.findAll();

        ObservableList<Champ> champs = FXCollections.observableArrayList();
        for(var championship : championships) {
            champs.add(new Champ(1, championship.getDateTo().toString() , championship.getCountry() + "," + championship.getCity()
                    , 1));
        }

        numberColumn.setCellValueFactory(new PropertyValueFactory<Champ, Integer>("number"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Champ, String>("year"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Champ, String>("field"));
        countParticipantColumn.setCellValueFactory(new PropertyValueFactory<Champ, Integer>("countParticipant"));

       System.out.println(championshipsTable.getColumns());

        championshipsTable.getItems().addAll(champs);



    }
}
