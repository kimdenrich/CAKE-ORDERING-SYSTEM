package project.final_ordering_system_project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseExcels {
    private static final String FILE_PATH = "userLogin.xlsx";

    public static Map<String, String> readUsers() throws IOException {
        Map<String, String> users = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell usernameCell = row.getCell(0);
                Cell passwordCell = row.getCell(1);
                if (usernameCell != null && passwordCell != null) {
                    users.put(usernameCell.getStringCellValue(), passwordCell.getStringCellValue());
                }
            }
        }
        return users;
    }

    public static void writeUser(String firstName, String lastName, String username, String password) throws IOException {
        Workbook workbook;
        Sheet sheet;

        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH)) {
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheetAt(0);
        } catch (IOException e) {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Users");
        }

        int rowCount = sheet.getLastRowNum();
        Row newRow = sheet.createRow(rowCount + 1);
        newRow.createCell(0).setCellValue(username);
        newRow.createCell(1).setCellValue(password);
        newRow.createCell(2).setCellValue(firstName);
        newRow.createCell(3).setCellValue(lastName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(fileOutputStream);
        } finally {
            workbook.close();
        }
    }
}
