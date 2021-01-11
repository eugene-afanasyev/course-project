package main.controllers;

import com.mchange.io.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.parsers.XLSXParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AboutKrasnodarRegionController {

    @FXML
    private TabPane iformationPane;

    @FXML
    private TextArea historyInformation;

    @FXML
    private VBox eventsBox;

    @FXML
    private ImageView firstFoodImage;

    @FXML
    private ImageView secondFoodImage;

    @FXML
    private ImageView thirdFoodImage;

    @FXML
    private ImageView firstTravelImage;

    @FXML
    private ImageView secondTravelImage;

    @FXML
    private ImageView thirdTravelImage;

    @FXML
    private ImageView firstHotelImage;

    @FXML
    private ImageView secondHotelImage;

    @FXML
    private ImageView thirdHotelImage;

    @FXML
    void initialize() throws IOException {
        File file = new File("res/history.txt");
        historyInformation.setText(FileUtils.getContentsAsString(file));
        historyInformation.setWrapText(true);


        XLSXParser parser = new XLSXParser();
        var events = parser.Parse();
        for(var event  : events) {
            BorderPane pane = new BorderPane();

            Label nameLabel = new Label(event.getName());
            nameLabel.setStyle("-fx-font-size:22; -fx-font-weight: bold");
            pane.setTop(nameLabel);

            Label txt = new Label(event.getDescription());
            txt.setStyle("-fx-font-size:18;");
            txt.setWrapText(true);
            pane.setCenter(txt);

            Label dateLabel = new Label("Дата проведения:");
            dateLabel.setStyle("-fx-opacity: 0.6");

            Label webLabel = new Label("website:");
            webLabel.setStyle("-fx-opacity: 0.6");

            HBox hBox = new HBox(dateLabel, new Label(event.getDate()),
                    webLabel, new Label(event.getWebSite()));

            hBox.setSpacing(5);
            pane.setBottom(hBox);
            pane.getCenter().prefHeight(700);
            eventsBox.getChildren().add(pane);

        }

        firstFoodImage.setImage(new Image(new FileInputStream("res/tourism/348310_original.jpg")));
        secondFoodImage.setImage(new Image(new FileInputStream("res/tourism/f558a9d42a68f823088a4bdf86600688.jpg")));
        thirdFoodImage.setImage(new Image(new FileInputStream("res/tourism/KMO_139031_01391_1_t218_171451.jpg")));

        firstTravelImage.setImage(new Image(new FileInputStream("res/tourism/Novorossiysk.jpg")));
        secondTravelImage.setImage(new Image(new FileInputStream("res/tourism/Gelendshik.jpg")));
        thirdTravelImage.setImage(new Image(new FileInputStream("res/tourism/Krasnodar_004.JPG")));

        firstHotelImage.setImage(new Image(new FileInputStream("res/tourism/1200px-МЃаб™Ѓ©_ѓЃав_СЃз®_®_ѓа®Ђ•£†ой†п_в•аа®вЃа®п_04.jpg")));
        secondHotelImage.setImage(new Image(new FileInputStream("res/tourism/Е©б™_ГДК.jpg")));
        thirdHotelImage.setImage(new Image(new FileInputStream("res/tourism/1200px-Sunset_Black_sea.jpg")));

        HeaderController.viewPath = "/Views/main.fxml";
    }
}