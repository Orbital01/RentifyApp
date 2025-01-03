package it.rentify.rentifyapp.controllers;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import it.rentify.rentifyapp.DB.AffittuarioDAO;
import it.rentify.rentifyapp.DB.DBConnection;
import it.rentify.rentifyapp.beans.Affittuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowHome {
    DBConnection db = new DBConnection();
    Stage primaryStage;

    public void start1(Stage primaryStage) throws Exception {
        Connection conn = db.getConnection();
        this.primaryStage = primaryStage;

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

        //crea il pulsante per eliminare un affittuario
        Button deleteButton = new Button("Elimina Affittuario");
        deleteButton.setOnAction(event -> deleteAffittuario());

        // Configura il layout e la scena
        VBox vbox = new VBox(tableView, insertButton, deleteButton);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Elenco Affittuari");
        primaryStage.show();
    }

    private void insertAffittuario() {
        // Create a new stage for the modal window
        Stage stage = new Stage();
        stage.setTitle("Inserimento Affittuario");

        // Create form fields
        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");
        TextField cognomeField = new TextField();
        cognomeField.setPromptText("Cognome");
        TextField CFField = new TextField();
        CFField.setPromptText("Codice Fiscale");

        // Create a submit button
        Button submitButton = new Button("Inserisci");
        submitButton.setOnAction(event -> {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String CF = CFField.getText();

            if (nome.isEmpty() || cognome.isEmpty() || CF.isEmpty()) {
                showAlert("Tutti i campi sono obbligatori");
            } else {
                //aggiungo il nuovo affittuario al database
                AffittuarioDAO affittuarioDAO = new AffittuarioDAO(db.getConnection());
                Affittuario affittuario = new Affittuario(nome, cognome, CF);
                try {
                    affittuarioDAO.insertAffittuario(affittuario);
                } catch (SQLException e) {
                    showAlert("Errore nell'inserimento dell'affittuario");
                }
                stage.close();
            }
        });

        // Create a layout and add form fields and button
        VBox vbox = new VBox(10, nomeField, cognomeField, CFField, submitButton);
        vbox.setPadding(new Insets(10));

        // Create a scene and set it on the stage
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal
        stage.showAndWait();
    }

    private void deleteAffittuario() {
        // Implementa il metodo per eliminare un affittuario
        System.out.println("Delete Affittuario button clicked");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}