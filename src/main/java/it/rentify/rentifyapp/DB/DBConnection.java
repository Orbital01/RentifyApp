package it.rentify.rentifyapp.DB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    String homeDirectory = System.getProperty("user.home");
    String url = "jdbc:sqlite:" + homeDirectory + "/Documents/database.db";

    Connection conn = null;

    //se ill database esiste bene, altrimenti lo creo
    public DBConnection() {
        if (checkDB(url)) {
            System.out.println("Database esistente");
            try {
                this.conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Database non esistente, creazione in corso...");
            try {
                this.conn = DriverManager.getConnection(url);
                //creo le tabelle
                createTableAffittuario();
                createTableAffitto();
                createTablePagamento();
                createTableContratto();

                System.out.println("Database creato con successo");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //creo le tabelle del database

    //creo la tabella Affittuario
    public void createTableAffittuario() {
        String query = "CREATE TABLE IF NOT EXISTS Affittuario (CodiceFiscale TEXT PRIMARY KEY, Nome TEXT, Cognome TEXT)";
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute(query);
            System.out.println("Tabella Affittuario creata con successo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //creo la tabella Affitto
    public void createTableAffitto() {
        String query = "CREATE TABLE IF NOT EXISTS Affitto (CodiceFiscale TEXT PRIMARY KEY, importo INTEGER, Scadenza TEXT, Pagato BOOLEAN, FOREIGN KEY (CodiceFiscale) REFERENCES Affittuario(CodiceFiscale))";
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute(query);
            System.out.println("Tabella Affitto creata con successo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //creo la tabella Pagamento
    public void createTablePagamento() {
        String query = "CREATE TABLE IF NOT EXISTS Pagamento (CodiceFiscale TEXT PRIMARY KEY, ImportoAnnuale INTEGER, Adeguamento INTEGER, Spese INTEGER, FOREIGN KEY (CodiceFiscale) REFERENCES Affittuario(CodiceFiscale))";
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute(query);
            System.out.println("Tabella Pagamento creata con successo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //creo la tabella Contratto
    public void createTableContratto() {
        String query = "CREATE TABLE IF NOT EXISTS Contratto (CodiceFiscale TEXT PRIMARY KEY, Iva BOOLEAN, DataInizio TEXT, Tipo TEXT, Rate TEXT, FOREIGN KEY (CodiceFiscale) REFERENCES Affittuario(CodiceFiscale))";
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute(query);
            System.out.println("Tabella Contratto creata con successo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //verifico se esiste o meno il database
    public boolean checkDB(String url) {
        String dbPath = url.replace("jdbc:sqlite:", "");
        File dbFile = new File(dbPath);
        return dbFile.exists();
    }


    public void CloseDB(Connection conn) throws SQLException {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connessione al database chiusa con successo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
