package com.casinelli.Appointments.Model;

/**
 * Class for an Application Event
 */
public class ApplicationEvent extends LogEvent{

    private final AppLocation scene;
    private final String eventDescription;

    /**
     * Constructor for ApplicationEvent object
     * @param userName user name
     * @param eventType LogEvent.EventType type of event
     * @param scene LogEvent.AppLocation location in application that event occurred
     * @param eventDescription String description of event
     */
    public ApplicationEvent(String userName, EventType eventType, AppLocation scene, String eventDescription) {
        super(userName, eventType);
        this.scene = scene;
        this.eventDescription = eventDescription;
    }

    ///// Getters /////
    public AppLocation getScene() { return scene; }

    public String getEventDescription() { return eventDescription; }

    @Override
    public String toString(){
        return super.toString() + " in Location: " + scene + "\n" +
                eventDescription;
    }
}
