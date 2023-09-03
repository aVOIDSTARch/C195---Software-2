package com.casinelli.Appointments.Model;

/**
 * Class to store Login Events
 */
public class LoginEvent extends LogEvent{

    private final boolean isSuccessful;

    /**
     * Constructor for LoginEvent objects
     * @param userName String user name
     * @param eventType LogEvent.EventType type of LogEvent
     * @param isSuccessful boolean true if Login was successful
     */
    public LoginEvent(String userName, EventType eventType, boolean isSuccessful) {
        super(userName, eventType);
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    @Override
    public String toString(){
        return super.toString() + " Successful: " + isSuccessful;
    }
}
