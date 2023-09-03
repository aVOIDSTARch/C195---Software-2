package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.*;
import java.time.LocalDateTime;



public class User extends DBObject{

    private String password;
    public static final String[] USER_COL_NAMES = {"User_ID", "User_Name", "Password", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};
    public static final RetrieveInterface userPassword = (userName) -> {
        String sql = "SELECT * FROM USERS WHERE PASSWORD = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName.getValue().toString());
        return ps.executeQuery();
    };
    public static final RetrieveInterface getUserByName = (userName) -> {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName.getValue().toString());
        return ps.executeQuery();
    };
    public static final RetrieveAllInterface allUsers = () -> {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    };


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
    public String getPassword() {
        return "";
    }

}
