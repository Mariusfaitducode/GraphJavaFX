package graphproject.controller;

import graphproject.model.Graph;
import graphproject.model.Node;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Pane centerPane;

    private Graph graph;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void initializeGraph(){

        graph = new Graph();
        graph.generateRandomNodes(10, centerPane);
        graph.generateRandomLinks();

        for (Node node: graph.getGraph()) {

            Circle circle = new Circle(10); // Crée un cercle avec un rayon de 50 pixels
            circle.setFill(Color.WHITE);

            circle.setCenterX(node.getX());
            circle.setCenterY(node.getY());

            circle.setOnMouseClicked(event -> {
                ((Pane) circle.getParent()).getChildren().remove(circle);
            });
            node.setCircle(circle);

            centerPane.getChildren().add(circle);
        }
        for (Node node: graph.getGraph()) {

            for (Node linkedNode: node.getLinks()){
                Line line = new Line(node.getX(), node.getY(), linkedNode.getX(), linkedNode.getY());
                line.setFill(Color.WHITE);

                centerPane.getChildren().add(line);
            }
        }
    }
}