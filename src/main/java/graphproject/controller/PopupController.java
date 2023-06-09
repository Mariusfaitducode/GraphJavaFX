package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import graphproject.view.AppView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PopupController {

    // Graphic attributes of the pop-up
    private RadioButton rbutton1, rbutton2, rbutton3;
    private TextField nameGraph, nodesNumber;
    private Label path;


    // App Attribute
    private App app;
    private File file;
    private AppView appView;

    PopupController(RadioButton rbutton1, RadioButton rbutton2, RadioButton rbutton3, TextField nameGraph, TextField nodesNumber, App app, AppView appView, Label path) {

        this.rbutton1 = rbutton1;
        this.rbutton2 = rbutton2;
        this.rbutton3 = rbutton3;
        this.nameGraph = nameGraph;
        this.nodesNumber = nodesNumber;
        this.path = path;

        this.app = app;
        this.appView = appView;
    }

    public Graph generateGraph(Pane centerPane){

        // Create new Graph
        app.createNewGraph(nameGraph.getText());

        // Update Graph depending on Radio Button:
        // Empty / Random / import existing one
        if (rbutton1.isSelected()) {

            // Empty

        } else if (rbutton2.isSelected()) {

            // Appelle la fonction pour créer un graph random
            app.getLastGraph().setRandomNodesAndLinks(Integer.parseInt(nodesNumber.getText()), centerPane);

        } else if (rbutton3.isSelected()) {
            // Appelle la fonction pour charger le graphe entré par l'utilisateur
            app.getLastGraph().loadGraph(file);
        }

        // Hide Creating Graph Pop-up
        appView.hidePopup();

        return app.getLastGraph();
    }

    // Fonction pour ouvrir l'explorateur de fichier
    public void openFileChooser(Stage mainStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        file = fileChooser.showOpenDialog(mainStage);
        if (file != null) {
            path.setText(file.getAbsolutePath());
        } else {
            path.setText("No file selected");
        }
    }
}
