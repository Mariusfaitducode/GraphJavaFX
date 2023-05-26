package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


//Permet de modifier un graphe
public class GraphController {

    // Graphic Attributes of the graph


    private  Pane centerPane;
    private Pane parentCenterPane;

    private HBox toolsBar;

    private Pane nodeRightPane;
    private Pane linkRightPane;
    private Pane searchPathRightPane;

    //tools
    private ToolsController toolsController;
    private SelectionPaneController selectionPaneController;
    private NodeController nodeController;

    // App attribute
    private Graph graph;

    private Label graphTitle;

    private Label zoomText;

    // Global variables

    private double initialX;

    private double initialY;

    GraphController(){};

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Pane nodeRightPane, Pane linkRightPane, Label graphTitle, Pane searchPathRightPane, HBox toolsBar, Pane parentCenterPane, Label zoomText) {

        this.graph = null;

        // Graphic elements of the scene

        this.centerPane = pane;
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;
        this.graphTitle = graphTitle;
        this.toolsBar = toolsBar;
        this.parentCenterPane = parentCenterPane;
        this.zoomText = zoomText;

        // tools

        this.selectionPaneController = new SelectionPaneController(nodeRightPane, linkRightPane, searchPathRightPane, toolsBar, centerPane);
        //selectionPaneController.searchResetButtonListener(graph);

        this.toolsController = new ToolsController(toolsBar, selectionPaneController);

        this.nodeController = new NodeController(this.graph, this.centerPane, this.toolsController, this.selectionPaneController);

        // Initializing Graphic Rendering

        initializeCenterPaneSettings();

        // All initialize listeners

        nodeController.listenerAddNodeToGraph(this);
        listenerZoomGraph();
        listenerMoveOnGraph();
        listenerCoordinateOnMousePressed();

        // All global variables

        initialX = 0;
        initialY = 0;

    }

    public void setGraph(Graph graph) {

        this.graph = graph;
    }

    public void clearGraph() {
        centerPane.getChildren().clear();
    }

    public boolean graphIsNull() {
        return graph == null;
    }

    public void closeGraph() {

        // Deselecting current / opened graph
        graph = null;

        // Clear graphic visual
        clearGraph();

        //Close Right Sidebar
        selectionPaneController.closeSelectionPane();
    }



    private void listenerZoomGraph() {
        centerPane.setOnScroll(event -> {

            double zoomFactor;

            if (event.getDeltaY() > 0 ) {
                zoomFactor = 0.1;
            } else {
                zoomFactor = -0.1;
            }

            double translateX = centerPane.getTranslateX();
            double translateY = centerPane.getTranslateY();

            double newScaleX = centerPane.getScaleX() + zoomFactor;
            double newScaleY = centerPane.getScaleX() + zoomFactor;

            double dX;
            double dY;

            if (event.getDeltaY() > 0 ) {
                dX = - ((event.getX() - (centerPane.getWidth()/2)) * (1 - centerPane.getScaleX() / newScaleX));
                dY = - ((event.getY() - (centerPane.getHeight()/2)) * (1 - centerPane.getScaleY() / newScaleY));
            } else {
                dX = (event.getX() - (centerPane.getWidth()/2)) * (centerPane.getScaleX() / newScaleX - 1);
                dY = (event.getY() - (centerPane.getHeight()/2)) * (centerPane.getScaleY() / newScaleY - 1);
            }

            if (newScaleX > 0.1 && newScaleY > 0.1) {

                centerPane.setScaleX(newScaleX);
                centerPane.setScaleY(newScaleY);
                zoomText.setText("ZOOM : " + (int)(newScaleX*100) + " %");

                centerPane.setTranslateX(translateX+dX*(centerPane.getBoundsInParent().getWidth()/8000));
                centerPane.setTranslateY(translateY+dY*(centerPane.getBoundsInParent().getHeight()/6240));

                double borderLeft = centerPane.getTranslateX() - 4000 * (centerPane.getScaleX()-0.1);
                double borderTop = centerPane.getTranslateY() - 3120 * (centerPane.getScaleX()-0.1);
                double borderRight = centerPane.getTranslateX() + 4000 * (centerPane.getScaleX()-0.1);
                double borderBottom = centerPane.getTranslateY() + 3120 * (centerPane.getScaleX()-0.1);

                if (borderLeft > 0) {
                    centerPane.setTranslateX(centerPane.getTranslateX() - borderLeft);
                } else if (borderRight < 0) {
                    centerPane.setTranslateX(centerPane.getTranslateX() - borderRight);
                }

                if (borderTop > 0) {
                    centerPane.setTranslateY(centerPane.getTranslateY() - borderTop);
                } else if (borderBottom < 0) {
                    centerPane.setTranslateY(centerPane.getTranslateY() - borderBottom);
                }
            }

        });
    }

    private void listenerCoordinateOnMousePressed() {
        centerPane.setOnMousePressed(event -> {
            
            initialX = event.getX();
            initialY = event.getY();
        });
    }

    private void listenerMoveOnGraph() {
        centerPane.setOnMouseDragged(event -> {

            double borderLeft = centerPane.getTranslateX() - 4000 * (centerPane.getScaleX()-0.1);
            double borderTop = centerPane.getTranslateY() - 3120 * (centerPane.getScaleX()-0.1);
            double borderRight = centerPane.getTranslateX() + 4000 * (centerPane.getScaleX()-0.1);
            double borderBottom = centerPane.getTranslateY() + 3120 * (centerPane.getScaleX()-0.1);

            double dX = (event.getX() - initialX) * (centerPane.getBoundsInParent().getWidth()/8000);
            double dY = (event.getY() - initialY) * (centerPane.getBoundsInParent().getHeight()/6240);

            System.out.println("--------------------------------");
            if (dX < 0) {
                if (borderRight < 0) {
                    System.out.println("out!!!! right");
                } else  {
                    centerPane.setTranslateX(centerPane.getTranslateX() + dX);
                }
            } else {
                if (borderLeft > 0) {
                    System.out.println("out!!!! left");
                } else  {
                    centerPane.setTranslateX(centerPane.getTranslateX() + dX);
                }
            }

            if (dY < 0) {
                if (borderBottom < 0) {
                    System.out.println("out!!!! bottom");
                } else  {
                    centerPane.setTranslateY(centerPane.getTranslateY() + dY);
                }
            } else {
                if (borderTop > 0) {
                    System.out.println("out!!!! top");
                } else  {
                    centerPane.setTranslateY(centerPane.getTranslateY() + dY);
                }
            }

        });
    }




    // Display the information if the link when clicked in it
    /*private void listenerLink() {
        //TODO :fonctions qui sélectionne un link si on clique dessus
        for (javafx.scene.Node node : centerPane.lookupAll(".line")) {
            if (node instanceof Line) {
                node.setOnMouseClicked(event -> {
                    nodeRightPane.setVisible(false);
                    linkRightPane.setVisible(true);
                    searchPathRightPane.setVisible(false);
                });
            }
        }
    }*/

    //Listener link
    public void listenerLink(Node node, Link link){

        Node linkedNode = link.getNode();

        link.getLine().setOnMouseClicked(event ->{
            if (toolsController.isSelected_deleteButton()){
                link.deleteLink(node, centerPane);
            }
            else{
                link.setSelection(true);
                selectionPaneController.setLinkPane(node, link, linkedNode);
                event.consume();
            }
        });
        link.getLine().setOnMouseEntered(event ->{
            link.getLine().setStroke(Color.RED);
            link.getArrowHead().setFill(Color.RED);
            event.consume();
        });
        link.getLine().setOnMouseExited(event ->{
            if (!link.isSelected()){
                link.getLine().setStroke(Color.BLACK);
                link.getArrowHead().setFill(Color.BLACK);
            }
            event.consume();
        });
    }

    public void openGraph(Graph openedGraph){
        closeGraph();
        setGraph(openedGraph);
        displayGraph();

        centerPane.setTranslateX(0);
        centerPane.setTranslateY(0);

        graphTitle.setText(openedGraph.getName());
        nodeController.setGraph(graph);
    }

    // Display graph on the graphic window
    public void displayGraph() {

        updateAllNodes();

        updateLinks();

    }

    public void updateAllNodes() {
        //Positionne les nodes
        for (Node node: graph.getNodes()) {

            // Crée un cercle avec un rayon de 10 pixels
            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);

            // Add event listener to the node
            nodeController.listenerNode(circle, node);

            // Add the circle to the pane
            node.setCircle(circle);
            centerPane.getChildren().add(circle);
        }
    }

    public void updateLinks() {
        for (Node node: graph.getNodes()) {

            for (Link link: node.getLinks()){

                Node linkedNode = link.getNode();

                Link.Arrow arrow = Graphics.DesignLineAndArrow(node, linkedNode, 10);

                centerPane.getChildren().addAll(arrow.line, arrow.arrowHead);

                link.setOrientedLine(arrow);

                //Update listener of link
                listenerLink(node, link);

            }
        }
    }

    private void initializeCenterPaneSettings() {

        double width = 8000.0;
        double height = 6240.0;

        double layoutX = - (width - (width/10) ) / 2;
        double layoutY = - (height - (height/10) ) / 2;

        // Initialize zoom to 1.0
        centerPane.setScaleX(1.0);
        centerPane.setScaleY(1.0);

        // Initialize max length and width of centerPane
        centerPane.setPrefSize(width, height);

        // Center the centerPane
        centerPane.setLayoutX(layoutX);
        centerPane.setLayoutY(layoutY);

//        centerPane.setTranslateX(-2000);
//        centerPane.setTranslateY(-1560);

        System.out.println("width : "+centerPane.getWidth());
        System.out.println("height : "+centerPane.getHeight());
        System.out.println("prefwidth : "+centerPane.getPrefWidth());
        System.out.println("prefheight : "+centerPane.getPrefHeight());
        System.out.println("layoutX : "+centerPane.getLayoutX());
        System.out.println("layoutY : "+centerPane.getLayoutY());
        System.out.println("scaleX : "+centerPane.getScaleX());
        System.out.println("scaleY : "+centerPane.getScaleY());
    }
}
