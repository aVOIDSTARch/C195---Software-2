package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.ApplicationEvent;
import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;
import javafx.scene.control.Alert;

import java.io.*;

/**
 * Object to log events to multiple files
 */
public class Logger {
    ///// File Name Variables /////
    public static final String loginFileName = "login_activity.txt";
    public static final String exceptionsFileName = "exception_log.txt";
    public static final String databaseFileName = "database_log.txt";
    public static final String applicationFileName = "application_event_log.txt";

    ///// Log Event Methods /////

    /**
     * Logs different events to appropriate text file
     * @param event LogEvent indicating type of event
     * @return boolean true if successfully written to file
     */
    public boolean log(LogEvent event){
        boolean isSuccessful = false;
        String logFilePath;
        //Log login attempts
        if(event.getEventType() == LogEvent.EventType.LOGIN_ATTEMPT){
            logFilePath = loginFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        //Log exception events
        if(event.getEventType() == LogEvent.EventType.EXCEPTION){
            logFilePath = exceptionsFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        //Log Database Access ATTEMPTS
        if(event.getEventType() == LogEvent.EventType.DB_ACCESS){
            logFilePath = databaseFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        //Log notable Application events
        if(event.getEventType() == LogEvent.EventType.APPLICATION){
            logFilePath = applicationFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    /**
     * Log the logger startup
     */
    public static void logLoggerStartUp(){
        ApplicationEvent startEvent = new ApplicationEvent("Daemon", LogEvent.EventType.APPLICATION,
                LogEvent.AppLocation.STARTUP, "Logger Successfully Initialized");
        Main.logger.log(startEvent);
    }

    /**
     * Write event to file specified
     * @param event LogEvnet that should be logged
     * @param logFile String file name of file to log event to
     * @return boolean true if event is successfully written to log
     */
    private boolean write(LogEvent event, String logFile){
        boolean isSuccessful = false;
        try{
            FileWriter fw = new FileWriter(logFile, true);
            PrintWriter loginPW = new PrintWriter(fw);
            loginPW.println(event.toString());
            loginPW.close();
            isSuccessful = true;
        }catch(IOException ioe){
            //dialog box
            ExceptionEvent thisEvent = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.LOGGER, ioe);
            Main.logger.log(thisEvent);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"LoggerClassTitle", "failedLogEventHeader",
                    "failedLogEventContent").showAndWait();
        }
        return isSuccessful;
    }
}
