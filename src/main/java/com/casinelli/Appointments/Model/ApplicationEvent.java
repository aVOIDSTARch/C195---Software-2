package com.casinelli.Appointments.Model;

public class ApplicationEvent extends LogEvent{

    private final AppLocation scene;
    private final String eventDescription;
    public ApplicationEvent(String userName, EventType eventType, AppLocation scene, String eventDescription) {
        super(userName, eventType);
        this.scene = scene;
        this.eventDescription = eventDescription;
    }

    public AppLocation getScene() { return scene; }

    public String getEventDescription() { return eventDescription; }

    @Override
    public String toString(){
        return super.toString() + " in Location: " + scene + "\n" +
                eventDescription;
    }
}
