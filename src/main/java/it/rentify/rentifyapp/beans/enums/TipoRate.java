package it.rentify.rentifyapp.beans.enums;

public enum TipoRate {
    MENSILE, TRIMESTRALE;

    public static TipoRate fromString(String s) {
        if (s.equals("MENSILE")) {
            return MENSILE;
        } else if (s.equals("TRIMESTRALE")) {
            return TRIMESTRALE;
        } else {
            return null;
        }
    }
}
