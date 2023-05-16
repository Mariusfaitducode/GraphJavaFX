package graphproject.controller;

import java.util.ArrayList;
import java.util.List;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

//Regroupe la gestion de toutes les interactions de l'utilisateur

public class AppController {

    //Tout les attributs de la scène qu'on va modifier

    //id des éléments
    @FXML
    private Pane centerPane;

    @FXML
    private MenuItem noRecentGraphMenuItem;

    @FXML
    private Menu graphsMenu;

    @FXML
    private Label graphTitle;

    //id des objets de la popup
    @FXML
    private Pane popupPane;
    @FXML
    private RadioButton rbutton1, rbutton2, rbutton3;
    @FXML
    private TextField nameGraph, nodesNumber;

    // attribut app qui stocke toutes les données de l'application

    private final App app = new App();

    //Tout ce qui contient les actions

//    private final GraphController graphController = new GraphController();
    private GraphController graphController;

    private PopupController popupController;

    //Tout ce qui déclenche les actions
    //
    @FXML
    public void createNewGraphPopup() {
        popupController = new PopupController(popupPane, rbutton1, rbutton2, rbutton3, nameGraph, nodesNumber, app);
        popupController.setVisible(true);
    }

    public void generateGraph() {
        graphTitle.setText(nameGraph.getText());
        openGraph(popupController.generateGraph(centerPane));

    }

    private void openGraph(Graph openedGraph) {
        graphController = new GraphController(centerPane, openedGraph);
        graphController.displayGraph();
    }

    @FXML
    public void openGraphs() {
        if (app.getNumberOfGraphs() > 0) {
            int i = 0;
            for (Graph graph : app.getGraphs()) {
                String graphName = graph.getName();
                MenuItem menuItem = new MenuItem();
                menuItem.setText(graphName);
                menuItem.setOnAction(actionEvent -> openGraph(graph));
                graphsMenu.getItems().add(menuItem);
            }
            noRecentGraphMenuItem.setVisible(false);
        } else {
            noRecentGraphMenuItem.setVisible(true);
        }

    }

}