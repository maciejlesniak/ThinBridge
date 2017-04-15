package pl.sparkidea.bridge.controller;

import pl.sparkidea.bridge.logic.Dispatcher;
import pl.sparkidea.bridge.logic.events.EventHandler;
import pl.sparkidea.bridge.logic.sides.Side;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller implements Initializable {
    private final Integer INITIAL_CARS_AMOUNT = 10;

    private Dispatcher dispatcher;
    private boolean isButtonStart;

    @FXML
    private Button btnStartStop;
    @FXML
    private Circle lightWest;
    @FXML
    private Circle lightEast;
    @FXML
    private ImageView imgCar;
    @FXML
    private ImageView imgArrowEast;
    @FXML
    private ImageView imgArrowWest;
    @FXML
    private Label labelCarsAmountWest;
    @FXML
    private Label labelCarsAmountEast;
    @FXML
    private TextField textCarsAmount;
    @FXML
    private ComboBox<String> comboSide;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isButtonStart = true;

        // activate dispatcher
        Map<Side, Integer> initialCarNumbers = new HashMap<>();
        initialCarNumbers.put(Side.WEST, INITIAL_CARS_AMOUNT);
        initialCarNumbers.put(Side.EAST, INITIAL_CARS_AMOUNT);
        this.dispatcher = new Dispatcher(initialCarNumbers, onCarsAmountChangedEvent());

        // labels
        labelCarsAmountEast.setText(INITIAL_CARS_AMOUNT.toString());
        labelCarsAmountWest.setText(INITIAL_CARS_AMOUNT.toString());

        // combo box
        textCarsAmount.setText(String.valueOf(INITIAL_CARS_AMOUNT));
        comboSide.getItems().addAll(Side.EAST.name(), Side.WEST.name());

        // side init
        switchSidesTo(Side.NONE);
    }

    public void onStartStopClick(MouseEvent mouseEvent) {
        if (isButtonStart) {
            btnStartStop.setText("Stop");
            switchSidesTo(Side.EAST);
        } else {
            btnStartStop.setText("Start");
            dispatcher.stop();
        }
        isButtonStart = !isButtonStart;
    }

    public void onAddTenWestClick(MouseEvent mouseEvent) {
        dispatcher.addCarsToSide(Side.WEST, INITIAL_CARS_AMOUNT);
    }

    public void onAddTenEastClick(MouseEvent mouseEvent) {
        dispatcher.addCarsToSide(Side.EAST, INITIAL_CARS_AMOUNT);
    }

    public void onAddAmountNowClick(MouseEvent mouseEvent) {
        Integer carsAmount = Integer.parseInt(textCarsAmount.getText());
        String sideName = comboSide.getSelectionModel().getSelectedItem();

        if (sideName.equals(Side.EAST.name())) {
            dispatcher.addCarsToSide(Side.EAST, carsAmount);
        } else if (sideName.equals(Side.WEST.name())) {
            dispatcher.addCarsToSide(Side.WEST, carsAmount);
        }
    }

    private void switchSidesTo(Side activeSide) {
        if (activeSide.equals(Side.EAST)) {
            imgCar.setVisible(true);
            imgArrowEast.setVisible(true);
            imgArrowWest.setVisible(true);

            imgCar.rotateProperty().setValue(0);
            imgArrowEast.rotateProperty().setValue(180);
            imgArrowWest.rotateProperty().setValue(180);

            lightWest.setFill(Color.RED);
            lightEast.setFill(Color.GREEN);

            dispatcher.setActiveSite(Side.EAST);
        } else if(activeSide.equals(Side.WEST)) {
            imgCar.setVisible(true);
            imgArrowEast.setVisible(true);
            imgArrowWest.setVisible(true);

            imgCar.rotateProperty().setValue(180);
            imgArrowEast.rotateProperty().setValue(0);
            imgArrowWest.rotateProperty().setValue(0);

            lightWest.setFill(Color.GREEN);
            lightEast.setFill(Color.RED);

            dispatcher.setActiveSite(Side.WEST);
        } else {
            imgCar.setVisible(false);
            imgArrowEast.setVisible(false);
            imgArrowWest.setVisible(false);

            lightWest.setFill(Color.RED);
            lightEast.setFill(Color.RED);

            dispatcher.setActiveSite(Side.NONE);
        }
    }

    private EventHandler<Integer> onCarsAmountChangedEvent() {
        return (side, count) -> {
            if (side.equals(Side.WEST)) {
                Platform.runLater(() -> {
                    labelCarsAmountWest.setText(count.toString());
                    if (count == 0) {
                        switchSidesTo(Side.EAST);
                    }
                });
            } else if (side.equals(Side.EAST)) {
                Platform.runLater(() -> {
                    labelCarsAmountEast.setText(count.toString());
                    if (count == 0) {
                        switchSidesTo(Side.WEST);
                    }
                });
            }
        };
    }

}
