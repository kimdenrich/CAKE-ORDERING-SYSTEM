package project.final_ordering_system_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class SignUpController {

    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField confirm_password;
    @FXML
    private TextField username;
    @FXML
    private TextField new_password;
    @FXML
    private Button signupButton;
    @FXML
    private Button cancelButton;

    @FXML
    public void onSignUpClick(ActionEvent event) {
        String user = username.getText();
        String password = new_password.getText();
        String confirmPassword = confirm_password.getText();
        String firstName = fname.getText();
        String lastName = lname.getText();

        if (!user.isBlank() && !password.isBlank() && !firstName.isBlank() && !lastName.isBlank() && password.equals(confirmPassword)) {
            try {
                DatabaseExcels.writeUser(firstName, lastName, user, password);
                showInformationDialog("Sign-up successful. Thank you for signing up!");

                // Load the login scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage currentStage = (Stage) signupButton.getScene().getWindow();

                // Create a new scene with the login layout
                Scene loginScene = new Scene(root);
                currentStage.setScene(loginScene);
                currentStage.setTitle("Filipino Bakes");
                currentStage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showErrorDialog("Failed to save user information or load the login screen.");
            }
        } else {
            if (user.isBlank() || password.isBlank() || firstName.isBlank() || lastName.isBlank()) {
                showErrorDialog("All fields are required.");
            } else if (!password.equals(confirmPassword)) {
                showErrorDialog("Passwords do not match.");
            }
        }
    }


    @FXML
    public void onCancelClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            stage.setTitle("Filipino Bakes");
            Stage loginStage = (Stage) cancelButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInformationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
