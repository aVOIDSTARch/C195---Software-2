package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public abstract class DataMgmt {
    private static final RetrieveAllInterface[] allDataRetrievers = {Division.all1stLvlDivisions, Appointment.allAppts,
        Country.allCountries, Customer.allCustomers, Contact.allContacts};
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<ObservableList<DBObject>> allData = FXCollections.observableArrayList(new ObservableList[]{allCountries, allContacts,
            allCustomers, allAppts, allDivisions});
    public static void initializeApplicationData(){

    }
    public static Vector<DBObject> fromRStoVector(VectorOfDBObjectsInterface vdboi, ResultSet rs) throws SQLException {
        return vdboi.toDBObjectVector(rs);
    }
    private static Vector<ResultSet> getAllResultSets() throws SQLException {
        Vector<ResultSet> vrs = new Vector<ResultSet>();
        for (RetrieveAllInterface aInterface : allDataRetrievers) {
            ResultSet newRS = DBQuery.retrieveAll(aInterface);
            vrs.add(newRS);
        }
    }
}
