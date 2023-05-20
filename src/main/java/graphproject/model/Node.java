package graphproject.model;

import graphproject.controller.graphics.Graphics;
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

    public void setX(int x){
        this.x = x;
        circle.setCenterX(x);
        for (Link link : links){
            Link.Arrow arrow = link.getArrow();
            Graphics.updateArrow(arrow, this, link.getNode());
        }
    }
    public void setY(int y){
        this.y = y;
        circle.setCenterY(y);
        for (Link link : links){
            Link.Arrow arrow = link.getArrow();
            Graphics.updateArrow(arrow, this, link.getNode());
        }
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public List<Link> getLinks(){return links;}

    public void setCircle(Circle circle){this.circle = circle;}

    public Circle getCircle(){return circle;}

    public void deleteAllLinks(){
        for (Link link : links){
            Line line = link.getLine();
            Polygon arrow = link.getArrowHead();

            ((Pane) line.getParent()).getChildren().remove(line);
            ((Pane) arrow.getParent()).getChildren().remove(arrow);
        }
    }

    //public List <Node> getLinks(){return linkNodes;}

    public void displayNode(){
        System.out.println("node : "+ x+ " "+y);
    }

}
