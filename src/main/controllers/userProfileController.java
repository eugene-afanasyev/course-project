package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.AuthManager;

public class userProfileController {
    @FXML
    private Label userNameLabel;

    @FXML
    private Label userSexLabel;

    @FXML
    private Label userDateOfBirthdateOfBirthLabel;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label userCountryLabel;

    @FXML
    private Label userPhoneLabel;

    @FXML
    private Label userMailLabel;

    @FXML
    void initialize() {
        var user = AuthManager.Current.getUser();

        userNameLabel.setText(user.getFirstName() + " " + user.getLastName());

        String sex = user.isMale() ? "Male" : "Female";
        userSexLabel.setText(sex);
        userDateOfBirthdateOfBirthLabel.setText(user.getBirthdayDate().toString());
        userCountryLabel.setText(user.getRegion().getName());
        userIdLabel.setText(String.valueOf(user.getId()));
        userMailLabel.setText(user.getEmail());

    }
}
