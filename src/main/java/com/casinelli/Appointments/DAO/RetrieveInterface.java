package com.casinelli.Appointments.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RetrieveInterface {
    ResultSet getRowsFromDB(Value<?> matchValue) throws SQLException;
}
