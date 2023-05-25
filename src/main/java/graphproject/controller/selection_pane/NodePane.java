package graphproject.controller.selection_pane;

import graphproject.model.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class NodePane{
    public Node selectedNode;
    public TextField textId;
    public TextField textName;
    public TextField textPosX;
    public TextField textPosY;

    public NodePane(Pane nodeRightPane){
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
