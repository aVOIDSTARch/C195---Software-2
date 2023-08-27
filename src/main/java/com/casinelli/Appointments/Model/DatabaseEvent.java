package com.casinelli.Appointments.Model;

public class DatabaseEvent extends LogEvent{
    public static enum DBEventType {CREATE,RETRIEVE,UPDATE,DELETE};
    private final DBEventType dbEventType;
    public DatabaseEvent(String userName, EventType eventType, DBEventType dbEventType) {
        super(userName, eventType);
        this.dbEventType = dbEventType;
    }

    public DBEventType getDbEventType() { return dbEventType; };

    @Override
    public String toString(){
       return super.toString() + " " + "Type: " + getDbEventType();
    }
}
