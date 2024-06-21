package project.final_ordering_system_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Map;

public class mainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(mainApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 699, 429);
        stage.setScene(scene);
        stage.setTitle("Filipino Bakes est. 2024");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // Method to authenticate user
    public static boolean authenticateUser(String username, String password) {
        try {
            Map<String, String> users = DatabaseExcels.readUsers();
            return users.containsKey(username) && users.get(username).equals(password);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
