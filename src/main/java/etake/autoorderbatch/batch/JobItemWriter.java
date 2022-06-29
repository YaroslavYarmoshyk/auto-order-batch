package etake.autoorderbatch.batch;

import etake.autoorderbatch.dto.NameValuePair;
import etake.autoorderbatch.dto.PositionResponseDTO;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Strings;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static etake.autoorderbatch.util.Constants.AM;
import static etake.autoorderbatch.util.Constants.ANALYST;
import static etake.autoorderbatch.util.Constants.CATEGORY;
import static etake.autoorderbatch.util.Constants.CODE_CSKU;
import static etake.autoorderbatch.util.Constants.CODE_SKU;
import static etake.autoorderbatch.util.Constants.COMPLIANCE_BASIC_DEMAND;
import static etake.autoorderbatch.util.Constants.COMPLIANCE_COEF_DEMAND;
import static etake.autoorderbatch.util.Constants.COMPLIANCE_FINAL_DEMAND;
import static etake.autoorderbatch.util.Constants.DEMAND;
import static etake.autoorderbatch.util.Constants.DEMAND_MAIN;
import static etake.autoorderbatch.util.Constants.DEMAND_WITH_COEFFICIENTS;
import static etake.autoorderbatch.util.Constants.GROUP_3;
import static etake.autoorderbatch.util.Constants.MIN_NUMBER;
import static etake.autoorderbatch.util.Constants.NAME_CSKU;
import static etake.autoorderbatch.util.Constants.NAME_SKU;
import static etake.autoorderbatch.util.Constants.NO;
import static etake.autoorderbatch.util.Constants.SPACE;
import static etake.autoorderbatch.util.Constants.STORE;
import static etake.autoorderbatch.util.Constants.YES;

@Configuration
public class JobItemWriter implements ItemStreamWriter<PositionResponseDTO> {
    private XSSFWorkbook workbook;
    private WritableResource resource;
    private int row;

    @Override
    public void write(List<? extends PositionResponseDTO> list) {
        list = list.stream()
                .filter(this::isMatrixPosition)
                .collect(Collectors.toList());

        XSSFSheet sheet = workbook.getSheetAt(0);
        for (PositionResponseDTO positionResponseDTO : list) {
            Row sheetRow = sheet.createRow(row++);

            sheetRow.createCell(0).setCellValue(positionResponseDTO.getStore());
            sheetRow.createCell(1).setCellValue(positionResponseDTO.getCategory());
            sheetRow.createCell(2).setCellValue(positionResponseDTO.getGroup3());
            sheetRow.createCell(3).setCellValue(positionResponseDTO.getCode());
            sheetRow.createCell(4).setCellValue(positionResponseDTO.getName());
            sheetRow.createCell(5).setCellValue(getAttributeValue(positionResponseDTO, AM, false));
            sheetRow.createCell(6).setCellValue(getAttributeValue(positionResponseDTO, MIN_NUMBER, false));
            sheetRow.createCell(7).setCellValue(getAttributeValue(positionResponseDTO, CODE_CSKU, false));
            sheetRow.createCell(8).setCellValue(getAttributeValue(positionResponseDTO, NAME_CSKU, false));
            sheetRow.createCell(9).setCellValue(getAttributeValue(positionResponseDTO, DEMAND_MAIN, false));
            sheetRow.createCell(10).setCellValue(getAttributeValue(positionResponseDTO, DEMAND_WITH_COEFFICIENTS, false));
            sheetRow.createCell(11).setCellValue(getAttributeValue(positionResponseDTO, DEMAND, false));
            sheetRow.createCell(12).setCellValue(getCalculatedValue(positionResponseDTO, DEMAND_MAIN));
            sheetRow.createCell(13).setCellValue(getCalculatedValue(positionResponseDTO, DEMAND_WITH_COEFFICIENTS));
            sheetRow.createCell(14).setCellValue(getCalculatedValue(positionResponseDTO, DEMAND));
            sheetRow.createCell(15).setCellValue(Objects.equals(sheetRow.getCell(9).getStringCellValue(), sheetRow.getCell(12).getStringCellValue()) ? YES : NO);
            sheetRow.createCell(16).setCellValue(Objects.equals(sheetRow.getCell(10).getStringCellValue(), sheetRow.getCell(13).getStringCellValue()) ? YES : NO);
            sheetRow.createCell(17).setCellValue(Objects.equals(sheetRow.getCell(11).getStringCellValue(), sheetRow.getCell(14).getStringCellValue()) ? YES : NO);
        }
    }

    /**
     * Check if matrix position, if it's true - we add values
     */
    private boolean isMatrixPosition(PositionResponseDTO positionResponseDTO) {
        return !positionResponseDTO.getAttributes().stream()
                .filter(a -> a.getName().equalsIgnoreCase(AM))
                .findFirst()
                .orElse(new NameValuePair<>())
                .getValue()
                .equals(org.apache.logging.log4j.util.Strings.EMPTY);
    }

    /**
     *Get attribute value and round it to 3 precise if the value is demand value
     */
    private String getAttributeValue(PositionResponseDTO positionResponseDTO, String attribute, boolean isAnalystAttributes) {
        final Set<NameValuePair<String, String>> attributes = isAnalystAttributes ? positionResponseDTO.getAttributesAnalyst() : positionResponseDTO.getAttributes();
        final String value = attributes.stream()
                .filter(a -> a.getName().equalsIgnoreCase(attribute))
                .findFirst()
                .orElse(new NameValuePair<>())
                .getValue();
        if (isDemandValue(attribute)) {
            if (Strings.isNullOrEmpty(value)) {
                return null;
            }

            return String.valueOf(BigDecimal.valueOf(Double.parseDouble(value)).divide(BigDecimal.ONE, 3, RoundingMode.HALF_UP));
        }

        return value;
    }

    /**
     * Check if we have value with we should compare
     */
    private boolean isDemandValue(String attribute) {
        return  attribute.equalsIgnoreCase(DEMAND_MAIN) || attribute.equalsIgnoreCase(DEMAND_WITH_COEFFICIENTS) || attribute.equalsIgnoreCase(DEMAND);
    }

    /**
     * Get calculated values for demands
     */
    private String getCalculatedValue(PositionResponseDTO positionResponseDTO, String attribute) {
        final String value = getAttributeValue(positionResponseDTO, attribute, true);

        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        final BigDecimal decimal = BigDecimal.valueOf(Double.parseDouble(value));

        if (decimal.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        return String.valueOf(decimal.divide(BigDecimal.ONE, 3, RoundingMode.HALF_UP));
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        resource = new FileSystemResource("/home/yaroslav-yarmoshyk/Desktop/projects/auto-order-batch/src/main/resources/output.xlsx");
        row = 0;

        createHeaderRow(sheet);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {
        if (workbook == null) {
            return;
        }

        final XSSFSheet sheet = workbook.getSheetAt(0);
        final int lastRow = sheet.getLastRowNum();
        final int lastColumn = sheet.getRow(0).getLastCellNum() - 1;

        for (int i = 0; i <= lastRow; i++) {
            for (int j = 0; j <= lastColumn; j++) {
                setCellStyle(sheet.getRow(i).getCell(j));
            }
        }

        IntStream.range(0, 18).forEach(sheet::autoSizeColumn);

        try (BufferedOutputStream bos = new BufferedOutputStream(resource.getOutputStream())) {
            workbook.write(bos);
            bos.flush();
            workbook.close();
        } catch (IOException ex) {
            throw new ItemStreamException("Error writing to output file", ex);
        }
        row = 0;
    }

    private void createHeaderRow(XSSFSheet sheet) {
        sheet.createFreezePane(0, 1);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);

        XSSFRow sheetRow = sheet.createRow(row);

        sheetRow.setRowStyle(cellStyle);

        sheetRow.createCell(0).setCellValue(STORE);
        sheetRow.createCell(1).setCellValue(CATEGORY);
        sheetRow.createCell(2).setCellValue(GROUP_3);
        sheetRow.createCell(3).setCellValue(CODE_SKU);
        sheetRow.createCell(4).setCellValue(NAME_SKU);
        sheetRow.createCell(5).setCellValue(AM);
        sheetRow.createCell(6).setCellValue(MIN_NUMBER);
        sheetRow.createCell(7).setCellValue(CODE_CSKU);
        sheetRow.createCell(8).setCellValue(NAME_CSKU);
        sheetRow.createCell(9).setCellValue(DEMAND_MAIN);
        sheetRow.createCell(10).setCellValue(DEMAND_WITH_COEFFICIENTS);
        sheetRow.createCell(11).setCellValue(DEMAND);
        sheetRow.createCell(12).setCellValue(DEMAND_MAIN + SPACE + ANALYST);
        sheetRow.createCell(13).setCellValue(DEMAND_WITH_COEFFICIENTS + SPACE + ANALYST);
        sheetRow.createCell(14).setCellValue(DEMAND_MAIN + SPACE + ANALYST);
        sheetRow.createCell(15).setCellValue(COMPLIANCE_BASIC_DEMAND);
        sheetRow.createCell(16).setCellValue(COMPLIANCE_COEF_DEMAND);
        sheetRow.createCell(17).setCellValue(COMPLIANCE_FINAL_DEMAND);

        row++;
    }

    private void setCellStyle(Cell cell) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints(Short.parseShort("10"));
        font.setFontName("Arial");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);
    }

}
