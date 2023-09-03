package com.casinelli.Appointments.Model;

public class ExceptionEvent extends LogEvent {
    //Subclass Instance Variables
    private final Exception e;
    private final AppLocation scene;

    /**
     * Constructor for ExceptionEvent objects
     * @param userName String user name
     * @param eventType LogEvent.EventType type of Event
     * @param scene LogEvent.AppLocation location of Event in application
     * @param e Exception actual exception that occurred
     */
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
