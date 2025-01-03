package it.rentify.rentifyapp.controllers;

import it.rentify.rentifyapp.DB.ContrattoDAO;
import it.rentify.rentifyapp.beans.Contratto;
import it.rentify.rentifyapp.beans.enums.TipoContratto;
import it.rentify.rentifyapp.beans.enums.TipoRate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import it.rentify.rentifyapp.DB.AffittuarioDAO;
import it.rentify.rentifyapp.DB.DBConnection;
import it.rentify.rentifyapp.beans.Affittuario;
import javafx.util.StringConverter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
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

        //aggiorno gli affitti
        CheckAllRents checkAllRents = new CheckAllRents();
        checkAllRents.checkPayments(conn);
        System.out.println("Affitti aggiornati");

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

        //quando clicco due volte su una riga, apro i dettagli dell'affittuario
        tableView.setRowFactory(tv -> {
            TableRow<Affittuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Affittuario affittuario = row.getItem();
                    new ShowDetails(affittuario).show();
                }
            });
            return row;
        });

        tableView.getColumns().add(nomeColumn);
        tableView.getColumns().add(cognomeColumn);
        tableView.getItems().addAll(affittuari);

        // Crea il pulsante per inserire un nuovo affittuario
        Button insertButton = new Button("Inserisci Affittuario");
        insertButton.setOnAction(event -> insertAffittuario(tableView));

        //crea il pulsante per eliminare un affittuario
        Button deleteButton = new Button("Elimina Affittuario");
        deleteButton.setOnAction(event -> deleteAffittuario(tableView));

        //crea il pulsante per aggiungere un contratto
        Button ContractButton = new Button("aggiungi Contratto");
        ContractButton.setOnAction(event -> addContratto(tableView));

        // Configura il layout e la scena
        HBox buttonBox = new HBox(50, insertButton, deleteButton, ContractButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(50, tableView, buttonBox);
        Scene scene = new Scene(hbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Elenco Affittuari");
        primaryStage.show();
    }

    private void insertAffittuario(TableView<Affittuario> tableView) {
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

            if (nome.isEmpty() || cognome.isEmpty() || CF.length()!=16) {
                showAlert("Tutti i campi sono obbligatori");
            } else {
                //aggiungo il nuovo affittuario al database
                AffittuarioDAO affittuarioDAO = new AffittuarioDAO(db.getConnection());
                Affittuario affittuario = new Affittuario(nome, cognome, CF);
                try {
                    affittuarioDAO.insertAffittuario(affittuario);
                    reloadTableData(tableView);
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

    private void deleteAffittuario(TableView<Affittuario> tableView) {
        System.out.println("Delete Affittuario button clicked");

        Stage stage = new Stage();
        stage.setTitle("Cancellazione Affittuario");

        TextField CFField = new TextField();
        CFField.setPromptText("Codice Fiscale");
        CFField.setPromptText("Codice Fiscale");

        Button submitButton = new Button("Cancella");
        submitButton.setOnAction(event -> {
            String CF = CFField.getText();

            if (CF.length() != 16) {
                showAlert("codice fiscale non valido");
            } else {
                //aggiungo il nuovo affittuario al database
                AffittuarioDAO affittuarioDAO = new AffittuarioDAO(db.getConnection());
                try {
                    affittuarioDAO.deleteAffittuario(CF);
                    reloadTableData(tableView);
                } catch (SQLException e) {
                    showAlert("Errore nella cancellazione dell'affittuario");
                }
                stage.close();
            }
        });

        // Create a layout and add form fields and button
        VBox vbox = new VBox(10, CFField, submitButton);
        vbox.setPadding(new Insets(10));

        // Create a scene and set it on the stage
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal
        stage.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private void reloadTableData(TableView<Affittuario> tableView) {
        AffittuarioDAO affittuarioDAO = new AffittuarioDAO(db.getConnection());
        try {
            ArrayList<Affittuario> affittuari = affittuarioDAO.getAffittuari();
            tableView.getItems().setAll(affittuari);
        } catch (SQLException e) {
            showAlert("Errore nel recupero degli affittuari");
        }
    }

    private void addContratto(TableView<Affittuario> tableView){

        System.out.println("Add Contratto button clicked");

        Stage stage = new Stage();
        stage.setTitle("Inserimento Nuovo Contratto");
        // Fetch the list of tenants
        AffittuarioDAO affittuarioDAO = new AffittuarioDAO(db.getConnection());
        ArrayList<Affittuario> affittuari;
        try {
            affittuari = affittuarioDAO.getAffittuari();
        } catch (SQLException e) {
            showAlert("Errore nel recupero degli affittuari");
            return;
        }

        //creo una lista con tutti i codici fiscali
        ArrayList<String> codiciFiscali = new ArrayList<>();
        for (Affittuario affittuario : affittuari) {
            codiciFiscali.add(affittuario.getCodiceFiscale());
        }

        // Create a ComboBox for selecting a tenant
        ComboBox<String> tenantComboBox = new ComboBox<>();
        tenantComboBox.getItems().addAll(codiciFiscali);


        // Create form fields for contract details
        CheckBox ivaCheckBox = new CheckBox("IVA");

        DatePicker dataInizioPicker = new DatePicker();
        TextField dataInizioField = new TextField();
        dataInizioField.setPromptText("Data Inizio");

        ComboBox<TipoContratto> tipoComboBox = new ComboBox<>();
        tipoComboBox.getItems().addAll(TipoContratto.values());
        TextField tipoContrattoField = new TextField();
        tipoContrattoField.setPromptText("Tipo Contratto");

        ComboBox<TipoRate> rateComboBox = new ComboBox<>();
        rateComboBox.getItems().addAll(TipoRate.values());
        TextField tipoRateField = new TextField();
        tipoRateField.setPromptText("Tipo Rate");

        TextField importoAnnualeField = new TextField();
        importoAnnualeField.setPromptText("Importo Annuale");

        TextField adeguamentoField = new TextField();
        adeguamentoField.setPromptText("Adeguamento");

        TextField speseField = new TextField();
        speseField.setPromptText("Spese");

        // Create a submit button
        Button submitButton = new Button("Inserisci");

        submitButton.setOnAction(event -> {
            String selectedTenant = tenantComboBox.getValue();
            Boolean iva = ivaCheckBox.isSelected();
            LocalDate dataInizio = dataInizioPicker.getValue();
            TipoContratto tipo = tipoComboBox.getValue();
            TipoRate rate = rateComboBox.getValue();
            int importoAnnuale = Integer.parseInt(importoAnnualeField.getText());
            int adeguamento = Integer.parseInt(adeguamentoField.getText());
            int spese = Integer.parseInt(speseField.getText());

            if (selectedTenant == null || dataInizio == null || tipo == null || rate == null) {
                showAlert("Tutti i campi sono obbligatori");
            } else {
                Contratto contratto = new Contratto(selectedTenant, iva, Date.from(dataInizio.atStartOfDay(ZoneId.systemDefault()).toInstant()), tipo, rate, importoAnnuale, adeguamento, spese);
                ContrattoDAO contrattoDAO = new ContrattoDAO(db.getConnection());
                try {
                    contrattoDAO.insertContratto(contratto);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                stage.close();
            }
        });

        // Create a layout and add form fields and button
        VBox vbox = new VBox(10, tenantComboBox, ivaCheckBox, dataInizioPicker, tipoComboBox, rateComboBox, importoAnnualeField, adeguamentoField, speseField, submitButton);
        vbox.setPadding(new Insets(10));

        // Create a scene and set it on the stage
        Scene scene = new Scene(vbox, 300, 400);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal
        stage.showAndWait();
    }
}