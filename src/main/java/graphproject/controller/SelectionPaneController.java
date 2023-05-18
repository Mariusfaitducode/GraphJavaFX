package graphproject.controller;

import graphproject.model.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SelectionPaneController {

    private Pane nodeRightPane, linkRightPane, searchPathRightPane;

    public static class NodePane{
        public Node selectedNode;
        public TextField textId;
        public TextField textName;
        public TextField textPosX;
        public TextField textPosY;
    }

    private NodePane nodePane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        nodePane = new NodePane();
        this.nodePane.textId = (TextField) nodeRightPane.lookup("#id-node-id");

        this.nodePane.textId.setOnKeyTyped(e ->{
            getNode().setId(Integer.parseInt(nodePane.textId.getText()));
        });
        this.nodePane.textName = (TextField) nodeRightPane.lookup("#id-node-name");
        this.nodePane.textName.setOnKeyTyped(e ->{
            getNode().setName(nodePane.textName.getText());
        });
        this.nodePane.textPosX = (TextField) nodeRightPane.lookup("#id-node-posX");

        this.nodePane.textPosY = (TextField) nodeRightPane.lookup("#id-node-posY");

    }

    public Node getNode(){return this.nodePane.selectedNode;}

    public void closeSelectionPane() {
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);
    }

    public void setNodePane(Node selectedNode){
        nodeRightPane.setVisible(true);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);

        nodePane.selectedNode = selectedNode;

        nodePane.textId.setText(Integer.toString(selectedNode.getId()));
        nodePane.textName.setText(selectedNode.getName());
        nodePane.textPosX.setText(Integer.toString(selectedNode.getX()));
        nodePane.textPosY.setText(Integer.toString(selectedNode.getY()));
    }
}
