package it.rentify.rentifyapp.DB;
import it.rentify.rentifyapp.beans.Contratto;
import it.rentify.rentifyapp.beans.enums.TipoContratto;
import it.rentify.rentifyapp.beans.enums.TipoRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContrattoDAO {

    Connection conn = null;

    public ContrattoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertContratto(Contratto contratto) throws SQLException {
        String query = "INSERT INTO Contratto(CodiceFiscale, iva, DataInizio, TipoContratto, TipoRate, ImportoAnnuale, Adeguamento, Spese) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, contratto.getCodiceFiscale());
            pstatement.setBoolean(2, contratto.getIva());
            pstatement.setString(3, contratto.getDataInizio().toString());
            pstatement.setString(4, contratto.getTipo().toString());
            pstatement.setString(5, contratto.getRate().toString());
            pstatement.setInt(6, contratto.getImportoAnnuale());
            pstatement.setInt(7, contratto.getAdeguamento());
            pstatement.setInt(8, contratto.getSpese());
            pstatement.executeUpdate();
        }
    }

    public Contratto getContratto (String codiceFiscale) throws SQLException {
        String query = "SELECT * FROM Contratto WHERE CodiceFiscale = ?";
        Contratto contratto = null;

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, codiceFiscale);
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    TipoContratto tipoContratto = TipoContratto.fromString(result.getString("TipoContratto"));
                    TipoRate tipoRate = TipoRate.fromString(result.getString("TipoRate"));

                    contratto = new Contratto(result.getString("CodiceFiscale"), result.getBoolean("iva"),
                            result.getDate("DataInizio"), tipoContratto,
                            tipoRate, result.getInt("ImportoAnnuale"), result.getInt("Adeguamento"), result.getInt("Spese"));
                }
                return contratto;
            }
        }
    }

    public ArrayList<Contratto> getAllContratti() throws SQLException {
        String query = "SELECT * FROM Contratto";

        ArrayList<Contratto> contratti = new ArrayList<>();

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    TipoContratto tipoContratto = TipoContratto.fromString(result.getString("TipoContratto"));
                    TipoRate tipoRate = TipoRate.fromString(result.getString("TipoRate"));

                    Contratto contratto = new Contratto(result.getString("CodiceFiscale"), result.getBoolean("iva"),
                            result.getDate("DataInizio"), tipoContratto,
                            tipoRate, result.getInt("ImportoAnnuale"), result.getInt("Adeguamento"), result.getInt("Spese"));
                    contratti.add(contratto);
                }
                return contratti;
            }
        }
    }
}
