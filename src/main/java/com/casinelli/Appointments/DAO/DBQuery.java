package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Model.DBObject;

import java.sql.ResultSet;

public abstract class DBQuery {

    public static int create(CreateInterface ci, DBObject dbo){
        return ci.insertIntoDB(dbo);
    }
    public static ResultSet retrieve(RetrieveInterface ri){
        return ri.selectFromDB();
    }
    public int update(UpdateInterface ui,String columnName, Value matchValue, DBObject object){
        return ui.updateDB(columnName, matchValue, object);
    }
    public int delete(DeleteInterface di,String columnName, Value matchValue){
        return di.deleteFromDB(columnName, matchValue);
    }
}
