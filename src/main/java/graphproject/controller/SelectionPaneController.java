package graphproject.controller;

import graphproject.model.Node;
import graphproject.model.SearchPath;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SelectionPaneController {

    private Pane nodeRightPane, linkRightPane, searchPathRightPane;

    public static class NodePane{
        public Node selectedNode;
        public TextField textId;
        public TextField textName;
        public TextField textPosX;
        public TextField textPosY;
    }

    public static class SearchPane{
        public Node startNode;
        public Node endNode;
        public Button findButton;

        public SearchPath searchPath;
    }

    private NodePane nodePane;

    private SearchPane searchPane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        nodePane = new NodePane();
        //Initialisation du champ de texte id
        this.nodePane.textId = (TextField) nodeRightPane.lookup("#id-node-id");
        this.nodePane.textId.setOnKeyTyped(e ->{
            getSelectedNode().setId(Integer.parseInt(nodePane.textId.getText()));
        });
        //Initialisation du champ de texte name
        this.nodePane.textName = (TextField) nodeRightPane.lookup("#id-node-name");
        this.nodePane.textName.setOnKeyTyped(e ->{
            getSelectedNode().setName(nodePane.textName.getText());
        });
        //Initialisation du champ de texte position X
        this.nodePane.textPosX = (TextField) nodeRightPane.lookup("#id-node-posX");
        this.nodePane.textPosX.setOnKeyTyped(e ->{
            getSelectedNode().setX(Integer.parseInt(nodePane.textPosX.getText()));
        });
        //Initialisation du champ de texte position X
        this.nodePane.textPosY = (TextField) nodeRightPane.lookup("#id-node-posY");
        this.nodePane.textPosY.setOnKeyTyped(e ->{
            getSelectedNode().setY(Integer.parseInt(nodePane.textPosY.getText()));
        });

        searchPane = new SearchPane();
        searchPane.searchPath = new SearchPath();

        searchPane.findButton = (Button) searchPathRightPane.lookup("#find-path-button");
        searchPane.findButton.setOnMouseClicked(e ->
        {
            System.out.println("Bouton clickééé");
            searchPane.searchPath.searchPath(searchPane.startNode, searchPane.endNode);
        });


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

    public boolean firstClick(){
        if (searchPane.startNode == null){
            return true;
        }
        return false;
    }

    public void setNodeStart(Node startNode){
        searchPane.startNode = startNode;
        startNode.getCircle().setFill(Color.GREEN);
    }

    public void setNodeEnd(Node endNode){
        searchPane.endNode = endNode;
        endNode.getCircle().setFill(Color.BLUE);
    }
}
