package it.rentify.rentifyapp.DB;

import it.rentify.rentifyapp.beans.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PagamentoDAO {
    Connection conn = null;

    public PagamentoDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Pagamento> getPagamenti() throws Exception {
        String query = "SELECT * FROM Pagamento";
        ArrayList<Pagamento> pagamenti = new ArrayList<>();
        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            try (ResultSet result = pstatement.executeQuery();) {
                while (result.next()) {
                    Pagamento pagamento = new Pagamento(result.getString("CodiceFiscale"),
                            result.getInt("ImportoAnnuale"), result.getInt("Adeguamento"),
                            result.getInt("Spese"));
                    pagamenti.add(pagamento);
                }
                return pagamenti;
            }
        }
    }

    public void insertPagamento(Pagamento pagamento) throws Exception {
        String query = "INSERT INTO Pagamento (CodiceFiscale, ImportoAnnuale, Adeguamento, Spese) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, pagamento.getCodiceFiscale());
            pstatement.setInt(2, pagamento.getImportoAnnuale());
            pstatement.setInt(3, pagamento.getAdeguamento());
            pstatement.setInt(4, pagamento.getSpese());
            pstatement.executeUpdate();
        }
    }

    public void updatePagamento(Pagamento pagamento) throws Exception {
        String query = "UPDATE Pagamento SET ImportoAnnuale = ?, Adeguamento = ?, Spese = ? WHERE CodiceFiscale = ?";
        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setInt(1, pagamento.getImportoAnnuale());
            pstatement.setInt(2, pagamento.getAdeguamento());
            pstatement.setInt(3, pagamento.getSpese());
            pstatement.setString(4, pagamento.getCodiceFiscale());
            pstatement.executeUpdate();
        }
    }

    public void deletePagamento(String codiceFiscale) throws Exception {
        String query = "DELETE FROM Pagamento WHERE CodiceFiscale = ?";
        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, codiceFiscale);
            pstatement.executeUpdate();
        }
    }

    public Pagamento getPagamento(String codiceFiscale) throws Exception {
        String query = "SELECT * FROM Pagamento WHERE CodiceFiscale = ?";
        try (PreparedStatement pstatement = conn.prepareStatement(query);) {
            pstatement.setString(1, codiceFiscale);
            try (ResultSet result = pstatement.executeQuery();) {
                if (result.next()) {
                    Pagamento pagamento = new Pagamento(result.getString("CodiceFiscale"),
                            result.getInt("ImportoAnnuale"), result.getInt("Adeguamento"),
                            result.getInt("Spese"));
                    return pagamento;
                }
                return null;
            }
        }
    }
}
