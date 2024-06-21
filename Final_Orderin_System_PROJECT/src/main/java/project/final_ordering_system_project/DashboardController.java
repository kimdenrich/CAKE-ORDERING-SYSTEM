package project.final_ordering_system_project;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class DashboardController {

    @FXML private AnchorPane mainPane;
    @FXML private TabPane MainTab;
    @FXML private TextArea addressArea;
    @FXML private Button chiffonCakeBtn;
    @FXML private Tab chiffonCakeTab;
    @FXML private TextField contact;
    @FXML private Button deleteOrderBtn;
    @FXML private Button fruitCakeBtn;
    @FXML private Tab fruitCakeTab;
    @FXML private Button logout;
    @FXML private TextField nameF;
    @FXML private Button checkOutBtn;
    @FXML private Button orderBtn1;
    @FXML private Button orderBtn10;
    @FXML private Button orderBtn11;
    @FXML private Button orderBtn12;
    @FXML private Button orderBtn13;
    @FXML private Button orderBtn14;
    @FXML private Button orderBtn15;
    @FXML private Button orderBtn16;
    @FXML private Button orderBtn17;
    @FXML private Button orderBtn18;
    @FXML private Button orderBtn19;
    @FXML private Button orderBtn2;
    @FXML private Button orderBtn20;
    @FXML private Button orderBtn3;
    @FXML private Button orderBtn4;
    @FXML private Button orderBtn5;
    @FXML private Button orderBtn6;
    @FXML private Button orderBtn7;
    @FXML private Button orderBtn8;
    @FXML private Button orderBtn9;
    @FXML private Label priceLabel0;
    @FXML private Label priceLabel1;
    @FXML private Label priceLabel10;
    @FXML private Label priceLabel11;
    @FXML private Label priceLabel12;
    @FXML private Label priceLabel13;
    @FXML private Label priceLabel14;
    @FXML private Label priceLabel15;
    @FXML private Label priceLabel17;
    @FXML private Label priceLabel19;
    @FXML private Label priceLabel2;
    @FXML private Label priceLabel20;
    @FXML private Label priceLabel21;
    @FXML private Label priceLabel5;
    @FXML private Label priceLabel6;
    @FXML private Label priceLabel7;
    @FXML private Label priceLabel8;
    @FXML private Label priceLabel9;
    @FXML private Label priceLabel4;
    @FXML private TableColumn<Order, Double> priceColumn;
    @FXML private TableColumn<Order, Integer> productIDColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;
    @FXML private TableColumn<Order, String> productNameColumn;
    @FXML private Spinner<Integer> spinner1;
    @FXML private Spinner<Integer> spinner10;
    @FXML private Spinner<Integer> spinner11;
    @FXML private Spinner<Integer> spinner12;
    @FXML private Spinner<Integer> spinner13;
    @FXML private Spinner<Integer> spinner14;
    @FXML private Spinner<Integer> spinner15;
    @FXML private Spinner<Integer> spinner16;
    @FXML private Spinner<Integer> spinner17;
    @FXML private Spinner<Integer> spinner18;
    @FXML private Spinner<Integer> spinner19;
    @FXML private Spinner<Integer> spinner20;
    @FXML private Spinner<Integer> spinner2;
    @FXML private Spinner<Integer> spinner3;
    @FXML private Spinner<Integer> spinner4;
    @FXML private Spinner<Integer> spinner5;
    @FXML private Spinner<Integer> spinner6;
    @FXML private Spinner<Integer> spinner7;
    @FXML private Spinner<Integer> spinner8;
    @FXML private Spinner<Integer> spinner9;
    @FXML private TableView<Order> tableView;
    @FXML private Label total;
    @FXML private Button traditionalCakeBtn;
    @FXML private Tab traditionalCakeTab;
    @FXML private ComboBox<String> selectOrderOptions;
    @FXML private ComboBox<String> modeOfPayment;
    @FXML private DatePicker datePicker;
    @FXML private TextField enterTime;
    @FXML private Button confirm;

    private final Map<String, List<Order>> orderHistoryMap = new HashMap<>();
    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<String> paymentOptions = FXCollections.observableArrayList("Cash on Delivery", "Online Payment");
    private final ObservableList<String> orderTypes = FXCollections.observableArrayList("Delivery", "Pickup");
    private final ObservableList<String> orderStats = FXCollections.observableArrayList("Pending", "Delivered", "Cancelled");
    private final List<Order> orderHistory = new ArrayList<>();

    @FXML
    public void initialize() {
        // Set options for the ComboBox
        modeOfPayment.setItems(paymentOptions);
        selectOrderOptions.setItems(orderTypes);

        // Add listener to ComboBox to handle selection changes
        modeOfPayment.setOnAction(this::handleModeOfPaymentSelection);
        selectOrderOptions.setOnAction(event -> handleOrderTypeSelection());

        // Add listener to the Confirm Order button
        confirm.setOnAction(event -> handleConfirmOrder());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        productIDColumn.setCellValueFactory(cellData -> {
            int idValue = cellData.getValue().getId();
            return new SimpleIntegerProperty(idValue).asObject();
        });

        if (spinner1 != null) {
            spinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner2 != null) {
            spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner3 != null) {
            spinner3.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner4 != null) {
            spinner4.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner5 != null) {
            spinner5.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner6 != null) {
            spinner6.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner7 != null) {
            spinner7.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner8 != null) {
            spinner8.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner9 != null) {
            spinner9.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner10 != null) {
            spinner10.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner11 != null) {
            spinner11.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner12 != null) {
            spinner12.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner13 != null) {
            spinner13.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner14 != null) {
            spinner14.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner15 != null) {
            spinner15.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner16 != null) {
            spinner16.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner17 != null) {
            spinner17.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner18 != null) {
            spinner18.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner19 != null) {
            spinner19.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }
        if (spinner20 != null) {
            spinner20.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        }

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Bind the orders list to the TableView
        tableView.setItems(orders);
    }


    @FXML
    void onActionOrder1(ActionEvent event) {
        addOrder("Bibingka", spinner1.getValue(), 25.00);
    }

    @FXML
    void onActionOrder2(ActionEvent event) {
        addOrder("Biko", spinner2.getValue(), 25.00);
    }

    @FXML
    void onActionOrder3(ActionEvent event) {
        addOrder("Pichi-Pichi", spinner3.getValue(), 25.00);
    }

    @FXML
    void onActionOrder4(ActionEvent event) {
        addOrder("Torta de Cebu", spinner4.getValue(), 25.00);
    }

    @FXML
    void onActionOrder5(ActionEvent event) {
        addOrder("Suman", spinner5.getValue(), 25.00);
    }

    @FXML
    void onActionOrder6(ActionEvent event) {
        addOrder("Puto Cheese", spinner6.getValue(), 25.00);
    }

    @FXML
    void onActionOrder7(ActionEvent event) {
        addOrder("Kutsinta", spinner7.getValue(), 25.00);
    }

    @FXML
    void onActionOrder8(ActionEvent event) {
        addOrder("Palitaw", spinner8.getValue(), 25.00);
    }

    @FXML
    void onActionOrder9(ActionEvent event) {
        addOrder("Pineapple", spinner9.getValue(), 50.00);
    }

    @FXML
    void onActionOrder10(ActionEvent event) {
        addOrder("Mango Float", spinner10.getValue(), 50.00);
    }

    @FXML
    void onActionOrder11(ActionEvent event) {
        addOrder("Ube Cake", spinner11.getValue(), 50.00);
    }

    @FXML
    void onActionOrder12(ActionEvent event) {
        addOrder("Yema Cake", spinner12.getValue(), 30.00);
    }

    @FXML
    void onActionOrder13(ActionEvent event) {
        addOrder("Banana Cake", spinner13.getValue(), 20.00);
    }

    @FXML
    void onActionOrder14(ActionEvent event) {
        addOrder("Strawberry Cake", spinner14.getValue(), 30.00);
    }

    @FXML
    void onActionOrder15(ActionEvent event) {
        addOrder("Brazo de Mercedes", spinner15.getValue(), 30.00);
    }

    @FXML
    void onActionOrder16(ActionEvent event) {
        addOrder("Orange Chiffon Cake", spinner16.getValue(), 30.00);
    }

    @FXML
    void onActionOrder17(ActionEvent event) {
        addOrder("Ube Chiffon Cake", spinner17.getValue(), 30.00);
    }

    @FXML
    void onActionOrder18(ActionEvent event) {
        addOrder("Mocha Cake", spinner18.getValue(), 25.00);
    }

    @FXML
    void onActionOrder19(ActionEvent event) {
        addOrder("Lemon Chiffon Cake", spinner19.getValue(), 30.00);
    }

    @FXML
    void onActionOrder20(ActionEvent event) {
        addOrder("Sapin-Sapin", spinner20.getValue(), 25.00);
    }


    // Similar methods for other order buttons...
    @FXML
    void onTraditionalCakeClick(ActionEvent event) {
        MainTab.getSelectionModel().select(0);
    }

    @FXML
    void onFruitCakeClick(ActionEvent event) {
        MainTab.getSelectionModel().select(1);
    }

    @FXML
    void onChiffonCakeClick(ActionEvent event) {
        MainTab.getSelectionModel().select(2);
    }

    //handle selec
    private void handleOrderTypeSelection() {
        String selectedOrderType = selectOrderOptions.getValue();
        if (selectedOrderType != null) {
            datePicker.setDisable(false);
            enterTime.setDisable(false);
        }
    }


    @FXML
    private void handleModeOfPaymentSelection(ActionEvent event) {
        String selectedMode = modeOfPayment.getValue();
        if (selectedMode != null) {
            if (selectedMode.equals("Cash on Delivery")) {
                // Handle Cash on Delivery mode
                System.out.println("Cash on Delivery selected. No online payment required.");
            } else if (selectedMode.equals("Online Payment")) {
                // Handle Online Payment mode
                loadOnlinePaymentForm();
            }
        }
    }

    private void loadOnlinePaymentForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("onlinePayment.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Debit and Credit Card");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPaymentDetailsClick(ActionEvent event){
        String filePath = "customer_details.pdf"; // Replace with your actual file path

        File file = new File(filePath);
        if (file.exists()) {
            try {
                // Open PDF file using Desktop
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to open PDF file.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "PDF file not found.");
        }

    }

    private void handleConfirmOrder() {
        String selectedModeOfPayment = modeOfPayment.getValue();
        String selectedOrderType = selectOrderOptions.getValue();
        LocalDate selectedDate = datePicker.getValue();
        String enteredTime = enterTime.getText();

        if (selectedModeOfPayment == null || selectedOrderType == null || selectedDate == null || enteredTime.isEmpty()) {
            showAlert("Error", "Please select a mode of payment, order type, date, and enter a time.");
            return;
        }

        // Validate entered time
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
            LocalTime.parse(enteredTime, timeFormatter);
        } catch (Exception e) {
            showAlert("Error", "Please enter a valid time in HH:mm a format.");
            return;
        }
        for (Order order : orders) {
            order.setModeOfPayment(selectedModeOfPayment);
            order.setOrderType(selectedOrderType);
            order.setOrderDate(selectedDate.toString());
            order.setOrderTime(enteredTime);
        }

        // Process the order here
        String orderDetails = String.format("Mode of Payment: %s\nOrder Type: %s\nDate: %s\nTime: %s",
                selectedModeOfPayment, selectedOrderType, selectedDate, enteredTime);
        showAlert("Order Confirmed", orderDetails);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onDeleteOrderClick(ActionEvent event) {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedOrder == null) {
            // Show an error message if no item is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Selection");
            alert.setContentText("Please select an order to delete.");
            alert.showAndWait();
            return;
        }

        // Confirmation dialog (optional)
        if (confirmDelete(selectedOrder)) {
            // Set the order status to "Cancelled"
            selectedOrder.setOrderStatus("Cancelled");

            // Remove the selected item from the orders list
            orders.remove(selectedOrder);

            // Update the visual representation of order status
            updateOrderStatusVisuals();
        }
    }

    private void updateOrderStatusVisuals() {
        // Loop through the items in the table and update visuals based on order status
        for (Order order : tableView.getItems()) {
            TableRow<Order> row = getTableRow(order); // Get the row for the order
            if (row != null && order.getOrderStatus().equals("Cancelled")) {
                // You can update row style or any visual representation here
                row.setStyle("-fx-background-color: #FFCCCC;"); // Change background color for cancelled orders
            }
        }
    }

    // Method to get TableRow for a specific item in the TableView
    private TableRow<Order> getTableRow(Order order) {
        for (Node node : tableView.lookupAll("TableRow")) {
            if (node instanceof TableRow) {
                TableRow<Order> row = (TableRow<Order>) node;
                if (row.getItem() == order) {
                    return row;
                }
            }
        }
        return null; // Return null if row not found
    }



    private void updateTotalPrice() {
        double totalPrice = orders.stream()
                .mapToDouble(order -> order.getQuantity() * order.getPrice())
                .sum();
        total.setText(String.format("%.2f", totalPrice));
    }

    private boolean confirmDelete(Order order) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Order Confirmation");
        confirmation.setHeaderText("Are you sure you want to delete the following order?");
        confirmation.setContentText(STR."\{order.getItemName()} (Quantity: \{order.getQuantity()})");
        confirmation.getButtonTypes().set(0, ButtonType.OK);
        confirmation.getButtonTypes().set(1, ButtonType.CANCEL);
        Optional<ButtonType> result = confirmation.showAndWait();
        return result.get() == ButtonType.OK;
    }


    @FXML
    void onLogoutClick(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) logout.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addOrder(String itemName, int quantity, double price) {
        Order order = new Order(itemName, quantity, quantity * price);
        tableView.getItems().add(order);
        updateTotalPrice();
    }

    @FXML
    void onCheckoutClick(ActionEvent event) {
        if (orders.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No Orders");
            alert.setContentText("There are no orders to place.");
            alert.showAndWait();
            return;
        }

        try {
            saveOrdersToExcel();
            generatePDFReceipt();
            openPDF("receipt.pdf");

            for (Order order : orders) {
                order.setModeOfPayment(modeOfPayment.getValue());
                order.setOrderType(selectOrderOptions.getValue());
                order.setOrderDate(datePicker.getValue().toString());
                order.setOrderTime(enterTime.getText());
            }

            orderHistory.addAll(orders);
            orders.clear();
            updateTotalPrice();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Order Failed");
            alert.setContentText("An error occurred while checking out your order.");
            alert.showAndWait();
        } catch (DocumentException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PDF Generation Failed");
            alert.setContentText("An error occurred while generating PDF receipt.");
            alert.showAndWait();
        }
    }

    private void saveOrdersToExcel() throws IOException {
        try (FileOutputStream fos = new FileOutputStream("orders.xlsx");
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Orders");

            int rowIndex = 0;

            // Add customer details
            Row customerDetailsHeader = sheet.createRow(rowIndex++);
            customerDetailsHeader.createCell(0).setCellValue("Customer Details");

            Row nameRow = sheet.createRow(rowIndex++);
            nameRow.createCell(0).setCellValue("Name");
            nameRow.createCell(1).setCellValue(nameF.getText());

            Row contactRow = sheet.createRow(rowIndex++);
            contactRow.createCell(0).setCellValue("Contact");
            contactRow.createCell(1).setCellValue(contact.getText());

            Row addressRow = sheet.createRow(rowIndex++);
            addressRow.createCell(0).setCellValue("Address");
            addressRow.createCell(1).setCellValue(addressArea.getText());

            Row dateRow = sheet.createRow(rowIndex++);
            dateRow.createCell(0).setCellValue("Date");
            dateRow.createCell(1).setCellValue(getCurrentDate());

            rowIndex++; // Skip a row for spacing

            // Add order details header
            Row headerRow = sheet.createRow(rowIndex++);
            headerRow.createCell(0).setCellValue("Product ID");
            headerRow.createCell(1).setCellValue("Product Name");
            headerRow.createCell(2).setCellValue("Quantity");
            headerRow.createCell(3).setCellValue("Price");
            headerRow.createCell(4).setCellValue("Status");


            // Add order details
            for (Order order : orders) {
                placeOrder(order);
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(order.getId());
                row.createCell(1).setCellValue(order.getItemName());
                row.createCell(2).setCellValue(order.getQuantity());
                row.createCell(3).setCellValue(order.getPrice());
                row.createCell(4).setCellValue(order.getOrderStatus());

            }

            workbook.write(fos);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void generatePDFReceipt() throws DocumentException, FileNotFoundException {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("receipt.pdf"));
            document.open();

            // Add customer details
            document.add(new Paragraph("Customer Details"));
            document.add(new Paragraph(STR."Date: \{getCurrentDate()}"));
            document.add(new Paragraph(STR."Name: \{nameF.getText()}"));
            document.add(new Paragraph(STR."Contact: \{contact.getText()}"));
            document.add(new Paragraph(STR."Address: \{addressArea.getText()}"));
            document.add(new Paragraph(STR."Mode of Payment: \{modeOfPayment.getValue()}"));
            document.add(new Paragraph(STR."Order Type: \{selectOrderOptions.getValue()}"));
            document.add(new Paragraph(STR."Order Date: \{datePicker.getValue()}"));
            document.add(new Paragraph(STR."Order Time: \{enterTime.getText()}"));
            document.add(new Paragraph("=========================================================================="));// Add an empty line

            PdfPTable table = new PdfPTable(4);
            table.addCell("Product ID");
            table.addCell("Product Name");
            table.addCell("Quantity");
            table.addCell("Price");

            for (Order order : orders) {
                table.addCell(String.valueOf(order.getId()));
                table.addCell(order.getItemName());
                table.addCell(String.valueOf(order.getQuantity()));
                table.addCell(String.valueOf(order.getPrice()));
            }

            document.add(table);
            document.add(new Paragraph(STR."Total: \{total.getText()}"));
        } finally {
            document.close();
        }
    }

    private void openPDF(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                // Desktop is not supported
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Desktop Not Supported");
                alert.setContentText("Cannot open PDF. Desktop is not supported on this platform.");
                alert.showAndWait();
            }
        } else {
            // File does not exist
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("File Not Found");
            alert.setContentText("Cannot open PDF. File not found.");
            alert.showAndWait();
        }
    }

    public void placeOrder(Order order) {
        // Code to handle placing the order

        // Now, save the order history to Excel
        try {
            saveOrderHistory(order);
            // Optionally, provide feedback to the user
            showAlert(Alert.AlertType.INFORMATION, "Order History Saved", "Order history saved successfully.");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your application's logic
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save order history.");
        }
    }

    // Method to save order history to Excel
    private void saveOrderHistory(Order order) throws IOException {
        // Retrieve existing order history or create a new one
        Map<String, List<Order>> orderHistoryMap = loadOrderHistory();

        // Update order history with the new order
        String productId = order.getItemName(); // Assuming itemName as product ID
        List<Order> orders = orderHistoryMap.getOrDefault(productId, new ArrayList<>());
        orders.add(order);
        orderHistoryMap.put(productId, orders);

        // Save the updated order history to Excel
        saveOrderToHistory.saveOrderHistoryToExcel(orderHistoryMap);
    }

    // Method to load existing order history
    private Map<String, List<Order>> loadOrderHistory() {
        return new HashMap<>();
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static class Order {

        public static int counter = 1001;

        public double getSingleItemTotalPrice() {
            return price;
        }
        private final String itemName;
        private final int quantity;
        private final double price;
        private final int id;
        private final int totalPrice;

        private String orderStatus;

        private String orderType; // Delivery or Pickup
        private String orderDate; // Date for delivery or pickup
        private String orderTime; // Time for delivery or pickup

        private String modeOfPayment;

        private final String date;




        public Order( String itemName, int quantity, double unitPrice) {
            this.id = counter++;
            this.date = null;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = unitPrice;
            this.totalPrice = (int) (quantity * unitPrice);
            this.orderStatus = "Pending";
        }

        public String getDate(){
            return date;
        }

        public int getId(){
            return id;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }


        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public void setModeOfPayment(String modePayment){
            this.modeOfPayment = modePayment;
        }
        public String getModeOfPayment(){
            return modeOfPayment;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatus() {
            return orderStatus;
        }
    }
}

