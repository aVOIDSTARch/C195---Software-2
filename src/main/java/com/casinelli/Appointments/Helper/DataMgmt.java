package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class DataMgmt {
    private static User currentUser;
    /////Observable Lists From DB Tables/////
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    /////Class Specific Functions/////
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

    /////List Insertion Functions/////
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
    /////APPOINTMENT FUNCTIONS/////
    public static ObservableList<Appointment> getAllApptsList(){
        return allAppts;
    }
    public static int getApptCountForToday(){
        AtomicInteger apptCount = new AtomicInteger();
        allAppts.forEach(appt -> {
            if(appt.getStart().toLocalDate().equals(LocalDate.now())){
                apptCount.getAndIncrement();
            }
        });
        return apptCount.get();
    }
    public static int getAllApptCount(){
        return allAppts.size();
    }
    public static int getTotalNumAppts(){
        return allAppts.size();
    }
    /////COUNTRY FUNCTIONS/////

    public static ObservableList<String> getAllCountryNames(){
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        allCountries.forEach(country->
                countryNames.add(country.getName()));
        return countryNames;
    }
    public static int getCountryIdFromCntryName(String cntryName){
        AtomicReference<Integer> cntryId = new AtomicReference<>(0);
        allCountries.forEach(cntry ->{
            if(cntry.getName() == cntryName) {
                cntryId.set(cntry.getId());
            }
        });
        return cntryId.get();
    }

    /////CONTACT FUNCTIONS/////
    public static ObservableList<Contact> getAllContactsList(){
        return allContacts;
    }
    public static int getApptCountByContactId(int contactId){
        AtomicInteger apptCount = new AtomicInteger();
        allAppts.forEach(appt ->  {
            if (appt.getContactId() == contactId){
                apptCount.getAndIncrement();
            }
        });
        return apptCount.get();
    }
    /////CUSTOMER FUNCTIONS/////
    public static ObservableList<Customer> getAllCustomersList(){
        return allCustomers;
    }


    /////DIVISION FUNCTIONS/////
    public static ObservableList<String> getListOfDivNamesByCountryId(int countryId) throws SQLException {
        ObservableList<String> divNames = FXCollections.observableArrayList();
        Value<Integer> queryValue = new Value<Integer>(countryId);
        ResultSet rs = DBQuery.retrieve(Division.getDivisionsWithCountryId, queryValue);
        while(rs.next()){
            String newDivName = rs.getString(Division.DIVISION_COL_NAMES[1]);
            divNames.add(newDivName);
        }
        return divNames;
    }

    public static String getCountryNameFromDivId(int divId) {
        AtomicReference<String> countryName = new AtomicReference<>("");
        AtomicInteger countryId = new AtomicInteger();
        allDivisions.forEach(division -> {
            if (division.getId() == divId){
                countryId.set(division.getCountryId());
            }
        });
        allCountries.forEach(country -> {
            if(country.getId() == countryId.get()){
                countryName.set(country.getName());
            }
        });
        return countryName.get();
    }
    public static String getDivisionNameFromDivId(int divisionId){
        AtomicReference<String> divName = new AtomicReference<>("");
        allDivisions.forEach(div ->{
            if(div.getId() == divisionId) {
                divName.set(div.getName());
            }
        });
        return divName.get();
    }
}
