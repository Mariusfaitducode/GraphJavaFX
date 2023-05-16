package graphproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

//Regroupe la gestion de toutes les interactions de l'utilisateur

public class AppController {

    //Tout les attributs de la scène qu'on va modifier
    //id des éléments
    @FXML
    private Pane centerPane;

    @FXML
    private Label graphTitle;

    //id des objets de la popup
    @FXML
    private Pane popupPane;
    @FXML
    private RadioButton rbutton1, rbutton2, rbutton3;
    @FXML
    private TextField nameGraph, nodesNumber;

    //Tout ce qui contient les actions

    private final GraphController graphController = new GraphController();

    private PopupController popupController;

    //Tout ce qui déclenche les actions
    //
    @FXML
    protected void createNewGraphPopup() {
        popupController = new PopupController(popupPane, rbutton1, rbutton2, rbutton3, nameGraph, nodesNumber);
        popupController.getPopupPane().setVisible(true);
    }

    public void generateGraph() {
        //graphTitle.setText(nameGraph.getText());

        popupController.generateGraph(graphController, centerPane);

    }

}