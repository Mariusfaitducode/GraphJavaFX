package graphproject.controller.selection_pane;

import graphproject.model.Link;
import graphproject.model.Node;
import graphproject.view.LinkView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LinkPane {

    public Node startNode;
    public Link selectedLink;
    public Node endNode;

    public TextField textColor;

    public Label startNodeID;
    public Label startNodeName;
    public Label endNodeID;
    public Label endNodeName;
    public Button deleteLinkButton;

    private LinkView linkView;

    public LinkPane(Pane linkRightPane, Pane centerPane){

        this.linkView = new LinkView(selectedLink, centerPane);

        startNodeID = (Label) linkRightPane.lookup("#start-node-id");
        startNodeName = (Label) linkRightPane.lookup("#start-node-name");

        endNodeID = (Label) linkRightPane.lookup("#end-node-id");
        endNodeName = (Label) linkRightPane.lookup("#end-node-name");

        //Initialisation du champ de texte color
        this.textColor = (TextField) linkRightPane.lookup("#id-link-color");
        this.textColor.setOnKeyTyped(e ->{
            try {
                Color color = Color.valueOf(textColor.getText());
                this.selectedLink.setColor(color);
            }
            catch (IllegalArgumentException event) {
                // Gère l'erreur pour une couleur invalide
                System.out.println("Couleur invalide : " + textColor);
            }

        });

        deleteLinkButton = (Button) linkRightPane.lookup("#delete-link-button");

        deleteLinkButton.setOnMouseClicked(e ->{
            selectedLink.deleteLink(startNode);
            linkView.removeLink();
        });
    }

    public void setLinkPane(Node startNode, Link link, Node endNode){
        this.startNode = startNode;
        this.endNode = endNode;

        startNodeID.setText(Integer.toString(startNode.getId()));
        startNodeName.setText(startNode.getName());

        endNodeID.setText(Integer.toString(endNode.getId()));
        endNodeName.setText(endNode.getName());

        if (selectedLink != null){
            selectedLink.setSelection(false);
            linkView.setLink(selectedLink);
            linkView.setLinkColor(Color.BLACK);
        }
        selectedLink = link;
        selectedLink.setSelection(true);
        textColor.setText(NodePane.toHex(selectedLink.getColor()));
        linkView.setLink(selectedLink);
        linkView.setLinkColor(Color.RED);
    }

    public void deselectLink(){
        if (selectedLink != null){
            selectedLink.setSelection(false);
            selectedLink.setColor(selectedLink.getColor());
            selectedLink = null;
        }
    }
}
