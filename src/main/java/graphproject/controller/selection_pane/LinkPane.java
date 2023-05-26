package graphproject.controller.selection_pane;

import graphproject.model.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LinkPane {

    public Node startNode;
    public Node endNode;

    public Label startNodeID;
    public Label startNodeName;
    public Label endNodeID;
    public Label endNodeName;

    public LinkPane(Pane linkRightPane){
        startNodeID = (Label) linkRightPane.lookup("#start-node-id");
        startNodeName = (Label) linkRightPane.lookup("#start-node-name");

        endNodeID = (Label) linkRightPane.lookup("#end-node-id");
        endNodeName = (Label) linkRightPane.lookup("#end-node-name");
    }

    public void setLinkPane(Node startNode, Node endNode){
        this.startNode = startNode;
        this.endNode = endNode;

        startNodeID.setText(Integer.toString(startNode.getId()));
        startNodeName.setText(startNode.getName());

        endNodeID.setText(Integer.toString(endNode.getId()));
        endNodeName.setText(endNode.getName());
    }
}
