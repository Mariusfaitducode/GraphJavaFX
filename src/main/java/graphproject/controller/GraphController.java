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
        //selectionPaneController.searchResetButtonListener(graph);

        this.toolsController = new ToolsController(toolsBar, selectionPaneController);

        // Initializing Graphic Rendering

        //initializeCenterPaneSettings();

        // All initialize listeners

        listenerAddNodeToGraph();
        //listenerZoomGraph();

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
        parentCenterPane.setOnScroll(event -> {
            double zoomFactor;
            if (event.getDeltaY() > 0 ) {
                zoomFactor = 0.1;
            } else {
                zoomFactor = -0.1;
            }
            double mouseX = event.getX(); // X coordinate of the mouse pointer
            double mouseY = event.getY(); // Y coordinate of the mouse pointer

            System.out.println("nouseX : "+mouseX);
            System.out.println("nousey : "+mouseY);

            double currentScaleX = centerPane.getScaleX();
            double currentScaleY = centerPane.getScaleY();

            double newScaleX = currentScaleX + zoomFactor;
            double newScaleY = currentScaleY + zoomFactor;

            double centerX = centerPane.getBoundsInParent().getCenterX() - centerPane.getLayoutX();
            double centerY = parentCenterPane.getBoundsInParent().getCenterY() - centerPane.getLayoutY();

            System.out.println("center X : "+centerX);
            System.out.println("center y : "+centerY);

            double sizeX = centerPane.getBoundsInParent().getWidth();
            double sizeY = centerPane.getBoundsInParent().getHeight();

            //double centerX = (sizeX - origineX)/2;
            //double centerY = (sizeY - origineY)/2;


            double dx = mouseX - centerX;
            double dy = mouseY - centerY;





            if (newScaleX > 0.1 && newScaleY > 0.1) {

                centerPane.setTranslateX(mouseX-500);
                centerPane.setTranslateY(mouseY-312);

                centerPane.setScaleX(newScaleX);
                centerPane.setScaleY(newScaleY);

//                double dx = (mouseX - centerPane.getBoundsInParent().getWidth()/2) * (1-zoomFactor);
//                double dy = (mouseY - centerPane.getBoundsInParent().getHeight()/2) * (1-zoomFactor);
//
//                centerPane.setTranslateX(centerPane.getBoundsInParent().getWidth()/2-dx);
//                centerPane.setTranslateY(centerPane.getBoundsInParent().getHeight()/2-dy);

                centerPane.setTranslateX(dx);
                centerPane.setTranslateY(dy);

                System.out.println("scale : "+centerPane.getScaleX());
                System.out.println("TranslateX : "+centerPane.getTranslateX());
                System.out.println("TranslateY : "+centerPane.getTranslateY());
                System.out.println("------------------------------");
            }

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
