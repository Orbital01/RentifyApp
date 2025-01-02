package it.rentify.rentifyapp.DB;
import it.rentify.rentifyapp.beans.Contratto;
import it.rentify.rentifyapp.beans.enums.TipoContratto;
import it.rentify.rentifyapp.beans.enums.TipoRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContrattoDAO {

    Connection conn = null;

    public ContrattoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertContratto(Contratto contratto) throws SQLException {
        String query = "INSERT INTO Contratto(CodiceFiscale, iva, DataInizio, TipoContratto, TipoRate) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, contratto.getCodiceFiscale());
            pstatement.setBoolean(2, contratto.getIva());
            pstatement.setString(3, contratto.getDataInizio().toString());
            pstatement.setString(4, contratto.getTipo().toString());
            pstatement.setString(5, contratto.getRate().toString());
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
                            tipoRate);
                }
                return contratto;
            }
        }
    }
}
