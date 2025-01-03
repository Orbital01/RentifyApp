package it.rentify.rentifyapp.controllers;

import it.rentify.rentifyapp.beans.Affittuario;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

        vBox.getChildren().addAll(nomeLabel, cognomeLabel, codiceFiscaleLabel);

        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
