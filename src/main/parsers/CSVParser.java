package main.parsers;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.List;

public class CSVParser {
    static public<T> List<T> Parse(String path, Class<T> clazz) {
        try {
            String fileName = path;
            List<T> beans = new CsvToBeanBuilder(new FileReader(fileName))
                    .withType(clazz)
                    .build()
                    .parse();
            return beans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
