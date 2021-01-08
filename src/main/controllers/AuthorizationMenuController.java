package main.controllers;

import com.github.cage.YCage;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.AuthHelper;
import main.AuthManager;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AuthorizationMenuController {
    @FXML
    public TextField idNumberField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public CheckBox rememberMeChBox;
    @FXML
    public Button loginButton;
    @FXML
    public Button cancelButton;
    @FXML
    public VBox captchaField;
    @FXML
    public ImageView captchaImage;
    @FXML
    public TextField captchaInputField;

    private String captchaText;

    private final List<String> errors = new LinkedList<>();

    public void initialize() {
        HeaderController.viewPath = "/Views/main.fxml";
        setCaptcha();
        loginButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle( ActionEvent event) {
                errors.clear();

                if(!AuthHelper.isLoginValid(idNumberField.getText())){
                    errors.add("Логин не должен быть пустым!");
                }
                if(!AuthHelper.isPasswordValid(passwordField.getText())){
                    errors.add("Пароль не должен быть пустым");
                }
                if(!captchaInputField.getText().equals(captchaText)){
                    errors.add("Неверный код");
                    // судя по всему, тут стоит новую капчу поставить
                    setCaptcha();
                }

                if(errors.size() != 0){
                    return;
                }

                var isSuccessful = AuthManager.Current.authorize(idNumberField.getText(), passwordField.getText());

                if(!isSuccessful){
                    errors.add("Неверный логин или пароль");
                }

                // к этому моменту пользователь авторизован, следовательно, тут нужно перейти на предыдущую страницу

            }
        });
    }

    public void setCaptcha() {
        // generate random string
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();

        captchaText = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        YCage cage = new YCage();
        BufferedImage captchaImg = cage.drawImage(captchaText);
        Image image = SwingFXUtils.toFXImage(captchaImg, null);
        captchaImage.setImage(image);
    }
}
