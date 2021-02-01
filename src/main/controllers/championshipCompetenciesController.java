package main.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.dao.DBDisciplineDAO;
import main.models.Discipline;
import main.services.DisciplineService;

import java.util.List;

public class championshipCompetenciesController {
    @FXML
    private TextArea competenceInformationArea;

    @FXML
    private Label competenceNameLabel;

    @FXML
    private TreeView<Label> competences;

    @FXML
    private Pane loadingArea;

    @FXML
    private ProgressIndicator spinner;
    @FXML
    private VBox content;

    private final  DisciplineService<DBDisciplineDAO> disciplineService = new DisciplineService<>(DBDisciplineDAO::new);

//    Executor exec = Executors.newCachedThreadPool(runnable -> {
//        Thread t = new Thread(runnable);
//        t.setDaemon(true);
//        return t ;
//    });

    Label buildLabel(String text,double prefWidth, double prefHeight) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
        label.setWrapText(true);

        return label;
    }

    Discipline findDiscipline(List<Discipline> list, String name) {
        for(var el : list) {
            if(el.getName().equals(name))
                return el;
        }
        return null;
    }

    public class ChampionshipCompetenciesExample extends Service<List<Discipline>> {
        @Override
        protected Task<List<Discipline>> createTask() {
            return new Task<List<Discipline>>() {
                @Override
                protected List<Discipline> call() throws Exception {
                    return disciplineService.findAll();
                }
            };
        }
    }

    ChampionshipCompetenciesExample loadRequest = new ChampionshipCompetenciesExample();
    @FXML
    void initialize() {

        content.setVisible(false);
        spinner.progressProperty().unbind();
        spinner.progressProperty().bind(loadRequest.progressProperty());


        loadRequest.setOnSucceeded(e -> {
            content.setVisible(true);
            TreeItem<Label> rootTreeNode = new TreeItem<Label>(buildLabel("Все компетенции", 150,20));

            for(var competence : loadRequest.getValue()) {
                TreeItem<Label> item = new TreeItem<Label>(buildLabel(competence.getName(), 300, 20));
                rootTreeNode.getChildren().addAll(item);
            }

            competences.setRoot(rootTreeNode);
            competences.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

                @Override
                public void changed(ObservableValue observable, Object oldValue,
                                    Object newValue) {

                    TreeItem<Label> selectedItem = (TreeItem<Label>) newValue;
                    System.out.println("Selected Text : " + selectedItem.getValue());
                    var competence = findDiscipline(loadRequest.getValue(), selectedItem.getValue().getText());
                    competenceNameLabel.setText(competence.getRuName());
                    competenceNameLabel.setStyle("-fx-wrap-text: true; -fx-font-size: 26");
                    competenceInformationArea.setText(competence.getDescription());
                }
            });

            loadingArea.setVisible(false);

        });
        loadRequest.setOnFailed(e -> {
            loadingArea.setVisible(false);
        });

        competenceInformationArea.setWrapText(true);
        competenceInformationArea.setStyle("-fx-font-size: 22");
        competenceInformationArea.setEditable(false);
 /*       var disciplineService = new DisciplineService<>(DBDisciplineDAO::new);
        var allDisciplines = disciplineService.findAll();
        System.out.println(allDisciplines);*/

        //exec.execute(loadRequest);
        loadRequest.restart();

        HeaderController.viewPath = "/Views/AboutWorldSkills.fxml";
    }
}
