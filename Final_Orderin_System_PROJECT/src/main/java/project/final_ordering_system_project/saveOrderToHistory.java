package project.final_ordering_system_project;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class saveOrderToHistory {

    public static void saveOrderHistoryToExcel(Map<String, List<DashboardController.Order>> orderHistoryMap) throws IOException {
        // Flatten the map into a single list
        List<DashboardController.Order> allOrders = orderHistoryMap.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Now call the method to save all orders
        saveOrderHistoryToExcel(allOrders);
    }

    public static void saveOrderHistoryToExcel(List<DashboardController.Order> orders) throws IOException {
        File file = new File("HISTORY_ORDER.xlsx");
        Workbook workbook;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new XSSFWorkbook();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            Sheet sheet = workbook.getSheet("Order History");
            if (sheet == null) {
                sheet = workbook.createSheet("Order History");
                // Add header if the sheet is newly created
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Product ID");
                headerRow.createCell(1).setCellValue("Product Name");
                headerRow.createCell(2).setCellValue("Quantity");
                headerRow.createCell(3).setCellValue("Price");
                headerRow.createCell(4).setCellValue("Order Type");
                headerRow.createCell(5).setCellValue("Order Date");
                headerRow.createCell(6).setCellValue("Order Time");
                headerRow.createCell(7).setCellValue("Mode of Payment");
                headerRow.createCell(8).setCellValue("Order Status");
                headerRow.createCell(9).setCellValue("Time Ordered");
            }

            int rowIndex = sheet.getLastRowNum() + 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:s a");

            // Add order history
            for (DashboardController.Order order : orders) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(order.getId());
                row.createCell(1).setCellValue(order.getItemName());
                row.createCell(2).setCellValue(order.getQuantity());
                row.createCell(3).setCellValue(order.getPrice());
                row.createCell(4).setCellValue(order.getOrderType());
                row.createCell(5).setCellValue(order.getOrderDate());
                row.createCell(6).setCellValue(order.getOrderTime());
                row.createCell(7).setCellValue(order.getModeOfPayment());
                row.createCell(8).setCellValue(order.getOrderStatus());
                row.createCell(9).setCellValue(LocalTime.now().format(formatter));



            }
            workbook.write(fos);
        }
    }

}
