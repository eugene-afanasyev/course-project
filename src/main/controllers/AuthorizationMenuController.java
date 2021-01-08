package main.controllers;

import com.github.cage.YCage;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
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

    public void initialize() {
        HeaderController.viewPath = "/Views/main.fxml";
        setCaptcha();
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
