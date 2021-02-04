package main.controllers;

import com.github.cage.YCage;
import com.google.gson.*;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.AuthHelper;
import main.AuthManager;
import main.misc.UserData;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    @FXML
    public StackPane stackPane;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label captchaErrorLabel;

    private String captchaText;

    private final List<String> errors = new LinkedList<>();

    private static int loginAttempt;

    Executor exec = Executors.newCachedThreadPool(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t ;
    });

    public void initialize() {
        loginAttempt = 0;
        HeaderController.viewPath = "/Views/main.fxml";
        setCaptcha();
        loginButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

            @Override
            public void handle( ActionEvent event) {
                errors.clear();

                captchaErrorLabel.setText("");
                loginErrorLabel.setText("");
                passwordErrorLabel.setText("");

                if(!AuthHelper.isLoginValid(idNumberField.getText())){
                    errors.add("Логин не должен быть пустым!");
                    //showErrorAlert("Логин не должен быть пустым!");
                    loginErrorLabel.setText("Логин не должен быть пустым!");
                }

                if(!AuthHelper.isPasswordValid(passwordField.getText())){
                    errors.add("Пароль не должен быть пустым");
                    //showErrorAlert("Пароль не должен быть пустым!");
                    passwordErrorLabel.setText("Пароль не должен быть пустым");
                }

                if(!captchaInputField.getText().equals(captchaText)){
                    errors.add("Неверный код");
                    //showErrorAlert("Каптча введена неверно");
                    captchaErrorLabel.setText("Каптча введена неверно");
                    setCaptcha();
                    captchaInputField.setStyle("-fx-border-color: red");
                }

                if(errors.size() != 0){
                    return;
                }

                var authRequest = new Task<Boolean>(){
                    @Override
                    protected Boolean call ( ) throws Exception {
                        return AuthManager.Current.authorize(idNumberField.getText(), passwordField.getText());
                    }
                };

                authRequest.setOnFailed(e -> {
                    errors.add("Ошибка приложения. Попоробуйте позже.");
                    loginButton.setText("Login");
                });

                authRequest.setOnSucceeded(e -> {
                    var isSuccessful = authRequest.getValue();
                    loginButton.setText("Login");
                    if(!isSuccessful){
                        errors.add("Неверный логин или пароль");
                        idNumberField.setStyle("-fx-border-color: #ff0000");
                        passwordField.setStyle("-fx-border-color: red");
                        //showErrorAlert("Неверный логин или пароль");
                        loginErrorLabel.setText("Неверный логин или пароль");
                        passwordErrorLabel.setText("Неверный логин или пароль");
                        setCaptcha();
                    }

                    // к этому моменту пользователь авторизован, следовательно, тут нужно перейти на предыдущую страницу
                    if (AuthManager.Current.isAuthorized()) {
                        var role = AuthManager.Current.getUserRole();

                        switch (role.getName()) {
                            case "Expert":
                                // TODO
                                break;
                            case "Press":
                                // TODO
                                break;
                            case "Competitor":
                                moveToScene("/Views/CompetitorMenu.fxml");
                                break;
                            case "Volunteer":
                                // TODO
                                break;
                            case "Administrator":
                                // TODO
                                break;
                            case "Coordinator":
                                moveToScene("/Views/CoordinatorMenu.fxml");
                                // TODO
                                break;
                            default:
                                break;
                        }

                        if (rememberMeChBox.isSelected()) {
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .create();

                            UserData userData = new UserData(idNumberField.getText(), passwordField.getText());
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("remembered", true);
                            JsonObject userJson = (JsonObject) JsonParser.parseString(gson.toJson(userData));
                            jsonObject.add("user", userJson);

                            try {
                                BufferedWriter writer = new BufferedWriter(new FileWriter("user-data.json"));
                                writer.write(gson.toJson(jsonObject));
                                writer.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }

                        } else {
                            Gson gson = new GsonBuilder()
                                    .setPrettyPrinting()
                                    .create();
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("remembered", false);

                            try {
                                BufferedWriter writer = new BufferedWriter(new FileWriter("user-data.json"));
                                writer.write(gson.toJson(jsonObject));
                                writer.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                });
                try {
                    loginButton.setText("Loading...");
                    exec.execute(authRequest);
                } catch (Exception ex){
                    ex.fillInStackTrace();
                }

                if (!AuthManager.Current.isAuthorized())
                    loginAttempt++;
                else
                    loginAttempt = 0;

                if (loginAttempt == 3) {
                    captchaInputField.setText("Заблокированно");
                    idNumberField.setText("Заблокированно");
                    passwordField.setText("Заблокированно");
                    passwordField.setVisible(true);
                    captchaInputField.setDisable(true);
                    idNumberField.setDisable(true);
                    passwordField.setDisable(true);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setWidth(300);
                    alert.setHeight(200);
                    alert.setHeaderText("Ошибка авторизации");
                    alert.setContentText("Пожалуйста, перезапустите приложение.");
                    alert.showAndWait();
                }
            }
        });

        BorderPane bp = new BorderPane();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        vBox.setStyle("-fx-background-color: #e8e8e8");

        var participantButton = createRoleButton("Участник");
        participantButton.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(bp);
        });
        var adminButton = createRoleButton("Администратор");
        adminButton.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(bp);
        });
        var vltButton = createRoleButton("Волонтер");
        vltButton.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(bp);
        });
        var crdButton = createRoleButton("Координатор");
        crdButton.setOnMouseClicked(event -> {
            stackPane.getChildren().remove(bp);
        });

        Label warningLabel = new Label("Только для тестирования");
        warningLabel.setTextFill(new Color(1,0.7,0.4, 1));
        warningLabel.setStyle("-fx-font-size: 32; -fx-wrap-text: true; -fx-font-weight: bold");

        Label deskLabel = new Label("Под каким пользователем вы хотите войти в систему?");
        deskLabel.setTextFill(new Color(0,0,0,0.7));
        deskLabel.setStyle("-fx-font-size: 20;-fx-wrap-text: true");

        vBox.getChildren().addAll(deskLabel, participantButton, adminButton, vltButton, crdButton);

        bp.setMaxHeight(550);
        bp.setMaxWidth(500);
        bp.setStyle("-fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 4;" +
                " -fx-background-color: #e5e5e5; -fx-font-size: 26; -fx-padding: 10");

        bp.setCenter(vBox);
        bp.setTop(warningLabel);
        stackPane.getChildren().add(bp);
    }

    private Button createRoleButton(String text) {
        Button b = new Button(text);
        b.setStyle("-fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 4; -fx-background-color: #bfbfbf;" +
                "-fx-font-size: 26; -fx-pref-width: 300");
        return b;
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

    public void showErrorAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(errorText);
        alert.showAndWait();
    }

    public void moveToScene(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root, 1020, 740);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("/stylesheets/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
