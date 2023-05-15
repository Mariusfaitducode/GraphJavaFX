package graphproject.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List <Node> nodes;

    public Graph(){
        nodes = new ArrayList<>(0);
    }
    public Graph(List <Node> nodes)
    {
        this.nodes = nodes;
    }

    public List <Node> getGraph(){return nodes;}

    public void generateRandomNodes(int number, Pane pane){
        for(int i = 0; i < number; i++){
            //System.out.println(pane.getLayoutX());
            nodes.add(new Node((int)(Math.random() * (pane.getWidth())), (int)(Math.random() * (pane.getHeight())), i));
        }
    }

    public void generateRandomLinks(){
        for(Node node: nodes){
            int links_count = (int)(Math.random() * 4);

            for (int i = 0; i < links_count; i++){

                //On ajoute un lien avec un node aléatoire
                node.links.add(new Link(nodes.get((int)(Math.random() * nodes.size()))));
            }

        }
    }
    
    public void displayGraph(){
        for (Node node: nodes ) {
            node.displayNode();
        }
    }
}
