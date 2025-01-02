package it.rentify.rentifyapp.beans;

public class Pagamento {
    private String codiceFiscale;
    private int importoAnnuale;
    private int adeguamento;
    private int spese;

    public Pagamento(String CF, int importoAnnuale, int adeguamento, int spese) {
        this.codiceFiscale = CF;
        this.importoAnnuale = importoAnnuale;
        this.adeguamento = adeguamento;
        this.spese = spese;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public int getImportoAnnuale() {
        return importoAnnuale;
    }

    public int getAdeguamento() {
        return adeguamento;
    }

    public int getSpese() {
        return spese;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setImportoAnnuale(int importoAnnuale) {
        this.importoAnnuale = importoAnnuale;
    }

    public void setAdeguamento(int adeguamento) {
        this.adeguamento = adeguamento;
    }

    public void setSpese(int spese) {
        this.spese = spese;
    }

}
