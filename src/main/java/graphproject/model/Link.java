package graphproject.model;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Link {

    public static class Arrow{
        public Line line;
        public Polygon arrowHead;
    }

    private Arrow arrow;

    private final Node linkedNode;
    //private final Node initialNode;

    Link(Node node){
        this.linkedNode = node;
        //this.initialNode = initialNode;
    }

    public Line getLine(){return arrow.line;}
    public Polygon getArrowHead(){return arrow.arrowHead;}

    public  Arrow getArrow(){return arrow;}
    public Node getNode(){return linkedNode;}

    public void setOrientedLine(Arrow arrow){
        this.arrow = arrow;
    }
}
