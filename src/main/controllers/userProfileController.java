package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private TextField newPasswordField;

    @FXML
    private TextField repeatPasswordField;

    @FXML
    private Button passwordSaveButton;

    @FXML
    private Button canselButton;

    public void showErrorAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public void showSuccessAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setContentText(errorText);
        alert.showAndWait();
    }

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

        passwordSaveButton.setOnAction(actionEvent -> {
            var newPassword = newPasswordField.getText();
            var repeatPassword = repeatPasswordField.getText();

            if(newPassword == "") {
                showErrorAlert("Введите новый пароль");
            }

            if(repeatPassword == "") {
                showErrorAlert("Введите повторно пароль");
            }

            if(newPassword.equals(repeatPasswordField.getText())) {
                if(user.getPassword().equals(newPassword)) {
                    showErrorAlert("Этот пароль сейчас используется");
                } else {
                    user.setPassword(newPassword);
                    showSuccessAlert("Пароль был успешно изменен");
                }
            } else {
                showErrorAlert("Поля нового пароля и повтора пароля должны совпадать");
            }
        });

    }
}
