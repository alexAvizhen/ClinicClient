package com.bsuir.lagunovskaya.clinic.client.utils;

import com.bsuir.lagunovskaya.clinic.client.ui.tables.models.AppointmentsTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static String saveAppointmentsToExcel(String tabName, AppointmentsTableModel appointmentsTableModel) {
        try {

            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(tabName);

            for (int i = 0; i <= appointmentsTableModel.getRowCount(); i++) {
                Row currentRow = getRowOrCreate(sheet, i);
                for (int j = 0; j < appointmentsTableModel.getColumnCount(); j++) {
                    Cell currentCell = getCellOrCreate(currentRow, j);
                    if (i == 0) {
                        currentCell.setCellValue(appointmentsTableModel.getColumnName(j));
                    } else {
                        Object cellValue = appointmentsTableModel.getValueAt(i - 1, j);
                        if (cellValue != null) {
                            currentCell.setCellValue(cellValue.toString());
                        } else {
                            currentCell.setCellValue("");

                        }
                    }
                }
            }
            String pathToResultFile = "src/main/resources/result.xls";
            FileOutputStream outputStream = new FileOutputStream(pathToResultFile);
            workbook.write(outputStream);
            workbook.close();
            return pathToResultFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Row getRowOrCreate(Sheet sheet, int rowNumb) {
        Row resultRow = sheet.getRow(rowNumb);
        if (resultRow == null) {
            resultRow = sheet.createRow(rowNumb);
        }
        return resultRow;
    }

    private static Cell getCellOrCreate(Row row, int columnNumb) {
        Cell resultCell = row.getCell(columnNumb);
        //Create the cell if it doesn't exist
        if (resultCell == null) {
            resultCell = row.createCell(columnNumb);
            CellStyle cellStyle = resultCell.getCellStyle();
            cellStyle.setWrapText(true);
            resultCell.setCellStyle(cellStyle);
        }
        return resultCell;
    }
}
