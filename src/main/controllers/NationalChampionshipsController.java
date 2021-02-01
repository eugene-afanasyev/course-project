package main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.dao.DBChampionshipDAO;
import main.models.Championship;
import main.services.ChampionshipService;
import tableModels.Champ;

import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private AnchorPane content;

    @FXML
    private Pane loadingArea;

    @FXML
    private ProgressIndicator spinner;

    private ChampionshipService<DBChampionshipDAO> champService = new ChampionshipService<>(DBChampionshipDAO::new);

//    private Executor exec = Executors.newCachedThreadPool(runnable -> {
//        Thread t = new Thread(runnable);
//        t.setDaemon(true);
//        return t ;
//    });

    Champ SearchByNumber(ObservableList<Champ> list, Integer number) {
        for(var el : list) {
            if(el.getNumber() == number)
                return el;
        }
        return null;
    }

    List<Champ> SearchByCountryOrCity(ObservableList<Champ> list, String field) {
        List<Champ> champs = new ArrayList<>();
        for(var el : list) {
            if(el.getCity().toLowerCase().contains(field.toLowerCase()) || el.getCountry().toLowerCase().contains(field.toLowerCase())) {
                champs.add(el);
            }
//            if(el.city.substring(2, el.city.length()).equals(field) || el.country.equals(field))
//                return el;
        }
        return champs;
    }

    public class ChampionshipExample extends Service<List<Championship>> {
        @Override
        protected Task<List<Championship>> createTask() {
            return new Task<List<Championship>>() {
                @Override
                protected List<Championship> call() throws Exception {
                    loadingArea.setVisible(true);
                    return champService.findAll();
                }
            };
        }
    }

    ChampionshipExample championshipsRequest = new ChampionshipExample();
    @FXML
    void initialize() {

        content.setVisible(false);

//        var championshipsRequest = new Task<List<Championship>>(){
//            @Override
//            protected List<Championship> call ( ) throws Exception {
//                loadingArea.setVisible(true);
//                return champService.findAll();
//            }
//        };

        spinner.progressProperty().unbind();
        spinner.progressProperty().bind(championshipsRequest.progressProperty());

        championshipsRequest.setOnFailed(event -> {
            loadingArea.setVisible(false);
        });
        championshipsRequest.setOnSucceeded(e -> {
            loadingArea.setVisible(false);
            content.setVisible(true);
            ObservableList<Champ> champs = FXCollections.observableArrayList();
            for(var championship : championshipsRequest.getValue()) {
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
                        if(searchByNumberField.getText() == "")
                            return;
                        var champ = SearchByNumber(champs, Integer.valueOf(searchByNumberField.getText()));

                        championshipsTable.getItems().clear();
                        championshipsTable.getItems().add(champ);
                    }
                }
            });

            searchByCountryOrSityField.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent t)  {
                    if (t.getCode() == KeyCode.ENTER) {
                        var selectedChamps = SearchByCountryOrCity(champs, searchByCountryOrSityField.getText());
                        championshipsTable.getItems().clear();
                        for(var selectedChamp : selectedChamps) {
                            championshipsTable.getItems().add(selectedChamp);
                        }
                    }
                }
            });

            championshipsTable.getItems().addAll(champs);

        });

       // var championships = champService.findAll();

//        ObservableList<Champ> champs = FXCollections.observableArrayList();
//        for(var championship : championships) {
//            champs.add(new Champ(championship.getOrderNumber(), championship.getName(),
//                    championship.getFullAddress() , championship.getDateTo().toString().substring(0,4) ,
//                    championship.getCountry(), championship.getCity(),
//                    championship.getUsers().size()));
//        }



        numberColumn.setCellValueFactory(new PropertyValueFactory<Champ, Integer>("number"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Champ, String>("year"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Champ, String>("field"));
        countParticipantColumn.setCellValueFactory(new PropertyValueFactory<Champ, Integer>("countParticipant"));

        //exec.execute(championshipsRequest);

        championshipsRequest.restart();




    }
}
