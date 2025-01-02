package it.rentify.rentifyapp.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.rentify.rentifyapp.DB.AffittuarioDAO;
import it.rentify.rentifyapp.DB.DBConnection;
import it.rentify.rentifyapp.beans.Affittuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowHome {

    public void start1(Stage primaryStage) throws Exception {
        // Recupero la connessione al database
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();

        if (conn == null) {
            System.out.println("Errore nella connessione al database");
            return;
        }

        // Crea una lista di affittuari
        ArrayList<Affittuario> affittuari = new ArrayList<>();
        AffittuarioDAO affittuarioDAO = new AffittuarioDAO(conn);

        try {
            affittuari = affittuarioDAO.getAffittuari();
        } catch (SQLException e) {
            showAlert("Errore nel recupero degli affittuari");
        }

        // Crea la tabella per visualizzare gli affittuari
        TableView<Affittuario> tableView = new TableView<>();
        TableColumn<Affittuario, String> nomeColumn = new TableColumn<>("Nome");
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<Affittuario, String> cognomeColumn = new TableColumn<>("Cognome");
        cognomeColumn.setCellValueFactory(new PropertyValueFactory<>("cognome"));

        tableView.getColumns().add(nomeColumn);
        tableView.getColumns().add(cognomeColumn);
        tableView.getItems().addAll(affittuari);

        // Crea il pulsante per inserire un nuovo affittuario
        Button insertButton = new Button("Inserisci Affittuario");
        insertButton.setOnAction(event -> insertAffittuario());

        // Configura il layout e la scena
        VBox vbox = new VBox(tableView, insertButton);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Elenco Affittuari");
        primaryStage.show();
    }

    private void insertAffittuario() {
        // Implementa il metodo per inserire un nuovo affittuario
        System.out.println("Insert Affittuario button clicked");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}