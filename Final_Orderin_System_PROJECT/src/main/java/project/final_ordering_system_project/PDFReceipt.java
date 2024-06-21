package project.final_ordering_system_project;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFReceipt {
    public void createPDF() {
        // Create Document
        Document document = new Document();
        try {
            // Create PdfWriter instance
            PdfWriter.getInstance(document, new FileOutputStream("receipt.pdf"));

            // Open the document before adding content
            document.open();

            // Add content to the document
            document.add(new Paragraph("Your content goes here."));

            // Close the document
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
