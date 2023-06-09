package graphproject.controller;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;


public class ToolsController {

    private final ToggleButton createNodesButton;
    private final ToggleButton createLinksButton;
    private final ToggleButton searchPathButton;

    private final ToggleButton deleteButton;

    private final SelectionPaneController selectionPaneController;

    ToolsController(HBox toolsBarPane, SelectionPaneController selectionPaneController) {
        this.createNodesButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-createNodes");
        this.createLinksButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-createLinks");
        this.searchPathButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-searchPath");
        this.deleteButton = (ToggleButton) toolsBarPane.lookup("#id-toolBar-delete");

        this.selectionPaneController = selectionPaneController;

        listenerNodesButton();
        listenerLinksButton();
        listenerPathButton();
        listenerDeleteButton();
    }

    public boolean isSelected_createNodesButton() {
        return createNodesButton.isSelected();
    }

    public boolean isSelected_createLinksButton() {
        return createLinksButton.isSelected();
    }

    public boolean isSelected_deleteButton() {
        return deleteButton.isSelected();
    }

    public void listenerNodesButton() {

        // Add listeners au toggle bouton "Créer des Nodes" pour déterminer son statut : appuyé / relaché
        createNodesButton.setOnMouseClicked(event -> {
            setSelectedToggleButtons(createNodesButton, searchPathButton, createLinksButton, deleteButton);
        });
    }

    public void listenerLinksButton() {

        // Add listeners au toggle bouton "Créer des Liens" pour déterminer son statut : appuyé / relaché
        createLinksButton.setOnMouseClicked(event2 -> {
            setSelectedToggleButtons(createLinksButton, searchPathButton,  createNodesButton, deleteButton);
        });
    }

    public void listenerPathButton() {

        // Add listeners au toggle bouton "Search Path" pour déterminer son statut : appuyé / relaché
        searchPathButton.setOnMouseClicked(event3 -> {
            setSelectedToggleButtons(searchPathButton, createLinksButton, createNodesButton, deleteButton);
            if (searchPathButton.isSelected()){
                selectionPaneController.setSearchPane();
            }
            else{
                selectionPaneController.closeSearchPane();
            }
        });
    }

    public void listenerDeleteButton(){

        // Add listeners au toggle bouton "Supprimer des nodes et liens" pour déterminer son statut : appuyé / relaché
        deleteButton.setOnMouseClicked(event4 ->{
            setSelectedToggleButtons(deleteButton, createNodesButton, createLinksButton, searchPathButton);
        });
    }

    // Définie la couleur des toggle bouton pour savoir si le bouton est sélectionner ou non
    public void setSelectedToggleButtons(ToggleButton t1, ToggleButton t2, ToggleButton t3, ToggleButton t4){
        if (t1.isSelected()) {
            t1.setStyle("-fx-background-color: red;");
            t2.setSelected(false);
            t2.setStyle("-fx-background-color: #222;");
            t3.setSelected(false);
            t3.setStyle("-fx-background-color: #222;");
            t4.setSelected(false);
            t4.setStyle("-fx-background-color: #222;");
        } else {
            t1.setStyle("-fx-background-color: #222;");
        }
        selectionPaneController.closeSearchPane();
    }
}
