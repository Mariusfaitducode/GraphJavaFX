package graphproject.controller;

import graphproject.view.selection_pane.LinkPane;
import graphproject.view.selection_pane.NodePane;
import graphproject.view.selection_pane.SearchPane;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SelectionPaneController {

    private final Pane nodeRightPane, linkRightPane, searchPathRightPane;
    private final HBox toolsBar;

    private NodePane nodePane;

    private SearchPane searchPane;

    private LinkPane linkPane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane, HBox toolsBar, Pane centerPane){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        this.toolsBar = toolsBar;

        nodePane = new NodePane(nodeRightPane);

        searchPane = new SearchPane(searchPathRightPane);

        searchPane.searchFindButtonListener(toolsBar, this);

        linkPane = new LinkPane(linkRightPane, centerPane);
    }

    // Cache toutes les barres de droites
    public void closeSelectionPane() {
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);
    }

    // Affiche la barre de droite qui contient les informations d'une node
    public void setNodePane(Node selectedNode){
        nodeRightPane.setVisible(true);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);

        nodePane.setSelectedNode(selectedNode);

        nodePane.setChoiceBox(this);
    }

    // Cache la barre de droite qui contient les informations d'une node
    public void closeNodePane(){
        nodeRightPane.setVisible(false);
        nodePane.deselectNode();
    }

    // Cache la barre de droite qui contient les informations d'un lien
    public void closeLinkPane(){
        linkRightPane.setVisible(false);
        linkPane.deselectLink();
    }

    public NodePane getNodePane(){return this.nodePane;}

    public Pane getSearchPathRightPane(){return this.searchPathRightPane;}

    // Affiche la barre de droite relative à la recherche du plus court chemin
    public void setSearchPane(){
        searchPathRightPane.setVisible(true);
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
    }

    // Cache la barre de droite relative à la recherche du plus court chemin
    public void closeSearchPane(){
        searchPathRightPane.setVisible(false);
    }

    //Search function

    public void setSearchNode(Node node){
        searchPane.setSearchNode(node);
    }

    // Affiche la barre de droite qui contient les informations d'un lien
    public void setLinkPane(Node startNode, Link link, Node endNode){
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(true);
        searchPathRightPane.setVisible(false);

        linkPane.setLinkPane(startNode, link, endNode);
    }

}
