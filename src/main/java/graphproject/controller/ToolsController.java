package graphproject.controller;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ToolsController {

    private final ToggleButton createNodesButton;

    private final ToggleButton createLinksButton;

    ToolsController(HBox toolsBarPane) {
        this.createNodesButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-createNodes");
        this.createLinksButton = (ToggleButton) toolsBarPane.lookup("#id-toolsBar-createLinks");

        listenerNodesButton();
        listenerLinksButton();
    }

    public boolean isSelected_createNodesButton() {
        return createNodesButton.isSelected();
    }

    public boolean isSelected_createLinksButton() {
        return createLinksButton.isSelected();
    }

    public void listenerNodesButton() {
        //initialize the color of the button
        createNodesButton.setStyle("-fx-background-color: #222;");

        // Add listeners
        createNodesButton.setOnMouseClicked(event -> {
            if (createNodesButton.isSelected()) {
                createNodesButton.setStyle("-fx-background-color: red;");
                createLinksButton.setSelected(false);
                createLinksButton.setStyle("-fx-background-color: #222;");

            } else {
                createNodesButton.setStyle("-fx-background-color: #222;");
            }
        });
    }

    public void listenerLinksButton() {
        //initialize the color of the button
        createLinksButton.setStyle("-fx-background-color: #222;");

        // Add listeners
        createLinksButton.setOnMouseClicked(event2 -> {
            if (createLinksButton.isSelected()) {
                createLinksButton.setStyle("-fx-background-color: red;");
                createNodesButton.setSelected(false);
                createNodesButton.setStyle("-fx-background-color: #222;");
            } else {
                createLinksButton.setStyle("-fx-background-color: #222;");
            }
        });
    }
}
