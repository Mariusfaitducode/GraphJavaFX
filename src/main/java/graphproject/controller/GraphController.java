package graphproject.controller;

import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;


//Permet de modifier un graphe
public class GraphController {

    private Graph graph;

    GraphController(){
        graph = new Graph();
    }

    public void generateRandomGraph(int number, String name, Pane pane) {

        graph.generateRandomGraph(number, name, pane);
        displayGraph(pane);
    }

    public void generateEmptyGraph(String name, Pane pane) {
        graph.generateEmptyGraph(name);
        displayGraph(pane);
    }

    public void displayGraph(Pane pane) {

        int radiusCircle = 10;

        //Positionne les nodes
        for (Node node: graph.getGraph()) {

            Circle circle = new Circle(radiusCircle); // Crée un cercle avec un rayon de 50 pixels
            circle.setFill(Color.WHITE);

            circle.setCenterX(node.getX());
            circle.setCenterY(node.getY());

            //fonctions qui permet de supprimer un node si on clique dessus
            circle.setOnMouseClicked(event -> {
                ((Pane) circle.getParent()).getChildren().remove(circle);
                graph.getGraph().remove(node);
                node.deleteAllLinks();
            });

            node.setCircle(circle);

            pane.getChildren().add(circle);
        }
        //Dessine les links
        for (Node node: graph.getGraph()) {

            for (Link link: node.getLinks()){

                Node linkedNode = link.getNode();

                int startX = node.getX();
                int startY = node.getY();
                int endX = linkedNode.getX();
                int endY = linkedNode.getY();

                double angle = Math.atan2(endY - startY, endX - startX);

                startX += Math.cos(angle) * radiusCircle;
                startY += Math.sin(angle) * radiusCircle;

                endX -= Math.cos(angle) * radiusCircle;
                endY -= Math.sin(angle) * radiusCircle;

                //On créé une ligne entre 2 nodes reliées
                Line line = new Line(startX, startY, endX, endY);
                line.setFill(Color.WHITE);

                //Tete de la flêche montrant la direction

                //Calcul des points

                double arrowHeadLength = 10; // Longueur de la pointe de la flèche
                double arrowHeadWidth = 5;

                double arrowPoint1X = endX - arrowHeadLength * Math.cos(angle - Math.PI / 6);
                double arrowPoint1Y = endY - arrowHeadLength * Math.sin(angle - Math.PI / 6);
                double arrowPoint2X = endX - arrowHeadLength * Math.cos(angle + Math.PI / 6);
                double arrowPoint2Y = endY - arrowHeadLength * Math.sin(angle + Math.PI / 6);
                double arrowPoint3X = endX + arrowHeadWidth * Math.cos(angle);
                double arrowPoint3Y = endY + arrowHeadWidth * Math.sin(angle);

                Polygon arrowHead = new Polygon();
                arrowHead.getPoints().addAll(
                        arrowPoint1X, arrowPoint1Y,
                        arrowPoint2X, arrowPoint2Y,
                        arrowPoint3X, arrowPoint3Y
                );

                pane.getChildren().addAll(line, arrowHead);

                link.setOrientedLine(line, arrowHead);
                //linkedNode.addLineLink(line);
            }
        }
    }
}
