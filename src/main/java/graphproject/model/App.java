package graphproject.model;

import java.util.ArrayList;
import java.util.List;

public class App {

    private final List<Graph> graphs;

    public App() {
        graphs = new ArrayList<>(0);
        create3RandomGraphs();
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

    public void create3RandomGraphs() {
        for (int i = 0; i<3;i++){
            String name = "Graph" + i;
            Graph graph = new Graph(name);
            graph.setRandomNodesAndLinks(15);
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
