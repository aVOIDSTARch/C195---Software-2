package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Class to Store User Object
 */
public class User extends DBObject{
    ///// Instance Variables /////
    private String password;
    ///// Column Names Array /////
    public static final String[] USER_COL_NAMES = {"User_ID", "User_Name", "Password", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};

    ///// Lambda Functions for Database Interactions /////

    //Get user from database using by USER_NAME

    public static final RetrieveInterface getUserByName = (userName) -> {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName.getValue().toString());
        return ps.executeQuery();
    };
    //Get all Users in Database
    public static final RetrieveAllInterface allUsers = () -> {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    };


    /**
     * Contructor for user from all params
     * @param id int user id
     * @param name String user name
     * @param password String user password
     * @param createDate LocalDateTime created date
     * @param createdBy String creator name
     * @param lastUpdate LocalDateTime last updated date and time
     * @param lastUpdatedBy String last updating user
     */
    public User(int id, String name,String password, LocalDateTime createDate, String createdBy,
                LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.password = password;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Costructor of User object from a ResultSet
     * @param rs ResultSet of User object data
     * @throws SQLException occurs when SQL retrieve command fails
     */
    public User(ResultSet rs) throws SQLException {
        if (rs != null) {
            this.id = rs.getInt(USER_COL_NAMES[0]);
            this.name = rs.getString(USER_COL_NAMES[1]);
            this.password = "";
            this.createDate = DateTimeMgmt.convertToLDTInZone(rs.getDate(USER_COL_NAMES[3]).toLocalDate()
                    .atTime(rs.getTime(USER_COL_NAMES[3]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.createdBy = rs.getString(USER_COL_NAMES[4]);
            this.lastUpdate = DateTimeMgmt.convertToLDTInZone(rs.getTimestamp(USER_COL_NAMES[5])
                    .toLocalDateTime(),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.lastUpdatedBy = rs.getString(USER_COL_NAMES[6]);
        }

    }

    ///// Getters and Setters /////
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }
    @Override
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    //NOTE: Password Access is intentionally denied
    public String getPassword() {
        return "";
    }

}
