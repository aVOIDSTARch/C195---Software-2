package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Contact extends DBObject{
    private int id;
    private String name;
    private String email;
    public static final String[] CONTACT_COL_NAMES = {"Contact_ID", "Contact_Name", "Email"};
    /////QUERY LAMBDA FUNCTIONS/////
    public static final RetrieveAllInterface allContacts = () -> {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    /////CONSTRUCTORS/////
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }
    public Contact(ResultSet rs) throws SQLException {
        if (rs.next()) {
            this.id = rs.getInt(CONTACT_COL_NAMES[0]);
            this.name = rs.getString(CONTACT_COL_NAMES[1]);
            this.email = rs.getString(CONTACT_COL_NAMES[2]);
        }
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    LocalDate getCreateDate() {
        return null;
    }

    @Override
    String getCreatedBy() {
        return null;
    }

    @Override
    LocalDateTime getLastUpdate() {
        return null;
    }
    @Override
    String getLastUpdatedBy() {
        return null;
    }

    public String getEmail() {
        return email;
    }

}
