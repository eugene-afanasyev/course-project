package main.parsers;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVParser {
    static List<String[]> Parse(String path) throws IOException {
        try {
            CSVReader reader = new CSVReader(new FileReader(path), ',', '"', 1);
            List<String[]> allRows = reader.readAll();
            return allRows;
        } catch (FileNotFoundException e) {
            e.getStackTrace();
            return null;
        }
    }
}
