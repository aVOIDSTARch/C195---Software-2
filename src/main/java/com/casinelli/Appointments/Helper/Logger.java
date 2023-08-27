package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Model.LogEvent;


import java.io.*;

public class Logger {
    public static final String loginFileName = "login_activity.txt";
    public static final String exceptionsFileName = "exception_log.txt";
    public static final String databaseFileName = "database_log.txt";
    public static final String applicationFileName = "application_event_log.txt";

    public Logger(){
        System.out.println("Logger initialized.");
    }
    public boolean log(LogEvent event){
        boolean isSuccessful = false;
        String logFilePath = "";
        if(event.getEventType() == LogEvent.EventType.LOGIN_ATTEMPT){
            logFilePath = loginFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        if(event.getEventType() == LogEvent.EventType.EXCEPTION){
            logFilePath = exceptionsFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        if(event.getEventType() == LogEvent.EventType.DB_ACCESS){
            logFilePath = databaseFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        if(event.getEventType() == LogEvent.EventType.APPLICATION){
            logFilePath = applicationFileName;
            if(write(event, logFilePath)){
                isSuccessful = true;
            }
        }
        return isSuccessful;
    }

    private boolean write(LogEvent event, String logFile){
        boolean isSuccessful = false;
        try{
            FileWriter fw = new FileWriter(logFile, true);
            PrintWriter loginPW = new PrintWriter(fw);
            loginPW.println(event.toString());
            loginPW.close();
            System.out.println("File write successful");
            isSuccessful = true;
        }catch(IOException ioe){
            //dialog box
            System.out.println("File access failure. Check file name.");
        }
        return isSuccessful;
    }
}
