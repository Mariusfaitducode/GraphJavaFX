package graphproject.model;

import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
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

        // Nombre de node maximum pour 500 * 500 pixels
        double standardSurface = 500 * 500;
        double maxNumberOfPerson = 10;
        double maxDensity = maxNumberOfPerson / standardSurface;

        double size = number * (1 / maxDensity);

        double scale = (800 * 624) / size;

        System.out.println("scale : " + scale);

        scale = (double) Math.round(scale * 10) / 10;

        if (scale < 0.1) {
            scale = 0.1;
        } else if (scale > 2.0) {
            scale = 2.0;
        }

        centerPane.setScaleX(scale);
        centerPane.setScaleY(scale);

        for(int i = 0; i < number; i++){

    // TODO : refaire avec getWidth(), ps : avec les modifs, pane est nulle
    //            nodes.add(new Node((int)(Math.random() * (pane.getWidth())), (int)(Math.random() * (pane.getHeight())), i));
            nodes.add(new Node(i, "node" + i,(int)(4000 - (800 / scale) / 2 + Math.random() * (800 / scale)), (int)(3120 - (624 / scale) / 2 + Math.random() * (624 / scale))));

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

    public void saveGraph(){

        String path = "src\\main\\resources\\saves\\" + name + ".csv";

        StringBuilder text = new StringBuilder();

        for (Node node : nodes) {
            String line = "node," + node.getId() + "," + node.getName() + "," + node.getX() + "," + node.getY() + "\n";
            text.append(line);
        }

        for (Node node : nodes) {
            for (Link link : node.getLinks()) {
                String line = "link," + node.getId() + "," + link.getNode().getId() + "\n";
                text.append(line);
            }
        }

        try ( FileWriter fileWriter = new FileWriter(path) ) {

            fileWriter.write(text.toString());
            System.out.println("Successfully saved the file.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File saved");
            alert.setHeaderText("The graph has been successfully saved");
            alert.setContentText("File location : \n" + path);
            alert.showAndWait();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
