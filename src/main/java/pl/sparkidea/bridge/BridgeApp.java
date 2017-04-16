package pl.sparkidea.bridge;

import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BridgeApp extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL rootWindow = getClass().getClassLoader().getResource("main.fxml");
        if (rootWindow != null) {
            Parent root = FXMLLoader.load(rootWindow);
            primaryStage.setTitle("Bridge");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
