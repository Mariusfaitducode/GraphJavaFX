package graphproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AppController {


    @FXML
    private Pane centerPane, popupPane;

    @FXML
    private RadioButton rbutton1, rbutton2, rbutton3;

    @FXML
    private Label graphTitle;

    @FXML
    private TextField nameGraph, nodesNumber;

//    AppController() {
//        popupPane.setVisible(false);
//    }

    private final GraphController graphController = new GraphController();

    @FXML
    protected void createNewGraphPopup() {
        popupPane.setVisible(true);
    }

    public void generateGraph() {
        graphTitle.setText(nameGraph.getText());
        if (rbutton1.isSelected()) {
            graphController.generateEmptyGraph(nameGraph.getText(), centerPane);
        } else if (rbutton2.isSelected()) {
            graphController.generateRandomGraph(Integer.parseInt(nodesNumber.getText()), nameGraph.getText(), centerPane);
        } else if (rbutton3.isSelected()) {
            popupPane.setVisible(false);
        }
        popupPane.setVisible(false);
    }

}