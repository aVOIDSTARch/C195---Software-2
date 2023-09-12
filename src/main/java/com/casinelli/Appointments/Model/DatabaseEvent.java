package com.casinelli.Appointments.Model;

/**
 * Class for an Event that attempts to access the database
 */
public class DatabaseEvent extends LogEvent{
    ///// Enumeration of Database Event Types /////

    /**
     * Enumeration of the types of database access attempt events that the DatabaseEvent type can build
     */
    public static enum DBEventType {CREATE,RETRIEVE,UPDATE,DELETE,CONNECTION}
    private final DBEventType dbEventType;

    /**
     * Constructor for a DatabaseEvent object
     * @param userName String user name
     * @param eventType LogEvent.EventType type of Event
     * @param dbEventType DatabaseEvent.DBEventType type of database event
     */
    public DatabaseEvent(String userName, EventType eventType, DBEventType dbEventType) {
        super(userName, eventType);
        this.dbEventType = dbEventType;
    }

    public DBEventType getDbEventType() { return dbEventType; }

    @Override
    public String toString(){
       return super.toString() + " " + "Type: " + getDbEventType();
    }
}
