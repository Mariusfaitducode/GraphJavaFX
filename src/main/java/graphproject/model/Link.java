package graphproject.model;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Link {

    private Line line;
    private Polygon arrow;
    private final Node linkedNode;

    Link(Node node){
        this.linkedNode = node;
    }

    public Line getLine(){return line;}
    public Polygon getArrow(){return arrow;}
    public Node getNode(){return linkedNode;}

    public void setOrientedLine(Line line, Polygon arrow){
        this.line = line;
        this.arrow = arrow;
    }
}
