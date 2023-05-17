package graphproject.controller.graphics;

import graphproject.model.Link.Arrow;
import graphproject.model.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class Graphics {

    //int radiusCircle;

    public static Circle DesignCircle(int x, int y, int radiusCircle){

        Circle circle = new Circle(radiusCircle); // Crée un cercle avec un rayon de 50 pixels
        circle.setFill(Color.WHITE);

        circle.setCenterX(x);
        circle.setCenterY(y);

        return circle;
    }

    public static Arrow DesignLineAndArrow(Node node, Node linkedNode, int radiusCircle){

        int startX = node.getX();
        int startY = node.getY();
        int endX = linkedNode.getX();
        int endY = linkedNode.getY();

        double angle = Math.atan2(endY - startY, endX - startX);

        startX += Math.cos(angle) * radiusCircle;
        startY += Math.sin(angle) * radiusCircle;

        endX -= Math.cos(angle) * radiusCircle;
        endY -= Math.sin(angle) * radiusCircle;

        //On créé une ligne entre 2 nodes reliées
        Line line = new Line(startX, startY, endX, endY);
        line.setFill(Color.WHITE);

        //Tete de la flêche montrant la direction

        //Calcul des points

        double arrowHeadLength = 10; // Longueur de la pointe de la flèche
        double arrowHeadWidth = 5;

        double arrowPoint1X = endX - arrowHeadLength * Math.cos(angle - Math.PI / 6);
        double arrowPoint1Y = endY - arrowHeadLength * Math.sin(angle - Math.PI / 6);
        double arrowPoint2X = endX - arrowHeadLength * Math.cos(angle + Math.PI / 6);
        double arrowPoint2Y = endY - arrowHeadLength * Math.sin(angle + Math.PI / 6);
        double arrowPoint3X = endX + arrowHeadWidth * Math.cos(angle);
        double arrowPoint3Y = endY + arrowHeadWidth * Math.sin(angle);

        Polygon arrowHead = new Polygon();
        arrowHead.getPoints().addAll(
                arrowPoint1X, arrowPoint1Y,
                arrowPoint2X, arrowPoint2Y,
                arrowPoint3X, arrowPoint3Y
        );

        Arrow arrow = new Arrow();
        arrow.line = line;
        arrow.arrowHead = arrowHead;

        return arrow;
    }
}