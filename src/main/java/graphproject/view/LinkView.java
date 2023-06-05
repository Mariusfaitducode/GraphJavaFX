package graphproject.view;

import graphproject.model.Link;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LinkView {
    Link link;
    Pane centerPane;

    public LinkView(Link link, Pane centerPane) {
        this.link = link;
        this.centerPane = centerPane;
    }

    public void removeLink() {
        centerPane.getChildren().remove(link.getLine());
        centerPane.getChildren().remove(link.getArrowHead());
    }

    public void setLinkColor(Color color) {
        link.getLine().setStroke(color);
        link.getArrowHead().setFill(color);
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
