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

    Link(Node node){
        this.linkedNode = node;
    }

    public Line getLine(){return arrow.line;}
    public Polygon getArrow(){return arrow.arrowHead;}
    public Node getNode(){return linkedNode;}

    public void setOrientedLine(Arrow arrow){
        this.arrow = arrow;
    }
}
