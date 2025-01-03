package it.rentify.rentifyapp.beans;

import it.rentify.rentifyapp.beans.enums.TipoContratto;
import it.rentify.rentifyapp.beans.enums.TipoRate;

import java.util.Date;

public class Contratto {
    private String codiceFiscale;
    private Boolean iva;
    private Date dataInizio;

    private TipoContratto tipo;
    private TipoRate rate;

    private int importoAnnuale;
    private int adeguamento;
    private int spese;


    public Contratto(String codiceFiscale, Boolean iva, Date dataInizio, TipoContratto tipo, TipoRate rate, int importoAnnuale, int adeguamento, int spese) {
        this.codiceFiscale = codiceFiscale;
        this.iva = iva;
        this.dataInizio = dataInizio;
        this.tipo = tipo;
        this.rate = rate;
        this.importoAnnuale=importoAnnuale;
        this.adeguamento=adeguamento;
        this.spese=spese;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public Boolean getIva() {
        return iva;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public TipoContratto getTipo() {
        return tipo;
    }

    public TipoRate getRate() {
        return rate;
    }

    public void setIva(Boolean iva) {
        this.iva = iva;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public void setTipo(TipoContratto tipo) {
        this.tipo = tipo;
    }

    public void setRate(TipoRate rate) {
        this.rate = rate;
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
