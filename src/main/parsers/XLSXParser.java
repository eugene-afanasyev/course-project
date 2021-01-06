package main.parsers;

import main.models.Event;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class XLSXParser {

    public LinkedList<Event> Parse() throws IOException {

        LinkedList<Event> events = new LinkedList<Event>();

        File excelFile = new File("res/event.xlsx");
        FileInputStream fis = new FileInputStream(excelFile);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // we get first sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIt = sheet.iterator();
        Row first_row = rowIt.next();

        while(rowIt.hasNext()) {
            Row row = rowIt.next();
            Event event = new Event();
            event.setName(row.getCell(0).toString());
            event.setDescription(row.getCell(1).toString());
            event.setDate(row.getCell(2).toString());
            event.setWebSite(row.getCell(3).toString());
            events.add(event);

            // iterate on cells for the current row
//            Iterator<Cell> cellIterator = row.cellIterator();
//
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
//                System.out.print(cell.toString() + ";");
//            }
//
//            System.out.println();
        }

        workbook.close();
        fis.close();

        return events;
    }

}
