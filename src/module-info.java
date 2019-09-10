module Image.Insert {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.jfoenix;
    requires java.sql;

    opens sample;
    opens sample.controller;
}