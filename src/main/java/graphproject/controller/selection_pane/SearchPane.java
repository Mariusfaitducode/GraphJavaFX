package graphproject.controller.selection_pane;

import graphproject.controller.SelectionPaneController;
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

public class SearchPane{
    public Node startNode;
    public Node endNode;

    public List<Node> listVisitedNode;
    public TextField textStartNode;
    public TextField textEndNode;
    public Label normDistance;
    public Label pathDistance;
    public Button findButton;

    public Button resetButton;
    public ChoiceBox<String> pathFoundChoice;
    public SearchPath searchPath;

    public SearchPane(Pane searchPathRightPane){

        listVisitedNode = new ArrayList<>(0);

        searchPath = new SearchPath();

        textStartNode = (TextField) searchPathRightPane.lookup("#name-start-node");

        textEndNode = (TextField) searchPathRightPane.lookup("#name-end-node");

        normDistance = (Label) searchPathRightPane.lookup("#text-norm-distance");

        pathDistance = (Label) searchPathRightPane.lookup("#text-path-distance");

        pathFoundChoice = (ChoiceBox<String>) searchPathRightPane.lookup("#path-found-choice");

        resetButton = (Button) searchPathRightPane.lookup("#reset-path-button");

        //setResetButtonListener();

        findButton = (Button) searchPathRightPane.lookup("#find-path-button");
    }

    //Listener

    public void setResetButtonListener(){

    }

    public void searchFindButtonListener(HBox toolsBar, SelectionPaneController selectionPaneController){
        this.findButton.setOnMouseClicked(e ->
        {
            List<Node> listNodePath = new ArrayList<>(0);

            //Calcul du chemin entre startNode et endNode
            float distance = this.searchPath.searchPath(this.startNode, this.endNode, listNodePath, listVisitedNode);

            if ( distance == 0){
                //Pas de chemin trouvé
            }
            else{
                this.pathDistance.setText("Path dist : "+ (int)distance);

                this.pathFoundChoice.setVisible(true);

                for (Node node : listNodePath){

                    this.pathFoundChoice.getItems().add(node.getName());
                }
                this.pathFoundChoice.getSelectionModel().selectedIndexProperty().addListener(
                        (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                            ToggleButton searchPathButton = (ToggleButton) toolsBar.lookup("#id-toolBar-searchPath");
                            searchPathButton.setSelected(false);

                            Node selectedNode = listNodePath.get(new_val.intValue());
                            selectionPaneController.setNodePane(selectedNode);
                        }
                );
            }
        });
        resetButton.setOnMouseClicked(e ->{

            System.out.println("reset button");

            for (Node node : listVisitedNode){
                System.out.println("node");
                if (node.getCircle().getFill() != Color.WHITE){

                    node.getCircle().setFill(Color.WHITE);
                }
            }
            deselectStartNode();
            deselectEndNode();
            pathFoundChoice.getItems().clear();
            this.normDistance.setText("Norm dist: ");
            this.pathDistance.setText("Path dist: ");
        });
    }


    public void setNodeStart(Node startNode){
        this.startNode = startNode;
        startNode.getCircle().setFill(Color.GREEN);
        this.textStartNode.setText(startNode.getName());
    }

    public void setNodeEnd(Node endNode){
        this.endNode = endNode;
        endNode.getCircle().setFill(Color.BLUE);
        this.textEndNode.setText(endNode.getName());
    }

    public void deselectStartNode(){
        this.startNode.getCircle().setFill(Color.WHITE);
        this.startNode = null;
        this.textStartNode.setText("");
    }

    public void deselectEndNode(){
        this.endNode.getCircle().setFill(Color.WHITE);
        this.endNode = null;
        this.textEndNode.setText("");
    }

    public void setSearchNode(Node node){
        if (this.startNode == null){
            if( this.endNode != node){
                setNodeStart(node);
            }
            else {
                deselectEndNode();
            }
        }
        else if (this.startNode == node){
            deselectStartNode();
        }
        else{
            if (this.endNode == null){
                setNodeEnd(node);
            }
            else if (this.endNode == node){

                deselectEndNode();
            }
            else{
                deselectEndNode();
                setNodeEnd(node);
            }
        }
        if (this.startNode != null && this.endNode != null){
            this.normDistance.setText("Norm dist: "+ (int)this.searchPath.normeVect(
                    this.startNode.getX(), this.startNode.getY(),
                    this.endNode.getX(), this.endNode.getY()));

            this.findButton.setDisable(false);
        }
        else{
            this.normDistance.setText("Norm dist: ");
            this.findButton.setDisable(true);
        }
    }

}
