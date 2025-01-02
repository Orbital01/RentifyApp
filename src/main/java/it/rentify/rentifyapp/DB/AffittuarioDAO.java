package it.rentify.rentifyapp.DB;

import it.rentify.rentifyapp.beans.Affittuario;

import java.sql.*;
import java.util.ArrayList;

public class AffittuarioDAO {
    Connection conn = null;

    public AffittuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Affittuario> getAffittuari() throws SQLException {
        String query = "SELECT * FROM Affittuario";
        ArrayList<Affittuario> affittuari = new ArrayList<>();

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    Affittuario user = new Affittuario(result.getString("Nome"), result.getString("Cognome"),
                            result.getString("CodiceFiscale"));
                    affittuari.add(user);
                }
                return affittuari;
            }
        }
    }

    public Affittuario getAffittuario(String codiceFiscale) throws SQLException {
        String query = "SELECT * FROM Affittuario WHERE CodiceFiscale = ?";
        Affittuario user = null;

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, codiceFiscale);
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    user = new Affittuario(result.getString("Nome"), result.getString("Cognome"),
                            result.getString("CodiceFiscale"));
                }
                return user;
            }
        }
    }

    public void insertAffittuario(Affittuario affittuario) throws SQLException {
        String query = "INSERT INTO Affittuario(Nome, Cognome, CodiceFiscale) VALUES(?, ?, ?)";

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, affittuario.getNome());
            pstatement.setString(2, affittuario.getCognome());
            pstatement.setString(3, affittuario.getCodiceFiscale());
            pstatement.executeUpdate();
        }
    }
}
