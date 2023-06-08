package graphproject.view.selection_pane;

import graphproject.controller.NodeController;
import graphproject.controller.SelectionPaneController;
import graphproject.model.Link;
import graphproject.model.Node;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class NodePane{
    public Node selectedNode;
    public TextField textId;
    public TextField textName;
    public TextField textColor;
    public TextField textPosX;
    public TextField textPosY;
    public ChoiceBox<String> goingLinks;
    public ChoiceBox<String> comingLinks;

    private ChangeListener<Number> choiceGoingBoxListener;
    private ChangeListener<Number> choiceComingBoxListener;

    public Button deleteNodeButton;


    public NodePane(Pane nodeRightPane){
        //Initialisation du champ de texte id
        this.textId = (TextField) nodeRightPane.lookup("#id-node-id");
        this.textId.setOnKeyTyped(e ->{
            if (!textId.getText().isEmpty()){
                this.selectedNode.setId(Integer.parseInt(textId.getText()));
            }
        });
        //Initialisation du champ de texte name
        this.textName = (TextField) nodeRightPane.lookup("#id-node-name");
        this.textName.setOnKeyTyped(e ->{
            if (!textName.getText().isEmpty()){
                this.selectedNode.setName(textName.getText());
            }
        });
        //Initialisation du champ de texte color
        this.textColor = (TextField) nodeRightPane.lookup("#id-node-color");
        this.textColor.setOnKeyTyped(e ->{
            try {
                Color color = Color.valueOf(textColor.getText());
                this.selectedNode.setColor(color);
            }
            catch (IllegalArgumentException event) {
                // Gère l'erreur pour une couleur invalide
                System.out.println("Couleur invalide : " + textColor);
            }

        });
        //Initialisation du champ de texte position X
        this.textPosX = (TextField) nodeRightPane.lookup("#id-node-posX");
        this.textPosX.setOnKeyTyped(e ->{
            if (!textPosX.getText().isEmpty()){
                this.selectedNode.setX(Integer.parseInt(textPosX.getText()));
            }
        });
        //Initialisation du champ de texte position X
        this.textPosY = (TextField) nodeRightPane.lookup("#id-node-posY");
        this.textPosY.setOnKeyTyped(e ->{
            if (!textPosY.getText().isEmpty()){
                this.selectedNode.setY(Integer.parseInt(textPosY.getText()));
            }
        });

        this.goingLinks = (ChoiceBox<String>) nodeRightPane.lookup("#node-going-links");
        this.comingLinks = (ChoiceBox<String>) nodeRightPane.lookup("#node-coming-links");

        this.choiceGoingBoxListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };
        this.choiceComingBoxListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

            }
        };

        this.deleteNodeButton = (Button) nodeRightPane.lookup("#delete-node-button");


    }

    public void setSelectedNode(Node selectedNode){

        if (this.selectedNode != null){
            this.selectedNode.getCircle().setScaleX(1);
            this.selectedNode.getCircle().setScaleY(1);
            this.selectedNode.getCircle().setStroke(Color.BLACK);
            this.selectedNode.getCircle().setStrokeWidth(1);
            this.selectedNode.setSelection(false);
        }

        //nodePane.lastSelectedNode = selectedNode;
        selectedNode.getCircle().setScaleX(1.1);
        selectedNode.getCircle().setScaleY(1.1);
        selectedNode.getCircle().setStroke(Color.RED);
        selectedNode.getCircle().setStrokeWidth(2);

        textId.setText(Integer.toString(selectedNode.getId()));
        textName.setText(selectedNode.getName());

        textPosX.setText(Integer.toString(selectedNode.getX()));
        textPosY.setText(Integer.toString(selectedNode.getY()));

        selectedNode.setSelection(true);

        this.selectedNode = selectedNode;
    }

    public void deselectNode(){
        if (this.selectedNode != null){
            this.selectedNode.getCircle().setScaleX(1);
            this.selectedNode.getCircle().setScaleY(1);
            this.selectedNode.getCircle().setStroke(Color.BLACK);
            this.selectedNode.getCircle().setStrokeWidth(1);
            this.selectedNode.setSelection(false);
            this.selectedNode = null;
        }
    }

    public void setChoiceBox(SelectionPaneController selectionPaneController){

        //Going lists

        List<Node> listGoingNode = new ArrayList<>(0);
        if (!goingLinks.getItems().isEmpty()){
            goingLinks.getItems().clear();
        }

        goingLinks.getSelectionModel().selectedIndexProperty().removeListener(choiceGoingBoxListener);

        for (Link link : selectedNode.getLinks()){

            goingLinks.getItems().add(link.getNode().getName());
            listGoingNode.add(link.getNode());
            //this.pathFoundChoice.getItems().add(node.getName());
        }
        choiceGoingBoxListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < listGoingNode.size()) {
                Node linkedNode = listGoingNode.get(new_val.intValue());
                selectionPaneController.setNodePane(linkedNode);
            }
        };
        goingLinks.getSelectionModel().selectedIndexProperty().addListener(choiceGoingBoxListener);

        //Coming list

        List<Node> listComingNode = new ArrayList<>(0);
        if (!comingLinks.getItems().isEmpty()){
            comingLinks.getItems().clear();
        }

        comingLinks.getSelectionModel().selectedIndexProperty().removeListener(choiceComingBoxListener);

        for (Node node : selectedNode.getLinkedNodeList()){

            comingLinks.getItems().add(node.getName());
            listComingNode.add(node);
            //this.pathFoundChoice.getItems().add(node.getName());
        }
        choiceComingBoxListener = (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

            if (new_val.intValue() >= 0 && new_val.intValue() < listComingNode.size()) {
                Node linkedNode = listComingNode.get(new_val.intValue());
                selectionPaneController.setNodePane(linkedNode);
            }
        };
        comingLinks.getSelectionModel().selectedIndexProperty().addListener(choiceComingBoxListener);
    }

    public void deleteNodeButtonListener(NodeController nodeController){
        this.deleteNodeButton.setOnMouseClicked(e ->{
            nodeController.deleteNode(selectedNode);
        });
    }


    public static String toHex(Color color) {
        // Convertit les composantes RGB en valeurs hexadécimales
        String red = Integer.toHexString((int) (color.getRed() * 255));
        String green = Integer.toHexString((int) (color.getGreen() * 255));
        String blue = Integer.toHexString((int) (color.getBlue() * 255));

        // Concatène les valeurs hexadécimales avec des zéros de remplissage si nécessaire
        red = padZero(red);
        green = padZero(green);
        blue = padZero(blue);

        // Crée la représentation hexadécimale complète de la couleur
        String hexColor = "#" + red + green + blue;

        return hexColor;
    }
    public static String padZero(String s) {
        return (s.length() == 1) ? "0" + s : s;
    }
}
