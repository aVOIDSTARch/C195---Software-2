package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class LogEvent {
    private final String userName;
    private final LocalDateTime attemptDateTime;
    private final ZoneId systemZoneId;
    private final EventType eventType;
    public static enum EventType {LOGIN_ATTEMPT, DB_ACCESS, APPLICATION, EXCEPTION};

    public static enum AppLocation {LOGIN_SCENE, WELCOME_HUB, CUSTOMERS, CUSTOMER_CREATE,
        CUSTOMER_UPDATE, SCHEDULING, APPOINTMENT_CREATE, APPOINTMENT_UPDATE, REPORTING};


    public LogEvent(String userName, EventType eventType){
        this.userName = userName;
        this.eventType = eventType;
        this.attemptDateTime = LocalDateTime.now();
        this.systemZoneId = ZoneId.systemDefault();
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getAttemptDateTime() {
        return attemptDateTime;
    }

    public ZoneId getSystemZoneId() {
        return systemZoneId;
    }

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
