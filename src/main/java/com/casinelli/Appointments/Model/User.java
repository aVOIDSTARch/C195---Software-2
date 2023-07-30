package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class User extends DBObject{

    private String password;
    public static final String[] USER_COL_NAMES = {"User_ID", "User_Name", "Password", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};
    public static final RetrieveInterface userPassword = (userName) -> {
        String sql = "SELECT * FROM USERS WHERE PASSWORD = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName.getValue().toString());
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getUserByName = (userName) -> {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName.getValue().toString());
        ResultSet rs = ps.executeQuery();
        return rs;
    };


    public User(int id, String name,String password, LocalDate createDate, String createdBy,
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
        while (rs.next()) {
            this.id = rs.getInt(USER_COL_NAMES[0]);
            this.name = rs.getString(USER_COL_NAMES[1]);
            this.password = "";
            this.createDate = rs.getDate(USER_COL_NAMES[3]).toLocalDate();
            this.createdBy = rs.getString(USER_COL_NAMES[4]);
            this.lastUpdate = rs.getTimestamp(USER_COL_NAMES[5]).toLocalDateTime();
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
    LocalDate getCreateDate() {
        return this.createDate;
    }

    @Override
    String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    @Override
    String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }
    public String getPassword() {
        return "";
    }

}
