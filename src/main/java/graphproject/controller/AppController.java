package graphproject.controller;

import java.util.ArrayList;
import java.util.List;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

//Regroupe la gestion de toutes les interactions de l'utilisateur

public class AppController implements Initializable {

    //Tout les attributs de la scène qu'on va modifier

    //id des éléments
    @FXML
    private Pane centerPane, nodeRightPane, linkRightPane, searchPathRightPane;

    @FXML
    private Label graphTitle;

    //Elements de la barre de menu
    @FXML
    private MenuItem noRecentGraphMenuItem;

    @FXML
    private Menu openGraphsMenu;

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

    private GraphController graphController;

    private PopupController popupController;

    private MenuController menuController;

    private SelectionPaneController selectionPaneController;

    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
        popupPane.setVisible(false);
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);
        graphController = new GraphController(centerPane, nodeRightPane, linkRightPane, graphTitle, searchPathRightPane);
        menuController = new MenuController(openGraphsMenu, noRecentGraphMenuItem);
        selectionPaneController = new SelectionPaneController(nodeRightPane, linkRightPane, searchPathRightPane);
    }

    //Tout ce qui déclenche les actions
    //
    @FXML
    public void createNewGraphPopup() {
        popupController = new PopupController(popupPane, rbutton1, rbutton2, rbutton3, nameGraph, nodesNumber, app);
        popupController.setVisible(true);
    }

    public void generateGraph() {
        graphController.openGraph(popupController.generateGraph(centerPane), selectionPaneController);
    }

    public void openExistingGraphsItems() {
        menuController.openExistingGraphsItem(app, graphController, selectionPaneController);
    }

    public void closeGraph() {
        graphController.clearGraph();
    }

    public void searchPath() {
        searchPathRightPane.setVisible(true);
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
    }
}