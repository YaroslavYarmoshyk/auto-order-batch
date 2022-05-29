package etake.autoorderbatch.service.impl;

import etake.autoorderbatch.service.HeaderMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static etake.autoorderbatch.util.Constants.ATTRIBUTES;

@Component
public class HeaderMapperImpl implements HeaderMapper {

    public static final Logger LOGGER = Logger.getLogger(HeaderMapperImpl.class.getName());

    @Override
    public Map<String, Integer>  getHeaders() {
        try (FileInputStream file = new FileInputStream("src/main/resources/input.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();

            return getHeaderNames(cellIterator);

        } catch (Exception e) {
            LOGGER.warning("Cannot get headers from file");
            return null;
        }
    }

    private Map<String, Integer> getHeaderNames(Iterator<Cell> cellIterator) {
        Map<String, Integer> headers = new HashMap<>();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            headers.put(cell.getStringCellValue(),cell.getColumnIndex());
        }
        return headers.entrySet()
                .stream()
                .filter(p -> ATTRIBUTES.contains(p.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
