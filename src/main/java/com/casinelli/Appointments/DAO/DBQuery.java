package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBQuery {

    public static int create(CreateInterface ci, DBObject dbo){
        return ci.insertIntoDB(dbo);
    }
    public static ResultSet retrieve(RetrieveInterface ri, Value<?> matchValue) throws SQLException {
        return ri.getRowsFromDB(matchValue);
    }
    public static ResultSet retrieveAll(RetrieveAllInterface rai) throws SQLException{
        return rai.getAllRecords();
    }
    public static int update(UpdateInterface ui,String columnName, Value<?> matchValue, DBObject object){
        return ui.updateDB(columnName, matchValue, object);
    }
    public static int delete(DeleteInterface di,String columnName, Value<?> matchValue){
        return di.deleteFromDB(columnName, matchValue);
    }
}
