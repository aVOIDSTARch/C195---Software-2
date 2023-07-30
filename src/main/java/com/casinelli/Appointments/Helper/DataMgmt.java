package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Vector;

import static com.casinelli.Appointments.DAO.DBQuery.retrieveAll;

public abstract class DataMgmt {
    private static User currentUser;
    /////Observable Lists From DB Tables/////
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();


    public static void initializeApplicationData() {
        try{
            DataMgmt.populateAllAppts(DBQuery.retrieveAll(Appointment.allAppts));
            DataMgmt.populateAllContacts(DBQuery.retrieveAll(Contact.allContacts));
            DataMgmt.populateAllCountries(DBQuery.retrieveAll(Country.allCountries));
            DataMgmt.populateAllCusts(DBQuery.retrieveAll(Customer.allCustomers));
            DataMgmt.populateAllDivisions(DBQuery.retrieveAll(Division.all1stLvlDivisions));
            System.out.println("All Observable Lists Populated");
        }catch(SQLException sqle){
            System.out.println("Failure to Populate Lists");
        }

    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User thisUser) {
        DataMgmt.currentUser = thisUser;
    }
    private static void populateAllAppts(ResultSet rs) throws SQLException {
        allAppts.clear();
        while (rs.next()){
            allAppts.add(new Appointment(rs));
        }
    }
    private static void populateAllCountries(ResultSet rs) throws SQLException {
        allCountries.clear();
        while (rs.next()){
            allCountries.add(new Country(rs));
        }
    }
    private static void populateAllContacts(ResultSet rs) throws SQLException {
        allContacts.clear();
        while (rs.next()){
            allContacts.add(new Contact(rs));
        }
    }
    private static void populateAllDivisions(ResultSet rs) throws SQLException {
        allDivisions.clear();
        while (rs.next()){
            allDivisions.add(new Division(rs));
        }
    }
    private static void populateAllCusts(ResultSet rs) throws SQLException {
        allCustomers.clear();
        while (rs.next()){
            allCustomers.add(new Customer(rs));
        }
    }
}
