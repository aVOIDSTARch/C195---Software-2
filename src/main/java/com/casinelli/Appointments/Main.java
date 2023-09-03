package com.casinelli.Appointments;


import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.JDBC;

import com.casinelli.Appointments.Helper.DataMgmt;

import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Helper.Logger;


import com.casinelli.Appointments.Model.Appointment;
import com.casinelli.Appointments.Model.LogEvent;
import javafx.application.Application;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import java.time.LocalDateTime;

public class Main extends Application {
    //Create Logger Instance
    public static final Logger logger = new Logger();

    /**
     * @param stage Initial Stage for Application to Contain FXML scenes
     * @throws IOException Occurs when the FXML file cannot be found - nested NullPointerException
     * Changes scene to Login Scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(I18nMgmt.translate("LoginSceneTitle"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        //Log Logger Initialized to Application Event Log
        Logger.logLoggerStartUp();
        //Setup i18n
        I18nMgmt.setup();
        //open connection to db
        JDBC.openConnection();
        //Initialize Data Setup
        DataMgmt.initializeApplicationData();
        //Test code to be removed

        //Start JavaFX interface
        launch();
        //disconnect from db
        JDBC.closeConnection();
    }
}