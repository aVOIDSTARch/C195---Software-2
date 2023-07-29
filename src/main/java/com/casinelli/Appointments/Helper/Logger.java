package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Model.LogEvent;

import java.io.*;
import java.util.Scanner;

public class Logger {
    private final String loginFileName = "login_activity.txt";

    public Logger(){
        System.out.println("Logger initialized.");
    }
    public boolean log(LogEvent event){
        boolean isSuccessful = false;
        if(event.getEventType() == LogEvent.EventType.LOGIN_ATTEMPT){
            try{
                FileWriter fw = new FileWriter(loginFileName, true);
                PrintWriter loginPW = new PrintWriter(fw);
                loginPW.println(event.toString());
                loginPW.close();
                System.out.println("File write successful");
                isSuccessful = true;
            }catch(IOException ioe){
                //dialog box
                System.out.println("File access failure. Check file name.");
            }
        }
        return isSuccessful;
    }
}
