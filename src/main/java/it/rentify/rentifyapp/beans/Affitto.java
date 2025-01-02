package it.rentify.rentifyapp.beans;

import java.util.Date;

public class Affitto {
    private String codiceFiscale;
    private int importo;
    private Date Scadenza;
    private boolean pagato;

    public Affitto(String codiceFiscale, int importo, Date scadenza, boolean pagato) {
        this.codiceFiscale = codiceFiscale;
        this.importo = importo;
        this.Scadenza = scadenza;
        this.pagato = pagato;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public int getImporto() {
        return importo;
    }

    public Date getScadenza() {
        return Scadenza;
    }

    public boolean getPagato() {
        return pagato;
    }

    public Boolean setPagato(boolean pagato) {
        return this.pagato = pagato;
    }
}
