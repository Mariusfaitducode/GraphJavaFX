package graphproject.model;

import java.util.ArrayList;
import java.util.List;

public class Node {


    int id;
    int x;
    int y;
    List <Node> linkNodes;

    public Node(int x, int y, int id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.linkNodes = new ArrayList<>(0);
    }

    public int getX(){return x;}
    public int getY(){return y;}

    public List <Node> getLinks(){return linkNodes;}

    public void displayNode(){
        System.out.println("node : "+ x+ " "+y);
    }

}
