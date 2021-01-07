package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.dao.DBChampionshipDAO;
import main.models.Champ;
import main.services.ChampionshipService;

public class NationalChampionshipsController {
    @FXML
    private TextField searchByNumberField;

    @FXML
    private TextField searchByCountryOrSityField;

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

    Champ SearchByNumber(ObservableList<Champ> list, Integer number) {
        for(var el : list) {
            if(el.getNumber() == number)
                return el;
        }
        return null;
    }

    Champ SearchByCountryOrCity(ObservableList<Champ> list, String field) {
        for(var el : list) {
            System.out.println(el.country);
            if(el.city.substring(2, el.city.length()).equals(field) || el.country.equals(field))
                return el;
        }
        return null;
    }

    @FXML
    void initialize() {
        var champService = new ChampionshipService<>(DBChampionshipDAO::new);
        var championships = champService.findAll();

        ObservableList<Champ> champs = FXCollections.observableArrayList();
        for(var championship : championships) {
            champs.add(new Champ(championship.getOrderNumber(), championship.getName(),
                    championship.getFullAddress() , championship.getDateTo().toString().substring(0,4) ,
                    championship.getCountry(), championship.getCity(),
                    championship.getUsers().size()));
        }

        searchByNumberField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent t)  {
                if (t.getCode() == KeyCode.ENTER) {
                    var champ = SearchByNumber(champs, Integer.valueOf(searchByNumberField.getText()));
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    if(champ == null) {
                        info.setHeaderText("Такого чемпионата нет");
                    } else {
                        info = new Alert(Alert.AlertType.INFORMATION);
                        info.setHeaderText(champ.getName());
                        info.setContentText(champ.getDescription());
                    }
                    info.show();
                }
            }
        });

        searchByCountryOrSityField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent t)  {
                if (t.getCode() == KeyCode.ENTER) {
                    var champ = SearchByCountryOrCity(champs, searchByCountryOrSityField.getText());
                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    if(champ == null) {
                        info.setHeaderText("Такого чемпионата нет");
                    } else {
                        info = new Alert(Alert.AlertType.INFORMATION);
                        info.setHeaderText(champ.getName());
                        info.setContentText(champ.getDescription());
                    }
                    info.show();
                }
            }
        });

        numberColumn.setCellValueFactory(new PropertyValueFactory<Champ, Integer>("number"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Champ, String>("year"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Champ, String>("field"));
        countParticipantColumn.setCellValueFactory(new PropertyValueFactory<Champ, Integer>("countParticipant"));

        championshipsTable.getItems().addAll(champs);



    }
}
