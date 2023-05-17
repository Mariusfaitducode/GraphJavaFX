package graphproject.controller;

import graphproject.model.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SelectionPaneController {

    private Pane nodeRightPane, linkRightPane, searchPathRightPane;

    public static class NodePane{
        public Node selectedNode;
        public TextField textId;
    }

    private NodePane nodePane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        nodePane = new NodePane();
        this.nodePane.textId = (TextField) nodeRightPane.lookup("#id-node-id");
    }

    public void setNodePane(Node selectedNode){
        nodeRightPane.setVisible(true);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);

        nodePane.selectedNode = selectedNode;

        nodePane.textId.setText(Integer.toString(selectedNode.getId()));
    }
}
