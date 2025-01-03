package it.rentify.rentifyapp.controllers;

import it.rentify.rentifyapp.DB.AffittoDAO;
import it.rentify.rentifyapp.DB.AffittuarioDAO;
import it.rentify.rentifyapp.DB.ContrattoDAO;
import it.rentify.rentifyapp.beans.Affitto;
import it.rentify.rentifyapp.beans.Affittuario;
import it.rentify.rentifyapp.beans.Contratto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CheckAllRents {
    Connection conn = null;

    //chiamo questo metodo per controllare tutti gli affitti mensili e vedere se sono stati pagati
    public ArrayList<Affittuario> checkMonthlyRent(ArrayList<Affittuario> affittuari, Connection conn) {

        //prendo la connessione al DB
        this.conn = conn;

        //prendo tutti gli affittuari e tutti i contratti
        AffittuarioDAO affittuarioDAO = new AffittuarioDAO(conn);
        ArrayList<Affittuario> affittuariDaControllare = null;
        ContrattoDAO contrattoDAO = new ContrattoDAO(conn);
        ArrayList<Contratto> contratti = null;

        //prendo tutti gli affitti
        AffittoDAO affittoDAO = new AffittoDAO(conn);
        ArrayList<Affitto> affitti = null;

        try {
            affittuariDaControllare = affittuarioDAO.getAffittuari();
            contratti = contrattoDAO.getAllContratti();
            affitti = affittoDAO.getAffitti();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //prendo la data odierna
        Date data = new Date(System.currentTimeMillis());

        //creo la lista di affittuari che non hanno pagato l'affitto
        ArrayList<Affittuario> affittuariNoPagato = new ArrayList<Affittuario>();

        for (Affittuario affittuario : affittuariDaControllare) {
            for (Contratto contratto : contratti) {
                if (affittuario.getCodiceFiscale().equals(contratto.getCodiceFiscale())) {
                    //verifico che i contratti mensili siano stati pagati
                    if (contratto.getRate().toString().equals("MENSILE")) {

                        for (Affitto affitto : affitti) {
                            if (affitto.getCodiceFiscale().equals(contratto.getCodiceFiscale())) {
                                if (affitto.getScadenza().before(data) && !affitto.getPagato()) {
                                    System.out.println("L'affitto mensile di " + affittuario.getNome() + " " + affittuario.getCognome() + " è scaduto e non è stato pagato");
                                    affittuariNoPagato.add(affittuario);
                                }
                            }
                        }

                    }
                }
            }
        }
        return affittuariNoPagato;
    }

    //chiamo questo metodo per verificare tutti gli affitti trimestrali e vedere se sono stati pagati
    public ArrayList<Affittuario> check3MonthRent(ArrayList<Affittuario> affittuari, Connection conn){
        //prendo la connessione al DB
        this.conn = conn;

        //prendo tutti gli affittuari e tutti i contratti
        AffittuarioDAO affittuarioDAO = new AffittuarioDAO(conn);
        ArrayList<Affittuario> affittuariDaControllare = null;
        ContrattoDAO contrattoDAO = new ContrattoDAO(conn);
        ArrayList<Contratto> contratti = null;

        //prendo tutti gli affitti
        AffittoDAO affittoDAO = new AffittoDAO(conn);
        ArrayList<Affitto> affitti = null;

        try {
            affittuariDaControllare = affittuarioDAO.getAffittuari();
            contratti = contrattoDAO.getAllContratti();
            affitti = affittoDAO.getAffitti();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //prendo la data odierna
        Date data = new Date(System.currentTimeMillis());

        //creo la lista di affittuari che non hanno pagato l'affitto
        ArrayList<Affittuario> affittuariNoPagato = new ArrayList<Affittuario>();

        for (Affittuario affittuario : affittuariDaControllare) {
            for (Contratto contratto : contratti) {
                if (affittuario.getCodiceFiscale().equals(contratto.getCodiceFiscale())) {
                    //verifico che i contratti mensili siano stati pagati
                    if (contratto.getRate().toString().equals("TRIMESTRALE")) {

                        for (Affitto affitto : affitti) {
                            if (affitto.getCodiceFiscale().equals(contratto.getCodiceFiscale())) {
                                if (affitto.getScadenza().before(data) && !affitto.getPagato()) {
                                    System.out.println("L'affitto trimestrale di " + affittuario.getNome() + " " + affittuario.getCognome() + " è scaduto e non è stato pagato");
                                    affittuariNoPagato.add(affittuario);
                                }
                            }
                        }

                    }
                }
            }
        }
        return affittuariNoPagato;
    }

    //chiamo questo metodo per verificare e inserire gli affitti in base al tipo di contratto
    public void checkPayments (Connection conn){

        ContrattoDAO contrattoDAO = new ContrattoDAO(conn);
        ArrayList<Contratto> contratti = null;
        AffittoDAO affittoDAO = new AffittoDAO(conn);
        ArrayList<Affitto> affitti = null;

        try {
            contratti = contrattoDAO.getAllContratti();
            affitti = affittoDAO.getAffitti();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //prendo la data odierna
        Date data = new Date(System.currentTimeMillis());

        //contratto è mensile
        for (Contratto contratto : contratti) {
            if(contratto.getRate().toString().equals("MENSILE")){

                //controllo che l'ultimo affitto entro un mese sia stato inserito nel DB
                LocalDate dataM = data.toLocalDate().minusMonths(1);
                Date dataMax = Date.valueOf(dataM);

                for (Affitto affitto : affitti) {
                    if (affitto.getCodiceFiscale().equals(contratto.getCodiceFiscale())) {
                        if (affitto.getScadenza().before(dataMax)) {

                            System.out.println("L'affitto mensile di " + contratto.getCodiceFiscale() + " è scaduto");
                            //calcolo l'importo
                            int importo;
                            if(contratto.getIva()){
                                importo = (((contratto.getImportoAnnuale()+contratto.getAdeguamento())*22/100) //da controllare
                                        + contratto.getSpese())/12;
                            }else {
                                importo = (contratto.getImportoAnnuale()+contratto.getAdeguamento()+contratto.getSpese())/12;
                            }

                            //inserisco il nuovo affitto
                            Affitto AffittoNuovo = new Affitto(contratto.getCodiceFiscale(), importo,
                                    Date.valueOf(data.toLocalDate().plusMonths(1)),
                                    false);

                            try {
                                affittoDAO.insertAffitto(AffittoNuovo);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }
            }
        }

        //contratto è trimestrale
        for (Contratto contratto : contratti) {
            if(contratto.getRate().toString().equals("TRIMESTRALE")){

                //controllo che l'ultimo affitto entro un mese sia stato inserito nel DB
                LocalDate dataM = data.toLocalDate().minusMonths(3);
                Date dataMax = Date.valueOf(dataM);

                for (Affitto affitto : affitti) {
                    if (affitto.getCodiceFiscale().equals(contratto.getCodiceFiscale())) {
                        if (affitto.getScadenza().before(dataMax)) {

                            System.out.println("L'affitto trimestrale di " + contratto.getCodiceFiscale() + " è scaduto");
                            //calcolo l'importo
                            int importo;
                            if(contratto.getIva()){
                                importo = (((contratto.getImportoAnnuale()+contratto.getAdeguamento())*22/100) //da controllare
                                        + contratto.getSpese())/4;
                            }else {
                                importo = (contratto.getImportoAnnuale()+contratto.getAdeguamento()+contratto.getSpese())/4;
                            }

                            //inserisco il nuovo affitto
                            Affitto AffittoNuovo = new Affitto(contratto.getCodiceFiscale(), importo,
                                    Date.valueOf(data.toLocalDate().plusMonths(1)),
                                    false);

                            try {
                                affittoDAO.insertAffitto(AffittoNuovo);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }
            }
        }



    }
}
