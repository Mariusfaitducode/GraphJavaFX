package graphproject.model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Link {

    public static class Arrow{
        public Line line;
        public Polygon arrowHead;
        public Color color;
    }

    private Arrow arrow;

    private final Node linkedNode;
    //private final Node initialNode;
    private boolean selected;

    Link(Node node){
        this.linkedNode = node;
        selected = false;
    }

    public Line getLine(){return arrow.line;}
    public Polygon getArrowHead(){return arrow.arrowHead;}

    public  Arrow getArrow(){return arrow;}
    public Node getNode(){return linkedNode;}

    public void setOrientedLine(Arrow arrow){
        this.arrow = arrow;
    }

    public void setSelection(boolean b){selected = b;}
    public boolean isSelected(){return selected;}

    public void deleteLink(Node node){

        linkedNode.linkedNodeList.remove(node);
        node.links.remove(this);
    }

    public Color getColor(){return arrow.color;}

    public void setColor(Color color){
        if (color != null){
            this.arrow.color = color;
            arrow.line.setStroke(color);
            arrow.arrowHead.setFill(color);
        }
    }

    public void tempColor(Color color){
        if (color != null){
            arrow.line.setStroke(color);
            arrow.arrowHead.setFill(color);
        }
    }
}
