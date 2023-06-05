package graphproject.view;

import javafx.scene.layout.Pane;

public class AppView {
    Pane popupPane, nodeRightPane, linkRightPane, searchPathRightPane;

    public AppView(Pane popupPane, Pane nodeRightPane, Pane linkRightPane, Pane searchPathRightPane) {
        this.popupPane = popupPane;
        this.nodeRightPane = nodeRightPane;
        this.linkRightPane = linkRightPane;
        this.searchPathRightPane = searchPathRightPane;

        popupPane.setVisible(false);
        nodeRightPane.setVisible(false);
        linkRightPane.setVisible(false);
        searchPathRightPane.setVisible(false);
    }

    public void showPopup() {
        popupPane.setVisible(true);
    }

    public void hidePopup() {
        popupPane.setVisible(false);
    }
}
