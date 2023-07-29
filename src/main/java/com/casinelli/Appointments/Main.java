package com.casinelli.Appointments;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Helper.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //Create Logger Instance
    public static final Logger logger = new Logger();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Setup i18n
        I18nMgmt.setup();
        //open connection to db
        JDBC.openConnection();
        //Create Logger Instance

        //start JavaFX interface
        launch();
        //disconnect from db
        JDBC.closeConnection();


    }
}