package com.casinelli.Appointments.Model;

public class ExceptionEvent extends LogEvent {

    private final Exception e;
    private final AppLocation scene;
    public ExceptionEvent(String userName, EventType eventType, AppLocation scene, Exception e) {
        super(userName, eventType);
        this.e = e;
        this.scene = scene;
    }
    public Exception getE() {
        return e;
    }
    @Override
    public String toString(){
        return super.toString() + "in " + scene + "\n" +
                e.getMessage();
    }
}
