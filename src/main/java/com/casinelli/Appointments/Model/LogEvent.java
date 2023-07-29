package com.casinelli.Appointments.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class LogEvent {
    private String userName;
    private boolean successful;
    private LocalDate attemptDate;
    private LocalTime attemptTime;
    private ZoneId systemZoneId;
    private EventType eventType;
    public static enum EventType {LOGIN_ATTEMPT, DB_ACCESS, APPLICATION};

    public LogEvent(String userName, boolean successful, EventType eventType){
        this.userName = userName;
        this.successful = successful;
        this.eventType = eventType;
        this.attemptDate = LocalDate.now();
        this.attemptTime = LocalTime.now();
        this.systemZoneId = ZoneId.systemDefault();
    }

    public String getUserName() {
        return userName;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public LocalDate getAttemptDate() {
        return attemptDate;
    }

    public LocalTime getAttemptTime() {
        return attemptTime;
    }

    public ZoneId getSystemZoneId() {
        return systemZoneId;
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString(){

        return eventType.toString() + " Username: " +  userName + " " + attemptDate.toString() + " " + attemptTime.toString()
                + " " + systemZoneId.toString() + " Successful: " + successful;
    }
}
