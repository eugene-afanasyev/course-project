package main.controllers;

import com.mchange.io.FileUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;

public class AboutKrasnodarRegionController {
    @FXML
    private TabPane iformationPane;

    @FXML
    private TextArea historyInformation;

    @FXML
    void initialize() throws IOException {
        File file = new File("res/history.txt");
        historyInformation.setText(FileUtils.getContentsAsString(file));
        historyInformation.setWrapText(true);

//        Tab historyInformation = new Tab("История", historyLabel);
//
//        Tab eventInformation = new Tab("События");
//        //TODO
//
//        XLSXParser parser = new XLSXParser();
//        var events = parser.Parse();
//
//
//        Tab tourismInformation = new Tab("Туризм");
        //TODO

       // iformationPane.getTabs().add(historyInformation);

    }
}
