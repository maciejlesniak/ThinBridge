package pl.sparkidea.bridge;

import pl.sparkidea.bridge.controll.Dispatcher;
import pl.sparkidea.bridge.controll.sides.Side;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Dispatcher dispatcher;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("Bridge");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        // activate dispatcher
        Map<Side, Integer> initialCarNumbers = new HashMap<>();
        initialCarNumbers.put(Side.WEST, 10);
        initialCarNumbers.put(Side.EAST, 20);
        this.dispatcher = new Dispatcher(initialCarNumbers);
        this.dispatcher.setActiveSite(Side.WEST);
        this.dispatcher.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }
}
