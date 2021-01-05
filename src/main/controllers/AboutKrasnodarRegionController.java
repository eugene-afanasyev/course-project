package main.controllers;

import com.mchange.io.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;

public class AboutKrasnodarRegionController {
    @FXML
    private TabPane iformationPane;

    @FXML
    void initialize() throws IOException {
        File file = new File("res/history.txt");
        String history = FileUtils.getContentsAsString(file);
        Label historyLabel = new Label(history);
        historyLabel.setWrapText(true);
        Tab historyInformation = new Tab("История", historyLabel);

        Tab eventInformation = new Tab("События");
        //TODO


        Tab tourismInformation = new Tab("Туризм");
        //TODO

        iformationPane.getTabs().add(historyInformation);

    }
}
