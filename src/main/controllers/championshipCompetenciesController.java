package main.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

    Label buildLabel(String text,double prefWidth, double prefHeight) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
        label.setWrapText(true);
        label.setPrefHeight(prefHeight);
        label.setPrefWidth(prefWidth);

        return label;
    }

    Discipline findDiscipline(List<Discipline> list, String name) {
        for(var el : list) {
            if(el.getName().equals(name))
                return el;
        }
        return null;
    }

    @FXML
    void initialize() {
        competenceInformationArea.setWrapText(true);
        competenceInformationArea.setStyle("");
        competenceInformationArea.setEditable(false);

        var disciplineService = new DisciplineService<>(DBDisciplineDAO::new);
        var allDisciplines = disciplineService.findAll();
        System.out.println(allDisciplines);

        TreeItem<Label> rootTreeNode = new TreeItem<Label>(buildLabel("Все компетенции", 150,20));

        for(var competence : allDisciplines) {
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
                var competence = findDiscipline(allDisciplines, selectedItem.getValue().getText());
                competenceNameLabel.setText(competence.getRuName());
                competenceInformationArea.setText(competence.getDescription());
            }

        });

        HeaderController.viewPath = "/Views/AboutWorldSkills.fxml";
    }
}
