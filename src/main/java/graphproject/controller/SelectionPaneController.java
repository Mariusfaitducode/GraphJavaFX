package graphproject.controller;

import graphproject.controller.selection_pane.NodePane;
import graphproject.controller.selection_pane.SearchPane;
import graphproject.model.Graph;
import graphproject.model.Node;
import graphproject.model.SearchPath;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SelectionPaneController {

    private final Pane nodeRightPane, linkRightPane, searchPathRightPane;
    private final HBox toolsBar;

    private NodePane nodePane;

    private SearchPane searchPane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane, HBox toolsBar){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        this.toolsBar = toolsBar;

        nodePane = new NodePane(nodeRightPane);

        searchPane = new SearchPane(searchPathRightPane);

        searchPane.searchFindButtonListener(toolsBar, this);
        searchPane.setResetButtonListener();


    }

    public Node getSelectedNode(){return this.nodePane.selectedNode;}

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

    public void setSearchPane(){
        searchPathRightPane.setVisible(true);
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
    }

    public void closeSearchPane(){
        searchPathRightPane.setVisible(false);
    }

    //Search function

    public void setSearchNode(Node node){
        searchPane.setSearchNode(node);
    }
}
