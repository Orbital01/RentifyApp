package it.rentify.rentifyapp.beans.enums;

public enum TipoContratto {
    COMMERICALE, RESIDENZIALE;

    public static TipoContratto fromString(String tipo) {
        if (tipo.equalsIgnoreCase("commerciale")) {
            return COMMERICALE;
        }
        if (tipo.equalsIgnoreCase("residenziale")) {
            return RESIDENZIALE;
        }
        return null;
    }
}
