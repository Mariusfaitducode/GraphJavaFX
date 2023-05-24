package graphproject.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class SearchPath {

    //private Graph graph;

    //private Node nodeStart;
    //private Node nodeEnd;

    public void searchPath(Node nodeStart, Node nodeEnd){

        List<Node> listVisitedNode = new ArrayList<>(0);

        List<Node> listToVisitNode = new ArrayList<>(0);
        List<Float> listWeightNode = new ArrayList<>(0);

        Node actualChooseNode = nodeStart;
        listVisitedNode.add(actualChooseNode);

        float weight = 0;
        float count = 0;

        while( actualChooseNode != nodeEnd){

            for(Link link : actualChooseNode.links){


                Node actualNode = link.getNode();

                //Si la node n'a pas déjà été visité
                if (!listVisitedNode.contains(actualNode)){

                    weight = count + normeVect(actualNode.getX(), actualNode.getY(), nodeEnd.getX(), nodeEnd.getY());

                    listToVisitNode.add(actualNode);
                    listWeightNode.add(weight);
                }
            }

            if (listToVisitNode.isEmpty()){

                // Le chemin n'est pas possible
                break;
            }

            //Choix de la node la plus intéressante
            actualChooseNode = chooseNodeToExplore(listToVisitNode, listWeightNode);
            actualChooseNode.getCircle().setFill(Color.RED);
            listVisitedNode.add(actualChooseNode);
        }

        // Chemin trouvé

        // actualNode = endNode

        Node lastNode = actualChooseNode;
        listVisitedNode.remove(listVisitedNode.size()-1);
        actualChooseNode = listVisitedNode.get(listVisitedNode.size() -1);

        while (actualChooseNode != nodeStart){

            if (areLinked(actualChooseNode, lastNode)){
                actualChooseNode.getCircle().setFill(Color.MAGENTA);
                lastNode = actualChooseNode;
            }

            listVisitedNode.remove(listVisitedNode.size()-1);
            actualChooseNode = listVisitedNode.get(listVisitedNode.size() -1);
        }
    }

    public boolean areLinked(Node node, Node linkedNode){

        for (Link link : node.links){
            if (link.getNode() == linkedNode){
                return true;
            }
        }
        return false;
    }

    public Node chooseNodeToExplore(List<Node> listToVisitNode, List<Float> listWeight){

        //On choisit la node qui a le poids le plus faible
        float min = 100000000000f;
        int ref = 0;

        for(int i = 0; i < listToVisitNode.size(); i++){

            if (listWeight.get(i) < min){
                min = listWeight.get(i);
                ref = i;
            }
        }

        listWeight.remove(ref);
        Node choosedNode = listToVisitNode.get(ref);
        listToVisitNode.remove(ref);

        return choosedNode;
    }

    public float normeVect(int startX, int startY, int endX, int endY){

        float a = endX - startX;
        float b = endY - startY;

        return (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

}
