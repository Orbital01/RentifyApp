package it.rentify.rentifyapp.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import it.rentify.rentifyapp.DB.AffittuarioDAO;
import it.rentify.rentifyapp.DB.DBConnection;
import it.rentify.rentifyapp.beans.Affittuario;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShowHome {

    public void start1(Stage primaryStage) throws Exception {


        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html"); // Indica l'estensione dei file
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        //recupero la connessione al database
        DBConnection db = new DBConnection();
        Connection conn = null;

        if (db.getConnection() != null) {
            conn = db.getConnection();
        } else {
            System.out.println("Errore nella connessione al database");
            return;
        }


        // Crea una lista di affittuari
        ArrayList<Affittuario> affittuari = new ArrayList<>();
        AffittuarioDAO affittuarioDAO = new AffittuarioDAO(conn);

        Context context = new Context();

        try {
            affittuari = affittuarioDAO.getAffittuari();
        } catch (SQLException e) {
            //messaggio di errore tramite tag thymeleaf
            context.setVariable("error", "Errore nel recupero degli affittuari");
        }

        // Imposta il contesto Thymeleaf con i dati
        context.setVariable("affittuari", affittuari);
        context.setVariable("db", conn);

        try {

            String renderedHtml = templateEngine.process("home", context);

            WebView webView = new WebView();
            webView.getEngine().loadContent(renderedHtml);


            // Configura la scena e mostra la finestra
            Scene scene = new Scene(webView, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Elenco Affittuari");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore nel rendering del template: " + e.getMessage());
        }

    }

}
