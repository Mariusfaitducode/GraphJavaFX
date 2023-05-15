package graphproject.controller;

import graphproject.model.Graph;
import graphproject.model.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GraphController {

    private Graph graph;

    GraphController(){
        graph = new Graph();
    }

    public void generateRandomGraph(int number, String name, Pane pane){
        graph.generateRandomGraph(number, name, pane);
        displayGraph(pane);
    }

    public void generateEmptyGraph(String name, Pane pane){
        graph.generateEmptyGraph(name);
        displayGraph(pane);
    }

    public void displayGraph(Pane pane) {
        for (Node node: graph.getGraph()) {

            Circle circle = new Circle(10); // Crée un cercle avec un rayon de 50 pixels
            circle.setFill(Color.WHITE);

            circle.setCenterX(node.getX());
            circle.setCenterY(node.getY());

            circle.setOnMouseClicked(event -> {
                ((Pane) circle.getParent()).getChildren().remove(circle);
            });
            node.setCircle(circle);

            pane.getChildren().add(circle);
        }
        for (Node node: graph.getGraph()) {

            for (Node linkedNode: node.getLinks()){
                Line line = new Line(node.getX(), node.getY(), linkedNode.getX(), linkedNode.getY());
                line.setFill(Color.WHITE);

                pane.getChildren().add(line);
            }
        }
    }
}
