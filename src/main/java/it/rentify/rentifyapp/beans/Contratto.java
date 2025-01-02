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

    public Contratto(String codiceFiscale, Boolean iva, Date dataInizio, TipoContratto tipo, TipoRate rate) {
        this.codiceFiscale = codiceFiscale;
        this.iva = iva;
        this.dataInizio = dataInizio;
        this.tipo = tipo;
        this.rate = rate;
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

}
