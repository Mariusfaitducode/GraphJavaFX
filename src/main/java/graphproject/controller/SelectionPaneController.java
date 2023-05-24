package graphproject.controller;

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

    public static class NodePane{
        public Node selectedNode;
        public TextField textId;
        public TextField textName;
        public TextField textPosX;
        public TextField textPosY;

        NodePane(Pane nodeRightPane){
            //Initialisation du champ de texte id
            this.textId = (TextField) nodeRightPane.lookup("#id-node-id");
            this.textId.setOnKeyTyped(e ->{
                this.selectedNode.setId(Integer.parseInt(textId.getText()));
            });
            //Initialisation du champ de texte name
            this.textName = (TextField) nodeRightPane.lookup("#id-node-name");
            this.textName.setOnKeyTyped(e ->{
                this.selectedNode.setName(textName.getText());
            });
            //Initialisation du champ de texte position X
            this.textPosX = (TextField) nodeRightPane.lookup("#id-node-posX");
            this.textPosX.setOnKeyTyped(e ->{
                this.selectedNode.setX(Integer.parseInt(textPosX.getText()));
            });
            //Initialisation du champ de texte position X
            this.textPosY = (TextField) nodeRightPane.lookup("#id-node-posY");
            this.textPosY.setOnKeyTyped(e ->{
                this.selectedNode.setY(Integer.parseInt(textPosY.getText()));
            });
        }
    }

    public static class SearchPane{
        public Node startNode;
        public Node endNode;
        public TextField textStartNode;
        public TextField textEndNode;
        public Label normDistance;
        public Label pathDistance;
        public Button findButton;
        public ChoiceBox<String> pathFoundChoice;
        public SearchPath searchPath;

        SearchPane(Pane searchPathRightPane){

            searchPath = new SearchPath();

            textStartNode = (TextField) searchPathRightPane.lookup("#name-start-node");

            textEndNode = (TextField) searchPathRightPane.lookup("#name-end-node");

            normDistance = (Label) searchPathRightPane.lookup("#text-norm-distance");

            pathDistance = (Label) searchPathRightPane.lookup("#text-path-distance");

            pathFoundChoice = (ChoiceBox<String>) searchPathRightPane.lookup("#path-found-choice");

            findButton = (Button) searchPathRightPane.lookup("#find-path-button");

        }
    }

    private NodePane nodePane;

    private SearchPane searchPane;


    SelectionPaneController(Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane, HBox toolsBar){
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        nodePane = new NodePane(nodeRightPane);

        searchPane = new SearchPane(searchPathRightPane);

        searchFindButtonListener();

        this.toolsBar = toolsBar;
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

    public boolean firstClick(){
        if (searchPane.startNode == null){
            return true;
        }
        return false;
    }

    public void setNodeStart(Node startNode){
        searchPane.startNode = startNode;
        startNode.getCircle().setFill(Color.GREEN);
        searchPane.textStartNode.setText(startNode.getName());
    }

    public void setNodeEnd(Node endNode){
        searchPane.endNode = endNode;
        endNode.getCircle().setFill(Color.BLUE);
        searchPane.textEndNode.setText(endNode.getName());
    }

    public void deselectStartNode(){
        searchPane.startNode.getCircle().setFill(Color.WHITE);
        searchPane.startNode = null;
        searchPane.textStartNode.setText("");
    }

    public void deselectEndNode(){
        searchPane.endNode.getCircle().setFill(Color.WHITE);
        searchPane.endNode = null;
        searchPane.textEndNode.setText("");
    }

    public void setSearchNode(Node node){
        if (searchPane.startNode == null){
            if( searchPane.endNode != node){
                setNodeStart(node);
            }
            else {
                deselectEndNode();
            }
        }
        else if (searchPane.startNode == node){
            deselectStartNode();
        }
        else{
            if (searchPane.endNode == null){
                setNodeEnd(node);
            }
            else if (searchPane.endNode == node){

                deselectEndNode();
            }
            else{
                deselectEndNode();
                setNodeEnd(node);
            }
        }
        if (searchPane.startNode != null && searchPane.endNode != null){
            searchPane.normDistance.setText("Norm dist: "+ (int)searchPane.searchPath.normeVect(
                    searchPane.startNode.getX(), searchPane.startNode.getY(),
                    searchPane.endNode.getX(), searchPane.endNode.getY()));
        }
        else{
            searchPane.normDistance.setText("Norm dist: ");
        }
    }

    public void searchFindButtonListener(){
        searchPane.findButton.setOnMouseClicked(e ->
        {
            List<Node> listNodePath = new ArrayList<>(0);

            float distance = searchPane.searchPath.searchPath(searchPane.startNode, searchPane.endNode, listNodePath);

            if ( distance == 0){
                //Pas de chemin trouvé
            }
            else{
                searchPane.pathDistance.setText("Path dist : "+ (int)distance);

                searchPane.pathFoundChoice.setVisible(true);

                for (Node node : listNodePath){

                    searchPane.pathFoundChoice.getItems().add(node.getName());


                }

                searchPane.pathFoundChoice.getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                        ToggleButton searchPathButton = (ToggleButton) toolsBar.lookup("#id-toolBar-searchPath");
                        searchPathButton.setSelected(false);

                        Node selectedNode = listNodePath.get(new_val.intValue());
                        setNodePane(selectedNode);

                    }

                );
            }
        });
    }
}
