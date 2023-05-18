package graphproject.model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class Node {


    int id;
    String name;
    int x;
    int y;
    List<Link> links;
    Circle circle;

    public Node(int id, String name, int x, int y)
    {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;

        this.links = new ArrayList<>(0);
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}
    public int getX(){return x;}
    public int getY(){return y;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Link> getLinks(){return links;}

    public void setCircle(Circle circle){this.circle = circle;}

    public Circle getCircle(){return circle;}

    public void deleteAllLinks(){
        for (Link link : links){
            Line line = link.getLine();
            Polygon arrow = link.getArrow();

            ((Pane) line.getParent()).getChildren().remove(line);
            ((Pane) arrow.getParent()).getChildren().remove(arrow);
        }
    }

    //public List <Node> getLinks(){return linkNodes;}

    public void displayNode(){
        System.out.println("node : "+ x+ " "+y);
    }

}
