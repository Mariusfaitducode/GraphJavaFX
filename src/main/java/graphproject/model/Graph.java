package graphproject.model;

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

    public void generateRandomNodes(int number){
        for(int i = 0; i < number; i++){
            nodes.add(new Node((int)(Math.random() * (900)), (int)(Math.random() * (750)), i));
        }
    }

    public void generateRandomLinks(){
        for(Node node: nodes){
            int links_count = (int)(Math.random() * 4);

            for (int i = 0; i < links_count; i++){

                //On ajoute un lien avec un node aléatoire
                node.linkNodes.add(nodes.get((int)(Math.random() * nodes.size())));
            }

        }
    }
    
    public void displayGraph(){
        for (Node node: nodes ) {
            node.displayNode();
        }
    }
}
