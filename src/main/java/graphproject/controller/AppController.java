package graphproject.controller;

import graphproject.model.Graph;
import graphproject.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AppController {


    @FXML
    private Pane centerPane;

    private final GraphController graphController = new GraphController();



    @FXML
    protected void initializeGraph(){

        graphController.RandomGraph(centerPane);
    }
}