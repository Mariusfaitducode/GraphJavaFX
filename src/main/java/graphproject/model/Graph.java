package graphproject.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private String name;
    private final List <Node> nodes;

    public Graph(String name){
        this.name = name;
        this.nodes = new ArrayList<>(0);
    }

    public List <Node> getGraph(){return nodes;}

    public void setName(String name){this.name = name;}

    public String getName(){return this.name;}

    public void addNode(int x, int y){
        int id = nodes.size();
        String name = "node" + id;
        nodes.add(new Node(id, name, x, y));
    }

    public void generateRandomNodes(int number){
        for(int i = 0; i < number; i++){

    // TODO : refaire avec getWidth(), ps : avec les modifs, pane est nulle
    //            nodes.add(new Node((int)(Math.random() * (pane.getWidth())), (int)(Math.random() * (pane.getHeight())), i));
            nodes.add(new Node(i, "node" + i,(int)(Math.random() * (300)), (int)(Math.random() * (450))));

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
