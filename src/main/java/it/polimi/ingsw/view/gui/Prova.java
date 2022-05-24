package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Prova extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Prova");    //nome dello stage
        Label label = new Label("Hello");   //testo da visualizzare
        Scene scene = new Scene(label, 400, 200);   //label = root dello scene graph; 400 = larghezza; 200 = altezza;
        stage.setScene(scene);  //per settare la scena
        stage.showAndWait();   //per mostrare lo stage
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
