package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.DBObject;
import com.casinelli.Appointments.Model.DatabaseEvent;
import com.casinelli.Appointments.Model.LogEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract class that centralizes all access to the database with logging functionality
 */
public abstract class DBQuery {

    /**
     * Writes a new row to the database and logs the access attempt
     * @param ci CreateInterface lambda expression containing specific database create instructions
     * @param dbo DBObject superclass for passing the object to be written to the database
     * @return int number of lines successfully written to database
     * @throws SQLException occurs when the SQL statements fails
     */
    //Create
    public static int create(CreateInterface ci, DBObject dbo) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.CREATE);
        return ci.insertIntoDB(dbo);
    }
    //Retrieve
    /**
     * Writes a new row to the database and logs the access attempt
     * @param ri RetrieveInterface lambda expression containing specific database retrieve instructions
     * @param matchValue Value object that holds a query argument
     * @return ResultSet containing the data retrieved from the database
     * @throws SQLException occurs when the SQL statements fails
     */
    public static ResultSet retrieve(RetrieveInterface ri, Value<?> matchValue) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.RETRIEVE);
        return ri.getRowsFromDB(matchValue);
    }
    /**
     * Writes a new row to the database and logs the access attempt
     * @param rai RetrieveAllInterface lambda expression containing specific database retrieve instructions
     * @return ResultSet containing the data retrieved from the database
     * @throws SQLException occurs when the SQL statements fails
     */
    public static ResultSet retrieveAll(RetrieveAllInterface rai) throws SQLException{
        logDBEvent(DatabaseEvent.DBEventType.RETRIEVE);
        return rai.getAllRecords();
    }
    //Update
    /**
     * Writes a new row to the database and logs the access attempt
     * @param ui UpdateInterface lambda expression containing specific database update instructions
     * @param dbo DBObject superclass for passing the object to be deleted from the database
     * @return int number of lines successfully written to database
     * @throws SQLException occurs when the SQL statements fails
     */
    public static int update(UpdateInterface ui,DBObject dbo) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.UPDATE);
        return ui.updateDB(dbo);
    }
    //Delete
    /**
     * Deletes a row from the database and logs the access attempt
     * @param di DeleteInterface lambda expression containing specific database delete instructions
     * @param matchValue Value object that holds a query argument
     * @return int number of lines successfully deleted from database
     * @throws SQLException occurs when the SQL statements fails
     */
    public static int delete(DeleteInterface di, Value<?> matchValue) throws SQLException {
        logDBEvent(DatabaseEvent.DBEventType.DELETE);
        return di.deleteFromDB( matchValue);
    }

    // Helper Function to Log DB Access Events
    private static void logDBEvent(DatabaseEvent.DBEventType dbEvent){
        DatabaseEvent dbE = new DatabaseEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.DB_ACCESS,
                dbEvent);
        Main.logger.log(dbE);
    }
}
