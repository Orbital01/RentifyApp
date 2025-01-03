package it.rentify.rentifyapp.DB;

import it.rentify.rentifyapp.beans.Affitto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AffittoDAO {
    Connection conn = null;

    public AffittoDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Affitto>  getAffitti() throws SQLException {
        String query = "SELECT * FROM Affitto";
        ArrayList<Affitto> affitti = new ArrayList<>();

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    Affitto affitto = new Affitto(result.getString("CodiceFiscale"),
                            result.getInt("Importo"), result.getDate("Scadenza"),
                            result.getBoolean("Pagato"));

                    affitti.add(affitto);
                }
                return affitti;
            }
        }
    }

    public ArrayList<Affitto> getAffitto(String codiceFiscale) throws SQLException {
        String query = "SELECT * FROM Affitto WHERE CodiceFiscale = ?";
        ArrayList<Affitto> affitto = null;

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, codiceFiscale);
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    Affitto affittoTemp = new Affitto(result.getString("CodiceFiscale"),
                            result.getInt("Importo"), result.getDate("Scadenza"),
                            result.getBoolean("Pagato"));

                    affitto.add(affittoTemp);
                }
            }
        }
        return affitto;
    }

    public void insertAffitto(Affitto affitto) throws SQLException {
        String query = "INSERT INTO Affitto(CodiceFiscale, Importo, Scadenza, Pagato) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, affitto.getCodiceFiscale());
            pstatement.setInt(2, affitto.getImporto());
            pstatement.setDate(3, new java.sql.Date(affitto.getScadenza().getTime()));
            pstatement.setBoolean(4, affitto.getPagato());
            pstatement.executeUpdate();
        }
    }

    public void updateAffitto(Affitto affitto) throws SQLException {
        String query = "UPDATE Affitto SET Importo = ?, Scadenza = ?, Pagato = ? WHERE CodiceFiscale = ?";

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setInt(1, affitto.getImporto());
            pstatement.setDate(2, new java.sql.Date(affitto.getScadenza().getTime()));
            pstatement.setBoolean(3, affitto.getPagato());
            pstatement.setString(4, affitto.getCodiceFiscale());
            pstatement.executeUpdate();
        }
    }

}
