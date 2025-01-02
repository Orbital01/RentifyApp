package it.rentify.rentifyapp;

import javafx.application.Application;
import javafx.stage.Stage;
import it.rentify.rentifyapp.DB.DBConnection;
import it.rentify.rentifyapp.controllers.ShowHome;

import java.sql.Connection;

public class Main extends Application {

    public void start(Stage primaryStage) {
        //creo la connessione al database
        DBConnection db = new DBConnection();
        //prendo la connessione al database
        Connection conn = null;
        conn=db.getConnection();
        if (conn != null) {
            System.out.println("Connessione al database avvenuta con successo");
            ShowHome showHome = new ShowHome();
            Stage stage = new Stage();

            try {
                showHome.start1(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("Errore nella connessione al database");
        }
    }
}
