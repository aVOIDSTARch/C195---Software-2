package com.casinelli.Appointments.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public interface VectorOfDBObjectsInterface {
    Vector<DBObject> toDBObjectVector(ResultSet rs) throws SQLException;
}
