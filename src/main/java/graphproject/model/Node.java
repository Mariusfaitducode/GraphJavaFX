package graphproject.model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Node {


    int id;
    int x;
    int y;
    List <Node> linkNodes;
    List <Line> links;
    Circle circle;

    public Node(int x, int y, int id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.linkNodes = new ArrayList<>(0);
        this.links = new ArrayList<>(0);
    }

    public int getX(){return x;}
    public int getY(){return y;}

    public void setCircle(Circle circle){this.circle = circle;}

    public void addLineLink(Line link){
        links.add(link);
    }

    public void deleteAllLinks(){
        for (Line line : links){
            ((Pane) line.getParent()).getChildren().remove(line);
        }
    }

    public List <Node> getLinks(){return linkNodes;}

    public void displayNode(){
        System.out.println("node : "+ x+ " "+y);
    }

}
