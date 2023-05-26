package graphproject.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class App {

    private final List<Graph> graphs;

    public App(Pane centerPane) {
        graphs = new ArrayList<>(0);
//        System.out.println("centerPane : " + centerPane.getWidth());
        create3RandomGraphs(centerPane);
        displayInConsole();
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    // create a new graph
    public void createNewGraph(String name) {
        Graph graph = new Graph(name);
        graphs.add(graph);
    }

    public void displayInConsole() {
        System.out.println("Number of graphs : " + getNumberOfGraphs());
        for (Graph graph : graphs) {
            graph.displayGraph();
        }
    }

    public void create3RandomGraphs(Pane centerPane) {
        for (int i = 0; i<3;i++){
            String name = "Graph" + i;
            Graph graph = new Graph(name);
            int nbr;
            graph.setRandomNodesAndLinks((int)Math.pow(10, 3-i), centerPane);
            graphs.add(graph);
        }
    }

    public Graph getLastGraph() {
        return graphs.get(graphs.size() - 1);
    }

    public int getNumberOfGraphs() {
        return graphs.size();
    }
}
