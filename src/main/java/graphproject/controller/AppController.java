package graphproject.controller;

import java.util.ArrayList;
import java.util.List;

import graphproject.model.App;
import graphproject.model.Graph;
import graphproject.view.AppView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//Regroupe la gestion de toutes les interactions de l'utilisateur

public class AppController implements Initializable {

    // Récupère tous les éléments du fichier FXML grâce à la fonction @FXML

    //id des éléments
    @FXML
    private Pane centerPane, nodeRightPane, linkRightPane, searchPathRightPane, parentCenterPane;

    @FXML
    private HBox toolsBar;

    @FXML
    private Label graphTitle, zoomText, path;

    //Elements de la barre de menu
    @FXML
    private MenuItem noRecentGraphMenuItem, buttonSaveGraph;

    @FXML
    private Menu openGraphsMenu;

    //id des objets de la popup
    @FXML
    private Pane popupPane;
    @FXML
    private RadioButton rbutton1, rbutton2, rbutton3;
    @FXML
    private TextField nameGraph, nodesNumber;

    // On récupère les classes Modèle et Vue de APP pour appeler les fonctions de modification des attributs de l'application et d'affichage.

    private App app;

    private AppView appView;

    //Tout ce qui contient les listeners des différentes parties de l'interface

    private GraphController graphController;

    private PopupController popupController;

    private MenuController menuController;


    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {

        graphController = new GraphController(centerPane, nodeRightPane, linkRightPane, graphTitle, searchPathRightPane, toolsBar, parentCenterPane, zoomText, buttonSaveGraph);
        menuController = new MenuController(openGraphsMenu, noRecentGraphMenuItem);

        app = new App();
        appView = new AppView(popupPane, nodeRightPane, linkRightPane, searchPathRightPane);
    }

    //Tout ce qui déclenche les actions

    // Créé et affiche la fenêtre de PopUp quand on appuit sur le bouton CreateNew Graph
    @FXML
    public void createNewGraphPopup() {
        popupController = new PopupController(rbutton1, rbutton2, rbutton3, nameGraph, nodesNumber, app, appView, path);
        appView.showPopup();
    }

    // Créé le graph demandé par le bouton Générer
    public void generateGraph() {
        graphController.openGraph(popupController.generateGraph(centerPane));
    }

    // Affiche tous les graphs quand on clique sur le bouton OpenGraph
    public void openExistingGraphsItems() {
        menuController.openExistingGraphsItem(app, graphController);
    }

    public void closeGraph() {
        graphController.closeGraph();
    }

    // Fonction appliqué sur le bouton pour ouvrir l'explorateur de fichier
    @FXML
    public void openFileChooser() {
        popupController.openFileChooser((Stage)centerPane.getScene().getWindow());
    }

}