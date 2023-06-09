package graphproject.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    // L'application contient seulement une liste de graphique
    private final List<Graph> graphs;

    public App() {
        graphs = new ArrayList<>(0);
        // Lorsqu'on lance l'application, on génère tous les graphes qui sont sauvegardés dans le dossier "saves"
        generateAllGraphsFromSave();
    }

    public List<Graph> getGraphs() {
        return graphs;
    }

    // create a new graph
    public void createNewGraph(String name) {
        Graph graph = new Graph(name);
        graphs.add(graph);
    }

    public Graph getLastGraph() {
        return graphs.get(graphs.size() - 1);
    }

    public int getNumberOfGraphs() {
        return graphs.size();
    }

    private void generateAllGraphsFromSave() {
        // Récupère tous les fichiers des graphes
        String directoryPath = "src\\main\\resources\\saves\\";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();


        if (files != null) {
            System.out.println(files.length + " graph(s) loaded from save : ");
            for (File file : files) {
                // Pour chaque fichier, il créé le graph
                String fileName = file.getName();
                String graphName = fileName.substring(0, fileName.length() - 4);
                System.out.println(" - " + graphName);
                Graph graph = new Graph(graphName);
                graph.setName(graphName);
                graph.loadGraph(file);
                graphs.add(graph);
            }
        }
    }
}
