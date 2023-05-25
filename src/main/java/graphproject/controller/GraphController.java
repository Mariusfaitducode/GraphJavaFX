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

    // App attribute
    private Graph graph;

    private Label graphTitle;

    GraphController(){};

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Pane nodeRightPane, Pane linkRightPane, Label graphTitle, Pane searchPathRightPane, HBox toolsBar, Pane parentCenterPane) {

        this.graph = null;

        // Graphic elements of the scene

        this.centerPane = pane;
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;
        this.graphTitle = graphTitle;
        this.toolsBar = toolsBar;
        this.parentCenterPane = parentCenterPane;

        // tools

        this.selectionPaneController = new SelectionPaneController(nodeRightPane, linkRightPane, searchPathRightPane, toolsBar);
        this.toolsController = new ToolsController(toolsBar, selectionPaneController);

        // Initializing Graphic Rendering

        initializeCenterPaneSettings();

        // All initialize listeners

        listenerAddNodeToGraph();
        listenerZoomGraph();
        listenerMoveOnGraph();

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

    // Add a node when the ToggleButton is true, and we click on the graph
    private void listenerAddNodeToGraph() {
        centerPane.setOnMouseClicked(event -> {

            if (toolsController.isSelected_createNodesButton() && !graphIsNull()) {

                int x = (int) event.getX();
                int y = (int) event.getY();
                Node node = graph.addNode(x,y);

                // Display the information of the new node
                //Node node = graph.getNodeFromPos(x,y);

                selectionPaneController.setNodePane(node);

                // Updates the display of Nodes
                updateNode(node);
            }
        });
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

                centerPane.setTranslateX(translateX+dX*(centerPane.getBoundsInParent().getWidth()/8000));
                centerPane.setTranslateY(translateY+dY*(centerPane.getBoundsInParent().getHeight()/6240));

                System.out.println("Scale : "+centerPane.getScaleX());
                System.out.println("----------------------------------");
            }

        });
    }

    private void listenerMoveOnGraph() {
        centerPane.setOnMouseDragged(event -> {

        });
    }


    // Display the information of the node when clicked on it
    private void listenerNode(Circle circle, Node node) {

        //fonctions qui sélectionne une node si on clique dessus
        circle.setOnMouseClicked(event -> {

            if (searchPathRightPane.isVisible()){

                selectionPaneController.setSearchNode(node);
            }
            else{
                // Display the information of the node
                selectionPaneController.setNodePane(node);
            }
        });

        // permet de déplacer les nodes avec la souris
        circle.setOnMouseDragged(event -> {
            // Mise à jour des coordonnées du cercle avec les coordonnées de la souris
            node.setX((int)event.getX());
            node.setY((int)event.getY());
            event.consume();
        });

        // diférencie les nodes lorsque la souris est dessus
        circle.setOnMouseEntered(event -> {
            circle.setStroke(Color.RED); // Changement de couleur de la bordure lors du survol
            event.consume();
        });

        circle.setOnMouseExited(event -> {
            circle.setStroke(Color.BLACK); // Rétablissement de la couleur de la bordure
            event.consume();
        });

        // fonction qui ajoute des links
        circle.setOnMouseReleased(event -> {
            if (toolsController.isSelected_createLinksButton() && !graphIsNull()) {
                System.out.println("check");
                //find the red circle
                boolean isRedCircle = false;
                Node linkedNode = null;
                for (Node node2 : graph.getNodes()) {
                    Circle circle2 = node2.getCircle();
                    if (circle2.getFill()==Color.RED) {
                        isRedCircle = true;
                        linkedNode = node2;
                        break;
                    }
                }

                if (isRedCircle) {
                    // Create new link
                    graph.addLink(linkedNode, node);
                    Link.Arrow arrow = Graphics.DesignLineAndArrow(linkedNode, node, 10);
                    centerPane.getChildren().addAll(arrow.line, arrow.arrowHead);
                    Link link = graph.getLinkFromIds(linkedNode, node);
                    link.setOrientedLine(arrow);

                    //Reset Color to node
                    node.getCircle().setFill(Color.WHITE);
                    linkedNode.getCircle().setFill(Color.WHITE);
                } else {
                    node.getCircle().setFill(Color.RED);
                }
            }
        });
    }

    // Display the information if the link when clicked in it
    private void listenerLink() {
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

    }

    public void openGraph(Graph openedGraph){
        closeGraph();
        setGraph(openedGraph);
        displayGraph();
        graphTitle.setText(openedGraph.getName());
    }

    // Display graph on the graphic window
    public void displayGraph() {

        updateAllNodes();

        updateLinks();

    }

    public void updateNodes() {
        for (Node node : graph.getNodes()) {

            //Display new nodes
            if (node.getCircle()==null) {
                Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);

                // Add event listener to the node
                listenerNode(circle, node);

                // Add the circle to the pane
                node.setCircle(circle);
                centerPane.getChildren().add(circle);
            }
        }
    }

    public void updateNode(Node node) {

        //Display new nodes
        if (node.getCircle()==null) {
            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);

            // Add event listener to the node
            listenerNode(circle, node);

            // Add the circle to the pane
            node.setCircle(circle);
            centerPane.getChildren().add(circle);
        }

    }

    public void updateAllNodes() {
        //Positionne les nodes
        for (Node node: graph.getNodes()) {

            // Crée un cercle avec un rayon de 10 pixels
            Circle circle = Graphics.DesignCircle(node.getX(), node.getY(), 10);

            // Add event listener to the node
            listenerNode(circle, node);

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
