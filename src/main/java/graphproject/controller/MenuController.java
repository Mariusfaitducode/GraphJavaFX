package graphproject.controller;

import graphproject.model.App;
import graphproject.model.Graph;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuController {

    //private MenuItem menuItem;

    private Menu openGraphsMenu;

    private MenuItem noRecentGraphMenuItem;

    MenuController(Menu openGraphsMenu, MenuItem noRecentGraphMenuItem){
        this.openGraphsMenu = openGraphsMenu;
        this.noRecentGraphMenuItem = noRecentGraphMenuItem;
    }

    // Permet d'afficher tous les menu items des graphes sauvegardés dans l'application
    public void openExistingGraphsItem(App app, GraphController graphController){
        // s'il existe des graphes à afficher dans le menu
        if (app.getNumberOfGraphs() > 0) {
            int i = 0;
            // On récupère tous les graphes
            for (Graph graph : app.getGraphs()) {

                String graphName = graph.getName();
                boolean set = false;

                for (MenuItem item : openGraphsMenu.getItems()) {
                    if (item.getText().equals(graphName)) {
                        set = true;
                    }
                }

                // Si le menu items n'est pas encore créé pour ce graphe, alors il créé un nouveau menu item
                if (!set) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setText(graphName);

                    //On déclare ce qui se passe lorsqu'on clique sur les sous menu
                    //(Ouvrir le graphe correspondant)
                    menuItem.setOnAction(actionEvent -> graphController.openGraph(graph));

                    //on ajoute les sous-menu dans le menu
                    openGraphsMenu.getItems().add(menuItem);
                }
            }
            // S'il y a un graphe à afficher, il cache le menu items avec écrit No Recent Graph
            noRecentGraphMenuItem.setVisible(false);
        } else {
            // S'il n'y a pas de graph à afficher, il affiche No Recent Graph
            noRecentGraphMenuItem.setVisible(true);
        }
    }
}
