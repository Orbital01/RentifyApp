package it.rentify.rentifyapp.beans;

public class Affittuario {
    private String nome;
    private String cognome;
    private String codiceFiscale;

    public Affittuario(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        try {
            verifyCodiceFiscale(codiceFiscale);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        this.codiceFiscale = codiceFiscale;
    }

    private void verifyCodiceFiscale(String codiceFiscale) {
        if (codiceFiscale.length() != 16) {
            throw new IllegalArgumentException("Il codice fiscale deve essere lungo 16 caratteri");
        }
    }

}

