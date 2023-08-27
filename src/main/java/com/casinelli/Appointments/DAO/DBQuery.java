package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.DBObject;
import com.casinelli.Appointments.Model.DatabaseEvent;
import com.casinelli.Appointments.Model.LogEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBQuery {

    public static int create(CreateInterface ci, DBObject dbo) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.CREATE);
        return ci.insertIntoDB(dbo);
    }
    public static ResultSet retrieve(RetrieveInterface ri, Value<?> matchValue) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.RETRIEVE);
        return ri.getRowsFromDB(matchValue);
    }
    public static ResultSet retrieveAll(RetrieveAllInterface rai) throws SQLException{
        logDBEvent(DatabaseEvent.DBEventType.RETRIEVE);
        return rai.getAllRecords();
    }
    public static int update(UpdateInterface ui,DBObject object) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.UPDATE);
        return ui.updateDB(object);
    }
    public static int delete(DeleteInterface di, Value<?> matchValue) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.DELETE);
        return di.deleteFromDB( matchValue);
    }

    private static void logDBEvent(DatabaseEvent.DBEventType dbEvent){
        DatabaseEvent dbE = new DatabaseEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.DB_ACCESS,
                dbEvent);
        Main.logger.log(dbE);
    }
}
