package graphproject.view;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GraphView {
    private Pane centerPane;
    private Label zoomText;
    private double initialX;
    private double initialY;

    public GraphView(Pane centerPane, Label zoomText) {
        this.centerPane = centerPane;
        this.zoomText = zoomText;
        this.initialX = 0;
        this.initialY = 0;
    }

    public void viewZoomGraph(double d2Y, double X, double Y) {
        double zoomFactor;

        if (d2Y > 0 ) {
            zoomFactor = 0.1;
        } else {
            zoomFactor = -0.1;
        }

        double translateX = centerPane.getTranslateX();
        double translateY = centerPane.getTranslateY();

        double newScaleX = centerPane.getScaleX() + zoomFactor;
        double newScaleY = centerPane.getScaleX() + zoomFactor;

        double dX;
        double dY;

        if (d2Y > 0 ) {
            dX = - ((X - (centerPane.getWidth()/2)) * (1 - centerPane.getScaleX() / newScaleX));
            dY = - ((Y- (centerPane.getHeight()/2)) * (1 - centerPane.getScaleY() / newScaleY));
        } else {
            dX = (X - (centerPane.getWidth()/2)) * (centerPane.getScaleX() / newScaleX - 1);
            dY = (Y - (centerPane.getHeight()/2)) * (centerPane.getScaleY() / newScaleY - 1);
        }

        newScaleX = (double)Math.round(newScaleX * 10) / 10;
        newScaleY = (double)Math.round(newScaleY * 10) / 10;

        if (newScaleX >= 0.099 && newScaleY >= 0.099) {

            centerPane.setScaleX(newScaleX);
            centerPane.setScaleY(newScaleY);
            zoomText.setText("ZOOM : " + (int)(newScaleX*100) + " %");

            centerPane.setTranslateX(translateX+dX*(centerPane.getBoundsInParent().getWidth()/8000));
            centerPane.setTranslateY(translateY+dY*(centerPane.getBoundsInParent().getHeight()/6240));

            double borderLeft = centerPane.getTranslateX() - 4000 * (centerPane.getScaleX()-0.1);
            double borderTop = centerPane.getTranslateY() - 3120 * (centerPane.getScaleX()-0.1);
            double borderRight = centerPane.getTranslateX() + 4000 * (centerPane.getScaleX()-0.1);
            double borderBottom = centerPane.getTranslateY() + 3120 * (centerPane.getScaleX()-0.1);

            if (borderLeft > 0) {
                centerPane.setTranslateX(centerPane.getTranslateX() - borderLeft);
            } else if (borderRight < 0) {
                centerPane.setTranslateX(centerPane.getTranslateX() - borderRight);
            }

            if (borderTop > 0) {
                centerPane.setTranslateY(centerPane.getTranslateY() - borderTop);
            } else if (borderBottom < 0) {
                centerPane.setTranslateY(centerPane.getTranslateY() - borderBottom);
            }
        }
    }

    public void setMouseCoordinate(double X, double Y) {
        initialX = X;
        initialY = Y;
    }

    public void viewMoveGraph(double X, double Y) {
        double borderLeft = centerPane.getTranslateX() - 4000 * (centerPane.getScaleX()-0.1);
        double borderTop = centerPane.getTranslateY() - 3120 * (centerPane.getScaleX()-0.1);
        double borderRight = centerPane.getTranslateX() + 4000 * (centerPane.getScaleX()-0.1);
        double borderBottom = centerPane.getTranslateY() + 3120 * (centerPane.getScaleX()-0.1);

        double dX = (X - initialX) * (centerPane.getBoundsInParent().getWidth()/8000);
        double dY = (Y - initialY) * (centerPane.getBoundsInParent().getHeight()/6240);

        System.out.println("--------------------------------");
        if (dX < 0) {
            if (borderRight < 0) {
                System.out.println("out!!!! right");
            } else  {
                centerPane.setTranslateX(centerPane.getTranslateX() + dX);
            }
        } else {
            if (borderLeft > 0) {
                System.out.println("out!!!! left");
            } else  {
                centerPane.setTranslateX(centerPane.getTranslateX() + dX);
            }
        }

        if (dY < 0) {
            if (borderBottom < 0) {
                System.out.println("out!!!! bottom");
            } else  {
                centerPane.setTranslateY(centerPane.getTranslateY() + dY);
            }
        } else {
            if (borderTop > 0) {
                System.out.println("out!!!! top");
            } else  {
                centerPane.setTranslateY(centerPane.getTranslateY() + dY);
            }
        }
    }

    public void displaySaveAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No graph to save");
        alert.setContentText("Create or open a graph before saving it");
        alert.showAndWait();
    }
}
