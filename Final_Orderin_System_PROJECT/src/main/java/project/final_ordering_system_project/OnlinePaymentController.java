package project.final_ordering_system_project;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class OnlinePaymentController {

    @FXML
    private TextField billAdd;

    @FXML
    private TextField cardName;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField expDate;

    @FXML
    private TextField ccv;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private RadioButton rButtonCredit;

    @FXML
    private RadioButton rButtonDebit;

    @FXML
    private Button submit;

    @FXML
    void onCreditClick(ActionEvent event) {
        rButtonDebit.setSelected(false); // Deselect debit radio button
    }

    @FXML
    void onDebitClick(ActionEvent event) {
        rButtonCredit.setSelected(false); // Deselect credit radio button
    }

    @FXML
    void onSubmitClick(ActionEvent event) {
        String cardholderName = cardName.getText();
        String cardNum = cardNumber.getText();
        String exp = expDate.getText();
        String cvv = ccv.getText();
        String address = billAdd.getText();
        String emailAddress = email.getText();
        String phone = phoneNumber.getText();

        // Basic validation
        if (cardholderName.isEmpty() || cardNum.isEmpty() || exp.isEmpty() || cvv.isEmpty() ||
                address.isEmpty() || emailAddress.isEmpty() || phone.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill in all fields");
            return;
        }
        createPDF(cardholderName, cardNum, exp, cvv, address, emailAddress, phone);

        showAlert(AlertType.INFORMATION, "Success", "Payment submitted successfully!");
    }

    private void createPDF(String cardholderName, String cardNum, String exp, String cvv, String address, String email, String phone) {
        String dest = "customer_details.pdf";

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            // Add title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Customer Payment Details", titleFont);
            title.setAlignment(Element.ALIGN_CENTER); // Align center
            document.add(title);

            document.add(new Paragraph("\n"));

            // Add customer details with center alignment and adjusted font size
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph cardholder = new Paragraph("Cardholder Name: " + cardholderName, normalFont);
            cardholder.setAlignment(Element.ALIGN_CENTER);
            document.add(cardholder);

            Paragraph cardNumberPara = new Paragraph("Card Number: " + cardNum, normalFont);
            cardNumberPara.setAlignment(Element.ALIGN_CENTER);
            document.add(cardNumberPara);

            Paragraph expDatePara = new Paragraph("Expiration Date: " + exp, normalFont);
            expDatePara.setAlignment(Element.ALIGN_CENTER);
            document.add(expDatePara);

            Paragraph cvvPara = new Paragraph("CVV: " + cvv, normalFont);
            cvvPara.setAlignment(Element.ALIGN_CENTER);
            document.add(cvvPara);

            Paragraph addressPara = new Paragraph("Billing Address: " + address, normalFont);
            addressPara.setAlignment(Element.ALIGN_CENTER);
            document.add(addressPara);

            Paragraph emailPara = new Paragraph("Email Address: " + email, normalFont);
            emailPara.setAlignment(Element.ALIGN_CENTER);
            document.add(emailPara);

            Paragraph phonePara = new Paragraph("Phone Number: " + phone, normalFont);
            phonePara.setAlignment(Element.ALIGN_CENTER);
            document.add(phonePara);

            document.add(new Paragraph("\n"));

            // Add thank you message in italic and centered
            Font italicFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
            Paragraph thankYouMessage = new Paragraph("Thank you for your payment!", italicFont);
            thankYouMessage.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYouMessage);

            document.close();

            System.out.println("PDF created successfully.");
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to create PDF.");
        }
    }



    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
