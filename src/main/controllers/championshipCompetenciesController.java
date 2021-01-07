package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class championshipCompetenciesController {
    @FXML
    private TextArea informationArea;

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

    @FXML
    void initialize() {
        TreeItem<Label> rootTreeNode = new TreeItem<Label>(buildLabel("Все компетенции", 150,20));

        TreeItem<Label> serviceTransports = new TreeItem<Label>(buildLabel("Обслуживание гражданского транспорта",
                300, 60));
        TreeItem<Label> buildingSphere = new TreeItem<Label>(buildLabel("Специалисты строительной системы",
                300, 60));
        TreeItem<Label> industrialProduction = new TreeItem<Label>(buildLabel("Специалисты, занятые на промышленном производстве",
                300, 70));
        TreeItem<Label> informationAndCommunicationTechnologies = new TreeItem<Label>(buildLabel("Специалисты информационных и коммуникаационных технологий",
                300, 70));
        TreeItem<Label> creativityAndDesign = new TreeItem<Label>(buildLabel("Творчество и дизайн", 300, 20));
        TreeItem<Label> serviceSector = new TreeItem<Label>(buildLabel("Сфера услуг", 300, 20));

        rootTreeNode.getChildren().addAll(serviceTransports, buildingSphere, industrialProduction,
                informationAndCommunicationTechnologies, creativityAndDesign, serviceSector);

        competences.setRoot(rootTreeNode);

    }
}
