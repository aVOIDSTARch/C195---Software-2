package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBQuery {

    public static int create(CreateInterface ci, DBObject dbo) throws SQLException {
        return ci.insertIntoDB(dbo);
    }
    public static ResultSet retrieve(RetrieveInterface ri, Value<?> matchValue) throws SQLException {
        return ri.getRowsFromDB(matchValue);
    }
    public static ResultSet retrieveAll(RetrieveAllInterface rai) throws SQLException{
        return rai.getAllRecords();
    }
    public static int update(UpdateInterface ui,DBObject object) throws SQLException {
        return ui.updateDB(object);
    }
    public static int delete(DeleteInterface di, Value<?> matchValue) throws SQLException {
        return di.deleteFromDB( matchValue);
    }
}
