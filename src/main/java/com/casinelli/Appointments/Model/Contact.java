package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * Class for object that stores Contact data and methods
 */
public class Contact extends DBObject{
    ///// Intance Variables /////
    private int id;
    private String name;
    private String email;
    ///// Column Name Array /////
    public static final String[] CONTACT_COL_NAMES = {"Contact_ID", "Contact_Name", "Email"};

    /////QUERY LAMBDA FUNCTIONS/////

    //Get all contacts from database
    public static final RetrieveAllInterface allContacts = () -> {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };

    /////CONSTRUCTORS/////
    /**
     * Constructor for Contact object that requires inputs for all variables
     * @param id int contact id
     * @param name String contact name
     * @param email String contact email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    /**
     * Constructor for Contact object that accepts a ResultSet object
     * @param rs ResultSet from contact table query
     * @throws SQLException occurs when SQL retrieve command fails
     */
    public Contact(ResultSet rs) throws SQLException {
        if (rs != null) {
            this.id = rs.getInt(CONTACT_COL_NAMES[0]);
            this.name = rs.getString(CONTACT_COL_NAMES[1]);
            this.email = rs.getString(CONTACT_COL_NAMES[2]);
        }
    }

    ///// Setters and Getters /////
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    ///// These getters must be overridden even though the data does not exist
    @Override
    public LocalDateTime getCreateDate() {
        return null;
    }
    @Override
    public String getCreatedBy() {
        return null;
    }
    @Override
    public LocalDateTime getLastUpdate() {
        return null;
    }
    @Override
    public String getLastUpdatedBy() {
        return null;
    }
}
