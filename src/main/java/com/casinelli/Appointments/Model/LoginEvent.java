package com.casinelli.Appointments.Model;

public class LoginEvent extends LogEvent{

    private final boolean isSuccessful;
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
