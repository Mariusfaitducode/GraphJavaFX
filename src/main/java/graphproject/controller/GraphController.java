package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


//Permet de modifier un graphe
public class GraphController {

    // Graphic Attributes of the graph
    private final Pane pane;
    private final Pane nodeRightPane;
    private final Pane linkRightPane;
    private final Pane searchPathRightPane;

    // App attribute
    private Graph graph;

    private Label graphTitle;

    GraphController(){};

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Pane nodeRightPane, Pane linkRightPane, Label graphTitle, Pane searchPathRightPane) {

        this.pane = pane;
        //this.graph = graph;
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;
        this.graphTitle = graphTitle;
    }

    public void setGraph(Graph graph) {
        //this.pane = pane;
        this.graph = graph;
        //this.nodeRightPane = nodeRightPane;
        //this.linkRightPane = linkRightPane;
        //
    }

    public void clearGraph() {
        pane.getChildren().clear();
    }


    public void openGraph(Graph openedGraph){
        setGraph(openedGraph);
        clearGraph();
        displayGraph();
        graphTitle.setText(openedGraph.getName());
    }

    // Display graph on the graphic window
    public void displayGraph() {

        int radiusCircle = 10;

        //Positionne les nodes
        for (Node node: graph.getGraph()) {

            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10); // Crée un cercle avec un rayon de 50 pixels

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

                Link.Arrow arrow = Graphics.DesignLineAndArrow(node, linkedNode, 10);

                
                //fonctions qui sélectionne un link si on clique dessus
                arrow.line.setOnMouseClicked(event -> {
                    nodeRightPane.setVisible(false);
                    linkRightPane.setVisible(true);
                    searchPathRightPane.setVisible(false);
                });


                pane.getChildren().addAll(arrow.line, arrow.arrowHead);

                link.setOrientedLine(arrow);
                //linkedNode.addLineLink(line);
            }
        }
    }
}
