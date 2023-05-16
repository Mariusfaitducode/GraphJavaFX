package graphproject;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {

        //Permet de lancer l'application
        Parent root = FXMLLoader.load(getClass().getResource("app-view.fxml"));
        mainStage.setTitle("Graph Project");
        Scene scene = new Scene(root, 1000, 700);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}