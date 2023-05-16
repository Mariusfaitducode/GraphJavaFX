package graphproject.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private String name;
    private List <Node> nodes;

    public Graph(String name){
        this.name = name;
        this.nodes = new ArrayList<>(0);
    }

    public List <Node> getGraph(){return nodes;}

    public void setName(String name){this.name = name;}

    public String getName(){return this.name;}

    public void generateRandomNodes(int number){
        for(int i = 0; i < number; i++){

//            nodes.add(new Node((int)(Math.random() * (pane.getWidth())), (int)(Math.random() * (pane.getHeight())), i));
            nodes.add(new Node((int)(Math.random() * (300)), (int)(Math.random() * (450)), i));

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

    public void setRandomNodesAndLinks(int number) {
        generateRandomNodes(number);
        generateRandomLinks();
    }


    public void displayGraph() {
        System.out.println("Name of graph : " + name);
    }
}
