package etake.autoorderbatch.batch;

import etake.autoorderbatch.dto.PositionDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.WritableResource;

import java.util.List;

@Configuration
public class JobItemWriter implements ItemStreamWriter<PositionDTO> {
    private HSSFWorkbook workbook;
    private WritableResource resource;
    private int row;

    @Override
    public void write(List<? extends PositionDTO> list) throws Exception {
        System.out.println(list.size());
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }


//    @Override
//    public void write(List<? extends PositionDTO> list) throws Exception {
//        HSSFSheet sheet = workbook.getSheetAt(0);
//
//        for (PositionDTO positionDTO : list) {
//            Row sheetRow = sheet.createRow(row++);
//            Cell cell = sheetRow.createCell(0);
//            cell.setCellValue(positionDTO.getName());
//
////            c = r.createCell(1);
////            c.setCellValue(o.getBookName());
////
////            c = r.createCell(2);
////            c.setCellValue(o.getISBN());
////
////            c = r.createCell(3);
////            c.setCellValue(o.getPrice().doubleValue());
//        }
//    }
//
//    @Override
//    public void open(ExecutionContext executionContext) throws ItemStreamException {
//        workbook = new HSSFWorkbook();
//        HSSFPalette palette = workbook.getCustomPalette();
//        HSSFSheet sheet = workbook.createSheet();
//
//        resource = new FileSystemResource("/home/yaroslav-yarmoshyk/Desktop/projects/auto-order-batch/src/main/resources/output.xlsx");
//        row = 0;
//
//        createTitleRow(sheet, palette);
//        createHeaderRow(sheet);
//    }
//
//    private void createTitleRow(HSSFSheet sheet, HSSFPalette palette) {
//        HSSFColor redish = palette.findSimilarColor((byte) 0xE6, (byte) 0x50, (byte) 0x32);
//        palette.setColorAtIndex(redish.getIndex(), (byte) 0xE6, (byte) 0x50, (byte) 0x32);
//
//        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setWrapText(true);
//        headerStyle.setAlignment(HorizontalAlignment.CENTER);
//        headerStyle.setFillForegroundColor(redish.getIndex());
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        headerStyle.setBorderTop(BorderStyle.THIN);
//        headerStyle.setBorderBottom(BorderStyle.THIN);
//        headerStyle.setBorderLeft(BorderStyle.THIN);
//        headerStyle.setBorderRight(BorderStyle.THIN);
//
//        HSSFRow sheetRow = sheet.createRow(row);
//
//        Cell cell = sheetRow.createCell(0);
//        cell.setCellValue("Internal Use Only");
//        sheetRow.createCell(1).setCellStyle(headerStyle);
//        sheetRow.createCell(2).setCellStyle(headerStyle);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
//        cell.setCellStyle(headerStyle);
//
//        CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
//
//        row++;
//    }
//
//    private void createHeaderRow(HSSFSheet sheet) {
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setWrapText(true);
//        cellStyle.setAlignment(HorizontalAlignment.LEFT);
//
//        HSSFRow sheetRow = sheet.createRow(row);
//        sheetRow.setRowStyle(cellStyle);
//
//        Cell cell = sheetRow.createCell(0);
//        cell.setCellValue("Name");
//        sheet.setColumnWidth(0, poiWidth(18.0));
//
//        row++;
//    }
//
//    private int poiWidth(double width) {
//        return (int) Math.round(width * 256 + 200);
//    }
//
//    @Override
//    public void update(ExecutionContext executionContext) throws ItemStreamException {
//
//    }
//
//    @Override
//    public void close() throws ItemStreamException {
//        if (workbook == null) {
//            return;
//        }
//        createFooterRow();
//        try (BufferedOutputStream bos = new BufferedOutputStream(resource.getOutputStream())) {
//            workbook.write(bos);
//            bos.flush();
//            workbook.close();
//        } catch (IOException ex) {
//            throw new ItemStreamException("Error writing to output file", ex);
//        }
//        row = 0;
//    }
//    private void createFooterRow() {
//        HSSFSheet s = workbook.getSheetAt(0);
//        HSSFRow r = s.createRow(row);
//        Cell c = r.createCell(3);
////        c.setCellType(CellType.FORMULA);
//        c.setCellFormula(String.format("SUM(D3:D%d)", row));
//        row++;
//    }

}
