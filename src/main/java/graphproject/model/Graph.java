package graphproject.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private String name;
    private List <Node> nodes;

    public Graph(){
        this.name = "";
        this.nodes = new ArrayList<>(0);
    }

    public Graph(String name){
        this.name = name;
        this.nodes = new ArrayList<>(0);
    }

    public Graph(List <Node> nodes)
    {
        this.name = "";
        this.nodes = nodes;
    }
    public Graph(String name, List <Node> nodes)
    {
        this.name = name;
        this.nodes = nodes;
    }

    public List <Node> getGraph(){return nodes;}

    public void setName(String name){this.name = name;}

    public String getName(){return this.name;}

    public void generateRandomNodes(int number, Pane pane){
        for(int i = 0; i < number; i++){

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

    public void generateRandomGraph(int number, String name, Pane pane) {
        generateRandomNodes(number, pane);
        generateRandomLinks();
        setName(name);
    }

    public void generateEmptyGraph(String name) {
        setName(name);
    }
}
