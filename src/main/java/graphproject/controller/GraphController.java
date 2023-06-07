package graphproject.controller;

import graphproject.controller.graphics.Graphics;
import graphproject.model.Graph;
import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.view.GraphView;
import graphproject.view.LinkView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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

    private MenuItem buttonSaveGraph;

    //tools
    private ToolsController toolsController;
    private SelectionPaneController selectionPaneController;
    private NodeController nodeController;

    // App attribute
    private Graph graph;
    private GraphView graphView;

    private Label graphTitle;

    private Label zoomText;

    // Contruct the controller for the opened graph
    GraphController(Pane pane, Pane nodeRightPane, Pane linkRightPane, Label graphTitle, Pane searchPathRightPane, HBox toolsBar, Pane parentCenterPane, Label zoomText, MenuItem buttonSaveGraph) {

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
        this.buttonSaveGraph = buttonSaveGraph;

        // tools

        this.selectionPaneController = new SelectionPaneController(nodeRightPane, linkRightPane, searchPathRightPane, toolsBar, centerPane);
        //selectionPaneController.searchResetButtonListener(graph);

        this.toolsController = new ToolsController(toolsBar, selectionPaneController);

        this.nodeController = new NodeController(this.graph, this.centerPane, this.toolsController, this.selectionPaneController);

        // Initialising graph view

        this.graphView = new GraphView(centerPane, zoomText);

        // Initializing Graphic Rendering

        initializeCenterPaneSettings();

        // All initialize listeners

        nodeController.listenerAddNodeToGraph();
        listenerZoomGraph();
        listenerMoveOnGraph();
        listenerCoordinateOnMousePressed();
        listenerSaveGraph();

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

            graphView.viewZoomGraph(event.getDeltaY(), event.getX(), event.getY());
        });
    }

    private void listenerCoordinateOnMousePressed() {
        centerPane.setOnMousePressed(event -> {

            graphView.setMouseCoordinate(event.getX(), event.getY());
        });
    }

    private void listenerMoveOnGraph() {
        centerPane.setOnMouseDragged(event -> {

            graphView.viewMoveGraph(event.getX(), event.getY());
        });
    }

    //Listener link
    public void listenerLink(Node node, Link link){

        Node linkedNode = link.getNode();
        LinkView linkView = new LinkView(link, centerPane);

        link.getLine().setOnMouseClicked(event ->{

            if (toolsController.isSelected_deleteButton()){
                link.deleteLink(node);
                linkView.removeLink();
            }
            else{
                link.setSelection(true);
                selectionPaneController.setLinkPane(node, link, linkedNode);
                event.consume();
            }
        });
        link.getLine().setOnMouseEntered(event ->{

            if (!selectionPaneController.getSearchPathRightPane().isVisible()){
                linkView.setLinkColor(Color.RED);
                event.consume();
            }

        });
        link.getLine().setOnMouseExited(event ->{

            if (!link.isSelected() && !selectionPaneController.getSearchPathRightPane().isVisible()){
                linkView.setLinkColor(link.getColor());
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
            node.setColor(Color.WHITE);
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

        System.out.println("Display parameters :");
        System.out.println(" - Origin of graph (coord 0,0) : Top Left Corner");
        System.out.println(" - Max dimension of graph : 8000 px x 6240 px");
        System.out.println(" - Spawning point : center of graph (coord 4000,3120)");
        System.out.println("\n");
    }

    private void listenerSaveGraph() {
        buttonSaveGraph.setOnAction(actionEvent -> {
            if (graph == null) {
                graphView.displaySaveAlert();
            }
            else {
                graph.saveGraph();
            }
        });

    }
}
