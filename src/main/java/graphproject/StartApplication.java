package graphproject;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //Permet de lancer l'application
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        //scene.getStylesheets().add("../src/resources/graphproject/style.css");


        //AppController controller = fxmlLoader.getController();

        stage.setTitle("Graph Project");
        stage.setScene(scene);




        //Pane centerPane = (Pane)scene.lookup("#id-graph-pane");

        //Circle circle = new Circle(50); // Crée un cercle avec un rayon de 50 pixels
        //circle.setFill(Color.RED); // Définit la couleur de remplissage du cercle

        //centerPane.getChildren().add(circle); // Ajoute le cercle au Pane du centre


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}