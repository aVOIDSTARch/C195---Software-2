package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



public class User extends DBObject{

    private String password;
    public static final String[] USER_COL_NAMES = {"User_ID", "User_Name", "PASSWORD", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};
    public static final RetrieveInterface userPassword = (userName) -> {
        String sql = "SELECT * FROM USERS WHERE PASSWORD = ?";
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

    @Override
    int getId() {
        return this.id;
    }

    @Override
    String getName() {
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
        return password;
    }
}
