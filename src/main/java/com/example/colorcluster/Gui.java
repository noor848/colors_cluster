package com.example.colorcluster;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Gui {
    public static final String ALERT = "Alert";
    @FXML
    private TextField epoch;
    private ColorGrid grid;
    private int input;
    private Alert warningAlert;
    @FXML
    private TextField yField;
    @FXML
    private TextField xField;
    @FXML
    private Slider red;
    @FXML
    private Slider blue;
    @FXML
    private Slider green;
    @FXML
    private Rectangle rect;
    @FXML
    private Text txtRbg;
    @FXML
    private Button chooseColors;
    @FXML
    private TextField inputColor;
    @FXML
    private Button addInputColor;
    @FXML
    private TextField learnR;
    @FXML
    private Button learnRadius;
    @FXML
    private TextField radius;
    @FXML
    private GridPane pane;

    @FXML
    private void initialize() {
        warningAlert = new Alert(AlertType.WARNING);
        warningAlert.setTitle(ALERT);
        grid = new ColorGrid();
        chooseColors.setDisable(true);
        addInputColor.setDisable(true);
        learnRadius.setDisable(true);
    }

    @FXML
    private void buildGrid() {
        if (xField.getText().isBlank() || yField.getText().isBlank()) {
            warningAlert.setHeaderText("You have to Fill Dimensions !");
            warningAlert.showAndWait();
            return;
        }
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());
        if (10 > y || y > 50 || 10 > x || x > 50) {
            warningAlert.setHeaderText("Dimensions should be less than or equal 50 !");
            warningAlert.showAndWait();
            return;
        }
        pane.getChildren().clear();
        pane.setPrefSize(x, y);
        addInputColor.setDisable(false);
        inputColor.setDisable(false);
        chooseColors.setDisable(true);
        learnRadius.setDisable(true);
        grid.fillGrid(x, y);
        grid.setX(x);
        grid.setY(y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                pane.add(new Circle(0, 0, 4, grid.getColors()[i][j]), j, i); // c
            }
        }

    }

    @FXML
    private void sliderMove() {
        rect.setFill(Color.rgb((int) red.getValue(), (int) green.getValue(), (int) blue.getValue()));
        txtRbg.setText("RGB (" + (int) red.getValue() + "," + (int) green.getValue() + "," + (int) blue.getValue() + ")");
    }

    @FXML
    private void addInputColor() {
        if (inputColor.getText().isBlank()) {
            warningAlert.setHeaderText("Enter Number of input Colors");
            warningAlert.showAndWait();
            return;
        }
        grid.setColorInputSize(Integer.parseInt(inputColor.getText().trim()));
        warningAlert.setHeaderText("You added " + grid.getColorInputSize() + " inputs");
        warningAlert.showAndWait();
        inputColor.setDisable(true);
        addInputColor.setDisable(true);
        chooseColors.setDisable(false);
        input = grid.getColorInputSize();
    }

    @FXML
    private void chooseColor() {
        int inputSize=grid.getColorInputSize();
        if (inputSize > 0) {
            grid.insertColor((int) red.getValue(), (int) green.getValue(), (int) blue.getValue());
            inputSize--;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(ALERT);
            alert.setHeaderText("Added");
            alert.showAndWait();
            inputColor.setText(Integer.toString(inputSize));
        } else {
            warningAlert.setHeaderText("you choose only " + input + "inputs ");
            warningAlert.showAndWait();
        }
        if (inputSize == 0) {
            learnRadius.setDisable(false);
        }
        grid.setColorInputSize(inputSize);
    }

    @FXML
    private void learnRadius() {
        if (learnR.getText().isBlank() || radius.getText().isBlank()) {
            warningAlert.setHeaderText("Can't be empty");
            warningAlert.showAndWait();
            return;
        }
        if (Integer.parseInt(radius.getText()) == 0) {
            warningAlert.setHeaderText("check Radius input ");
            warningAlert.showAndWait();
            return;
        }
        grid.setEpoch(!epoch.getText().isEmpty() ? Integer.parseInt(epoch.getText()) : 0);
        grid.setRadius(Integer.parseInt(radius.getText()));
        if ((Float.parseFloat(learnR.getText()) < 0) || (Float.parseFloat(learnR.getText()) > 1)) {
            warningAlert.setHeaderText("Range should be btw 0 and 1");
            warningAlert.showAndWait();
            return;
        }
        grid.setLearnRate(Float.parseFloat(learnR.getText()));
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(ALERT);
        alert.setHeaderText("Added  ");
        alert.showAndWait();
        learnRadius.setDisable(true);
    }


    public void findSolution() {
        grid.solve();
        for (int i = 0; i < grid.getX(); i++) {
            for (int j = 0; j < grid.getY(); j++) {
                pane.add(new Circle(0, 0, 4, grid.getColors()[i][j]), j, i); // c
            }
        }


    }
}



