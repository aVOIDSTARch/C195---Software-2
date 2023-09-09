package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.time.LocalDateTime;
import java.time.ZoneId;


/**
 * Base Class for All LogEvent Subclasses that holds common Event information and methods
 */
public class LogEvent {
    private final String userName;
    private final LocalDateTime attemptDateTime;
    private final ZoneId systemZoneId;
    private final EventType eventType;
    //Enumeration of Evernt Types
    public static enum EventType {LOGIN_ATTEMPT, DB_ACCESS, APPLICATION, EXCEPTION}
    //Enumeration of Application Locations
    public static enum AppLocation {LOGIN_SCENE, WELCOME_HUB, CUSTOMERS, CUSTOMER_CREATE,
        CUSTOMER_UPDATE, SCHEDULING, APPOINTMENT_CREATE, APPOINTMENT_UPDATE, REPORTING, STARTUP, DATETIMEMGMT, DATAMGMT, LOGGER}

    /**
     * Constructor for LogEvent
     * @param userName String user name
     * @param eventType EventType type of Event
     */
    public LogEvent(String userName, EventType eventType){
        this.userName = userName;
        this.eventType = eventType;
        this.attemptDateTime = LocalDateTime.now();
        this.systemZoneId = ZoneId.systemDefault();
    }

    ///// Getters and Setters /////
    public String getUserName() {return userName;}
    public LocalDateTime getAttemptDateTime() {
        return attemptDateTime;
    }
    public ZoneId getSystemZoneId() {return systemZoneId;}
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString(){
        return eventType.toString() + " Username: " +  userName + " " +
                attemptDateTime.toLocalDate().format(DateTimeMgmt.dateOnlyFormatter) + " " +
                attemptDateTime.toLocalTime().format(DateTimeMgmt.timeOnlyFormat) + " " +
                systemZoneId.toString();
    }
}
