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

    // Les graphes sont simplement caractérisé par un nom et sa liste de node.
    // Ce sont les nodes qui possède une list de liens
    private String name;
    private final List <Node> nodes;

    public Graph(String name){
        this.name = name;
        this.nodes = new ArrayList<>(0);
    }

    public List <Node> getNodes(){return nodes;}

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


        scale = (double) Math.round(scale * 10) / 10;

        if (scale < 0.1) {
            scale = 0.1;
        } else if (scale > 2.0) {
            scale = 2.0;
        }

        centerPane.setScaleX(scale);
        centerPane.setScaleY(scale);

        for(int i = 0; i < number; i++){

            nodes.add(new Node(i, "node" + i,(int)(4000 - (800 / scale) / 2 + Math.random() * (800 / scale)), (int)(3120 - (624 / scale) / 2 + Math.random() * (624 / scale))));
        }
    }

    public void generateRandomLinks(){
        for(Node node: nodes){
            int links_count = (int)(Math.random() * 4);

            for (int i = 0; i < links_count; i++){

                Node linkedNode = selectionRoulette(node);

                Link link = new Link(linkedNode);

                // On ajoute un lien avec un node aléatoire
                node.links.add(link);
                linkedNode.linkedNodeList.add(node);
            }
        }
    }

    // Pour créer des liens de manière pas trop aléatoire pour ne avoir des liens entre des nodes trop éloigné
    // On a vu en IT45 une méthode de roulette pour avoir plus de chance de créer des liens avec des nodes proches.
    public Node selectionRoulette(Node selectedNode){
        int total = 0;
        double maxDistance = 0;

        for (Node node : nodes) {
            double distance = SearchPath.normeVect(selectedNode.x, selectedNode.y, node.x, node.y);
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        for(Node node: nodes){
            total += maxDistance - SearchPath.normeVect(selectedNode.x, selectedNode.y, node.x, node.y);
        }
        total *= 50;

        int random = (int)(Math.random() * total);
        int count = 0;
        for(Node node: nodes){
            count += 50 * (maxDistance - SearchPath.normeVect(selectedNode.x, selectedNode.y, node.x, node.y));
            if(count >= random){
                return node;
            }
        }
        return nodes.get(0);
    }

    public void setRandomNodesAndLinks(int number, Pane centerPane) {
        generateRandomNodes(number, centerPane);
        generateRandomLinks();
    }

    public void saveGraph(){

//        String path = "src\\main\\resources\\saves\\" + name + ".csv";
        String path = ".\\saves\\" + name + ".csv";

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

    public void loadGraph(File file) {


            try ( java.util.Scanner scanner = new java.util.Scanner(file) ) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] values = line.split(",");

                    if (values[0].equals("node")) {
                        int id = Integer.parseInt(values[1]);
                        String name = values[2];
                        int x = Integer.parseInt(values[3]);
                        int y = Integer.parseInt(values[4]);

                        Node node = new Node(id, name, x, y);
                        nodes.add(node);

                    } else if (values[0].equals("link")) {
                        int id = Integer.parseInt(values[1]);
                        int linkedId = Integer.parseInt(values[2]);

                        Node node = nodes.get(id);
                        Node linkedNode = nodes.get(linkedId);

                        Link link = new Link(linkedNode);
                        node.links.add(link);
                        linkedNode.linkedNodeList.add(node);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
}
