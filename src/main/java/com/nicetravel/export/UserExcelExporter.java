package com.nicetravel.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.nicetravel.entity.Account;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Account> listAccounts;

    public UserExcelExporter(List<Account> listAccounts) {
        this.listAccounts = listAccounts;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.GREEN.getIndex());
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Username", style);
        createCell(row, 2, "FullName", style);
        createCell(row, 3, "E-mail", style);
        createCell(row, 4, "Gender", style);
        createCell(row, 5, "Address", style);
        createCell(row, 6, "Phone", style);
        createCell(row, 7, "Image", style);
        createCell(row, 8, "ID Card", style);
        createCell(row, 9, "Role", style);
        createCell(row, 10, "Created Date", style);
        createCell(row, 11, "isDelete", style);
        createCell(row, 12, "Provider", style);
        createCell(row, 13, "Password Changed Time", style);


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Account user : listAccounts) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getUsername(), style);
            createCell(row, columnCount++, user.getFullname(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getGender() ? "Nam" : "Nữ", style);
            createCell(row, columnCount++, user.getAddress(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getImg(), style);
            createCell(row, columnCount++, user.getId_Card(), style);
            createCell(row, columnCount++, user.getRole_Id().getRole(), style);
            createCell(row, columnCount++, String.valueOf(user.getCreatedDate()), style);
            createCell(row, columnCount++, user.getIsEnable() ? "Vô hiệu hóa" : "Kích hoạt", style);
            createCell(row, columnCount++, String.valueOf(user.getProvider()), style);
            createCell(row, columnCount++, String.valueOf(user.getPasswordChangedTime()), style);


        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
