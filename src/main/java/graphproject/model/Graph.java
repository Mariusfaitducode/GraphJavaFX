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

    public List <Node> getNodes(){return nodes;}

    public Node getNodeFromPos(int x, int y) {
        for (Node node : nodes) {
            if (node.getX()==x && node.getY()==y)
                return node;
        }
        // By default, return the first node
        return nodes.get(0);
    }

    public void setName(String name){this.name = name;}

    public String getName(){return this.name;}

    public void addNode(int x, int y){
        int id = nodes.size();
        String name = "node" + id;
        nodes.add(new Node(id, name, x, y));
    }

    public void addLink(Node node, Node linkedNode) {
        node.links.add(new Link(linkedNode));
    }

    public Link getLinkFromIds(Node node, Node linkedNode) {
        for (Link link : node.getLinks()) {
            if (link.getNode()==linkedNode) {
                return link;
            }
        }
        //default value
        return node.getLinks().get(0);
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
