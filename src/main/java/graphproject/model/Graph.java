package graphproject.model;

import javafx.scene.layout.Pane;

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

    public Node addNode(int x, int y){
        int id = nodes.size();
        String name = "node" + id;

        Node node = new Node(id, name, x, y);
        nodes.add(node);
        return node;
    }

    public void addLink(Node node, Node linkedNode) {
        node.links.add(new Link(linkedNode));
        //linkedNode.links.add()
        linkedNode.linkedNodeList.add(node);
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

    public void generateRandomNodes(int number, Pane centerPane){

        System.out.println("centerPane width : " + centerPane.getWidth());

        // Nombre de node maximum pour 500 * 500 pixels
        double standardSurface = 500 * 500;
        double maxNumberOfPerson = 10;
        double maxDensity = standardSurface / maxNumberOfPerson;

        // Reset scale to 1.0
        centerPane.setScaleX(1.0);
        centerPane.setScaleY(1.0);

        // dimension of visible centerPane
        double width = 800;
        double height = 624;

        double surface = width * height;

        //
        boolean isBestScaleFound = false;
        double scale;

        while (isBestScaleFound){

        }

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

                Node linkedNode = nodes.get((int)(Math.random() * nodes.size()));

                Link link = new Link(linkedNode);

                // On ajoute un lien avec un node aléatoire
                node.links.add(link);
                linkedNode.linkedNodeList.add(node);
            }
        }
    }

    public void setRandomNodesAndLinks(int number, Pane centerPane) {
        generateRandomNodes(number, centerPane);
        generateRandomLinks();
    }


    public void displayGraph() {
        System.out.println("Name of graph : " + name);
    }
}
