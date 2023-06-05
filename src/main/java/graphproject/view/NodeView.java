package graphproject.view;

import graphproject.model.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeView {

    private Circle circle;
    private Node node;

    public NodeView(Node node, Circle circle){
        this.node = node;
        this.circle = circle;
    }

    public void setNodeBorderColor(Color color){
        circle.setStroke(color);
    }

    public void setNodeColor(Color color){
        circle.setFill(color);
    }

    public void setNodeColor(Node node, Color color){
        node.getCircle().setFill(color);
    }
}
