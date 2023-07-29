package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class Contact extends DBObject{
    private int id;
    private String name;
    private String email;
    public static final String[] CONTACT_COL_NAMES = {"Contact_ID", "Contact_Name", "Email"};
    public static final RetrieveAllInterface allContacts = () -> {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final VectorOfDBObjectsInterface toVectorOfContacts = (rs) -> {
        Vector<DBObject> contacts = new Vector<DBObject>();
        while (rs.next()){
            int id = rs.getInt(CONTACT_COL_NAMES[0]);
            String name = rs.getString(CONTACT_COL_NAMES[1]);
            String email = rs.getString(CONTACT_COL_NAMES[2]);
            Contact newContact = new Contact(id, name,email);
            contacts.add(newContact);
        }
        return contacts;
    };

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

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
    public static Vector<Contact> toContactVector(Vector<DBObject> vdbo){
        Vector<Contact> contactVector = new Vector<Contact>();
        vdbo.forEach(dbobj -> {
            Contact newContact = (Contact) dbobj;
            contactVector.add(newContact);
        });
        return contactVector;
    }
}
