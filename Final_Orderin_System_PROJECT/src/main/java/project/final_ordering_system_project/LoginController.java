package project.final_ordering_system_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @FXML
    private Label wrongLogin;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField userText;
    @FXML
    private PasswordField passText;

    @FXML
    public void onLoginClick(ActionEvent event) {
        String username = userText.getText();
        String password = passText.getText();

        // Check username and password in the Excel "database"
        try {
            Map<String, String> users = DatabaseExcels.readUsers();
            if (users.containsKey(username)) {
                if (users.get(username).equals(password)) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();

                        Stage loginStage = (Stage) loginButton.getScene().getWindow();
                        loginStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Display error message for invalid credentials
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
                }
            } else {
                // Display alert to sign up first
                showAlert(Alert.AlertType.INFORMATION, "User Not Found", "User not found. Please sign up first.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error accessing user database.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void onSignUpClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) signUpButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            logger.error("Error loading SignUp.fxml", e);
        }
    }

    @FXML
    private void onCancelClick(ActionEvent event) {
        // Logic to handle cancel button click
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean isValidUser(String username, String password) {
        try {
            Map<String, String> users = DatabaseExcels.readUsers();
            return users.containsKey(username) && users.get(username).equals(password);
        } catch (IOException e) {
            logger.error("Error reading users from Excel", e);
            return false;
        }
    }
}
