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

    // Graphic Attributes of the graph
    private final Pane pane;
    private final Pane nodeRightPane;
    private final Pane linkRightPane;
    private final Pane searchPathRightPane;

    // App attribute
    private final Graph graph;

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Graph graph, Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane) {

        this.pane = pane;
        this.graph = graph;
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;
    }

    public void clearGraph() {
        pane.getChildren().clear();
    }

    // Display graph on the graphic window
    public void displayGraph() {

        int radiusCircle = 10;

        //Positionne les nodes
        for (Node node: graph.getGraph()) {

            Circle circle = new Circle(radiusCircle); // Crée un cercle avec un rayon de 50 pixels
            circle.setFill(Color.WHITE);

            circle.setCenterX(node.getX());
            circle.setCenterY(node.getY());

            //fonctions qui permet de supprimer un node si on clique dessus
//            circle.setOnMouseClicked(event -> {
//                ((Pane) circle.getParent()).getChildren().remove(circle);
//                graph.getGraph().remove(node);
//                node.deleteAllLinks();
//            });

            //fonctions qui sélectionne une node si on clique dessus
            circle.setOnMouseClicked(event -> {
                nodeRightPane.setVisible(true);
                linkRightPane.setVisible(false);
                searchPathRightPane.setVisible(false);
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

                //fonctions qui sélectionne un link si on clique dessus
                line.setOnMouseClicked(event -> {
                    nodeRightPane.setVisible(false);
                    linkRightPane.setVisible(true);
                    searchPathRightPane.setVisible(false);
                });


                pane.getChildren().addAll(line, arrowHead);

                link.setOrientedLine(line, arrowHead);
                //linkedNode.addLineLink(line);
            }
        }
    }
}
