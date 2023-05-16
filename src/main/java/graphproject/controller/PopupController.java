package graphproject.controller;

import graphproject.model.Graph;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class PopupController {


    private Pane popupPane;

    private RadioButton rbutton1, rbutton2, rbutton3;

    private TextField nameGraph, nodesNumber;

    PopupController(Pane popupPane, RadioButton rbutton1, RadioButton rbutton2, RadioButton rbutton3, TextField nameGraph, TextField nodesNumber){
        this.popupPane = popupPane;
        this.rbutton1 = rbutton1;
        this.rbutton2 = rbutton2;
        this.rbutton3 = rbutton3;
        this.nameGraph = nameGraph;
        this.nodesNumber = nodesNumber;
    }

    public Pane getPopupPane(){return popupPane;}

    public void generateGraph(GraphController graphController, Pane centerPane){
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
