package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
    public void initialize(){

    }
}