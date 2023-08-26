package com.casinelli.Appointments.DAO;

import java.sql.SQLException;

public interface DeleteInterface {
    int deleteFromDB(Value<?> matchValue) throws SQLException;
}
