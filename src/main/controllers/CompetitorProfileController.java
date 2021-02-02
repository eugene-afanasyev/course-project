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

    @FXML
    private Label newPasswordErrorLabel;

    @FXML
    private Label repeatPasswordErrorLabel;


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

    Thread SuccessPasswordThread = new Thread(() -> {
        try {
            repeatPasswordErrorLabel.setStyle("-fx-text-fill: green");
            repeatPasswordErrorLabel.setText("Пароль успешно сохранен");
            Thread.sleep(7000);
            repeatPasswordErrorLabel.setStyle("-fx-text-fill: red");
            repeatPasswordErrorLabel.setText("");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    boolean checkCorrectPassword(String password, String newPassword, String repeatPassword) {
        if(newPassword == "") {
            newPasswordErrorLabel.setText("Введите новый пароль");
            return false;
        }

        if(repeatPassword == "") {
            repeatPasswordErrorLabel.setText("Введите новый пароль");
            return false;
        }

        if(newPassword.contains(" ")) {
            newPasswordErrorLabel.setText("Некорректный новый пароль");
            return false;
        }

        if(password.equals(newPassword)) {
            newPasswordErrorLabel.setText("Этот пароль уже используется");
            return false;
        }

        if(!newPassword.equals(repeatPassword)) {
            repeatPasswordErrorLabel.setText("Повторный пароль не совпадает");
            return false;
        }

        if(newPassword.length() < 8) {
            newPasswordErrorLabel.setText("Пароль должен содержать минимум 8 символов");
            return false;
        }
        return true;
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
            newPasswordErrorLabel.setText("");
            repeatPasswordErrorLabel.setText("");
            if(checkCorrectPassword(user.getPassword(), newPassword, repeatPassword)) {
                AuthManager.Current.changePassword(newPassword);
                newPasswordField.clear();
                repeatPasswordField.clear();
                new Thread(SuccessPasswordThread).start();
            }
        });

        canselButton.setOnAction(actionEvent -> {
            newPasswordField.clear();
            repeatPasswordField.clear();
        });

        SignedUserHeaderController.viewPath = "/Views/CompetitorMenu.fxml";
    }

}
