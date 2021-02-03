package main.controllers;

import com.mchange.io.FileUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setMaxWidth(600);
            vbox.setStyle("-fx-spacing: 10");

            Label nameLabel = new Label(event.getName());
            nameLabel.setStyle("-fx-font-size:28; -fx-font-weight: bold");
            vbox.getChildren().add(nameLabel);

            Label txt = new Label(event.getDescription());
            txt.setStyle("-fx-font-size:24;");
            txt.setWrapText(true);
            vbox.getChildren().add(txt);

            Label dateLabel = new Label("Дата проведения:");
            dateLabel.setStyle("-fx-opacity: 0.6; -fx-font-size: 20");

            Label webLabel = new Label("website:");
            webLabel.setStyle("-fx-opacity: 0.6; -fx-font-size: 20");


            HBox hBox1 = new HBox(dateLabel, new Label(event.getDate()));
            hBox1.setSpacing(5);
            hBox1.setStyle("-fx-font-size: 20");

            HBox hBox2 = new HBox(webLabel, new Label(event.getWebSite()));
            hBox2.setSpacing(5);
            hBox2.setStyle("-fx-font-size: 20");

            vbox.getChildren().addAll(hBox1, hBox2);
            vbox.getChildren().add(new Separator());
            eventsBox.getChildren().add(vbox);

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