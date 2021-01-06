package main.controllers;

import com.mchange.io.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.parsers.XLSXParser;

import java.io.File;
import java.io.IOException;

public class AboutKrasnodarRegionController {

    class EventNode extends AnchorPane {
        public EventNode() throws IOException {
            FXMLLoader ev = FXMLLoader.load(getClass().getResource("res/Views/event.fxml"));
            ev.load();
            ev.setController(this);
        }
    }

    @FXML
    private TabPane iformationPane;

    @FXML
    private TextArea historyInformation;

    @FXML
    private VBox eventsBox;

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
            eventsBox.getChildren().add(pane);

        }

    }
}