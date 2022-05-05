package com.example.colorcluster;

import javafx.event.ActionEvent;
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

    public Button start;
    public TextField epic;
    ColorGrid grid;
    int input;
    Alert warningAlert;
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
        warningAlert.setTitle("Alert");
        grid = new ColorGrid();
        chooseColors.setDisable(true);
        addInputColor.setDisable(true);
        learnRadius.setDisable(true);

    }

    @FXML
    private void buildGrid() {
        if (!xField.getText().isBlank() && !yField.getText().isBlank()) {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            System.out.println(x + "," + y);
            if (10 <= y && y <= 50 && 10 <= x && x <= 50) {
                pane.getChildren().clear();
                pane.setPrefSize(x, y);
                addInputColor.setDisable(false);
                inputColor.setDisable(false);
                chooseColors.setDisable(true);
                learnRadius.setDisable(true);
                grid.fillGrid(x, y);
                System.out.println("Length is " + grid.colors.length);
                grid.x=x;
                grid.y=y;
                for (int i = 0; i < x; i++)
                    for (int j = 0; j < y; j++)
                        pane.add(new Circle(0, 0, 4, grid.colors[i][j]), j, i); // c

            } else {
                warningAlert.setHeaderText("Dimensions should be less than or equal 50 !");
                warningAlert.showAndWait();
            }
        } else {
            warningAlert.setHeaderText("You have to Fill Dimensions !");
            warningAlert.showAndWait();

        }
    }

    @FXML
    private void sliderMove() {
        rect.setFill(Color.rgb((int) red.getValue(), (int) green.getValue(), (int) blue.getValue()));
        txtRbg.setText("RGB (" + (int) red.getValue() + "," + (int) green.getValue() + "," + (int) blue.getValue() + ")");


    }

    @FXML
    private void addInputColor() {
        if (!inputColor.getText().isBlank()) {
            grid.colorInputSize = Integer.parseInt(inputColor.getText().trim());
            warningAlert.setHeaderText("YOu added" + grid.colorInputSize + "Inputs");
            warningAlert.showAndWait();
            inputColor.setDisable(true);
            addInputColor.setDisable(true);
            chooseColors.setDisable(false);

            input = grid.colorInputSize;
        } else {
            warningAlert.setHeaderText("Enter Number of input Colors");
            warningAlert.showAndWait();

        }


    }

    @FXML
    private void chooseColor() {
        if (grid.colorInputSize > 0) {
            grid.insertColor((int) red.getValue(), (int) green.getValue(), (int) blue.getValue());
            grid.colorInputSize--;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Added  ");
            alert.showAndWait();
            inputColor.setText(Integer.toString(grid.colorInputSize));
        } else {
            warningAlert.setHeaderText("you choose only " + input + "inputs ");
            warningAlert.showAndWait();
        }
        if (grid.colorInputSize == 0) {
            learnRadius.setDisable(false);
        }

    }

    @FXML
    private void learnRadius() {
        if (!learnR.getText().isBlank() && !radius.getText().isBlank()) {

            if (Integer.parseInt(radius.getText()) != 0 ) {
                grid.epic = !epic.getText().isEmpty()?Integer.parseInt(epic.getText()):0;
                grid.radius = Integer.parseInt(radius.getText());
                if (Float.parseFloat(learnR.getText())>= 0 && Float.parseFloat(learnR.getText())<=1) {
                    grid.learnRate = Float.parseFloat(learnR.getText());

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Added  ");
                    alert.showAndWait();
                    learnRadius.setDisable(true);
                }else {
                    warningAlert.setHeaderText("Range should be btw 0 and 1");
                    warningAlert.showAndWait();


                }
            } else {
                warningAlert.setHeaderText("check Radius input ");
                warningAlert.showAndWait();


            }


        } else {
            warningAlert.setHeaderText(" Can't be empty ");
            warningAlert.showAndWait();


        }

    }


    public void findSolution(ActionEvent actionEvent) {

        grid.solve();
        for (int i = 0; i < grid.x; i++)
            for (int j = 0; j < grid.y; j++)
                pane.add(new Circle(0, 0, 4, grid.colors[i][j]), j, i); // c


    }
}



