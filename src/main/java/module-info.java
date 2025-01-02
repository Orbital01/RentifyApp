module it.rentify.rentifyapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires javafx.web;
    requires thymeleaf;

    opens it.rentify.rentifyapp to javafx.fxml;
    exports it.rentify.rentifyapp;
}