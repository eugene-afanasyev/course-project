package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.AuthManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CompetitorProfileController {

    @FXML
    private ImageView userImage;

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
    private PasswordField newPasswordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    private Button passwordSaveButton;

    @FXML
    private Button canselButton;

    @FXML
    private CheckBox changePasswordCheckBox;


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
    void initialize() throws FileNotFoundException {
        var user = AuthManager.Current.getUser();

        newPasswordField.setEditable(false);
        repeatPasswordField.setEditable(false);

        changePasswordCheckBox.setOnAction(actionEvent -> {
            if(changePasswordCheckBox.isSelected()) {
                newPasswordField.setStyle("-fx-opacity : 1;");
                repeatPasswordField.setStyle("-fx-opacity : 1;");

                newPasswordField.setEditable(true);
                repeatPasswordField.setEditable(true);
            } else {
                newPasswordField.setStyle("-fx-opacity : 0.3;");
                repeatPasswordField.setStyle("-fx-opacity : 0.3;");

                newPasswordField.setEditable(false);
                repeatPasswordField.setEditable(false);
            }
        });

        userImage.setImage(new Image(new FileInputStream("res/ws6195763a-6ba0-49c9-aa9e-028d630977d0_portrait.jpg")));
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

        canselButton.setOnAction(actionEvent -> {
            newPasswordField.clear();
            repeatPasswordField.clear();
        });

        SignedUserHeaderController.viewPath = "/Views/CompetitorMenu.fxml";
    }
}
