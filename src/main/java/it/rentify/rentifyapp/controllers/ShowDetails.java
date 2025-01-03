package it.rentify.rentifyapp.controllers;

import it.rentify.rentifyapp.DB.AffittoDAO;
import it.rentify.rentifyapp.DB.DBConnection;
import it.rentify.rentifyapp.beans.Affitto;
import it.rentify.rentifyapp.beans.Affittuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.ArrayList;

public class ShowDetails {
    Affittuario affittuario;

    public ShowDetails(Affittuario affittuario) {
        this.affittuario = affittuario;
    }

    public void show() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Dettagli affittuario");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        Label nomeLabel = new Label("Nome: " + affittuario.getNome());
        Label cognomeLabel = new Label("Cognome: " + affittuario.getCognome());
        Label codiceFiscaleLabel = new Label("Codice fiscale: " + affittuario.getCodiceFiscale());

        //controllo se l'affittuario deve qualcosa
        DBConnection db = new DBConnection();
        Connection conn =db.getConnection();
        AffittoDAO affittoDAO = new AffittoDAO(conn);
        ArrayList<Affitto> affitti = null;

        try {
            affitti = affittoDAO.getAffitto(affittuario.getCodiceFiscale());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (affitti != null) {
            for (Affitto affitto : affitti) {
                if (!affitto.getPagato()) {
                    Label affittoLabel = new Label("Affitto non pagato: " + affitto.getImporto());
                    vBox.getChildren().add(affittoLabel);
                }
            }
        }



        vBox.getChildren().addAll(nomeLabel, cognomeLabel, codiceFiscaleLabel);

        Scene scene = new Scene(vBox, 500, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
