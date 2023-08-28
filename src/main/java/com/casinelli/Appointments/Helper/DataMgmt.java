package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DataMgmt {
    private static final User defaultUser = new User(9999999, "DEFAULT_BEFORE_LOGIN", "noPASS", LocalDateTime.now(),
            "DEFAULT_BEFORE_LOGIN",LocalDateTime.now(), "DEFAULT_BEFORE_LOGIN");
    private static User currentUser = defaultUser;
    /////Observable Lists From DB Tables/////
    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private static final ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static final ObservableList<User> allUsers = FXCollections.observableArrayList();

    /////Class Specific Functions/////
    public static void initializeApplicationData() {
        try{
            DataMgmt.populateAllAppts(DBQuery.retrieveAll(Appointment.allAppts));
            DataMgmt.populateAllContacts(DBQuery.retrieveAll(Contact.allContacts));
            DataMgmt.populateAllCountries(DBQuery.retrieveAll(Country.allCountries));
            DataMgmt.populateAllCusts(DBQuery.retrieveAll(Customer.allCustomers));
            DataMgmt.populateAllDivisions(DBQuery.retrieveAll(Division.all1stLvlDivisions));
            DataMgmt.populateAllUsers(DBQuery.retrieveAll(User.allUsers));
            System.out.println("All Observable Lists Populated");
        }catch(SQLException sqle){
            System.out.println("Failure to Populate Lists");
        }

    }

    ///// Integer Generator /////
    public static List<Integer> makeIntList(int startingWith, int endingWith){
        return IntStream.range(startingWith, endingWith + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    ///// Current User Getter-Setters /////
    public static User getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(User thisUser) {
        DataMgmt.currentUser = thisUser;
    }
    public static void setUserToDefault() {setCurrentUser(defaultUser);}

    /////List Insertion Functions/////
    private static void populateAllUsers(ResultSet rs) throws SQLException {
        allUsers.clear();
        while (rs.next()){
            allUsers.add(new User(rs));
        }
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

    /////USERS FUNCTIONS/////
    public static ObservableList<User> getAllUsersList(){
        return allUsers;
    }
    public static User getUserById(int id){
        AtomicReference<User> aUser = new AtomicReference<>();
        allUsers.forEach(user -> {
            if(user.getId() == id){
                aUser.set(user);
            }
        });
        return aUser.get();
    }
    public static User getUserByName(String username){
        AtomicReference<User> aUser = new AtomicReference<>();
        allUsers.forEach(user -> {
            if(Objects.equals(user.getName(), username)){
                aUser.set(user);
            }
        });
        return aUser.get();
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
    public static ObservableList<Appointment> getApptsByCustomerId(int custID){
        ObservableList<Appointment> thisCustsAppts = FXCollections.observableArrayList();
        getAllApptsList().forEach(appt -> {
            if(appt.getId() == custID){
                thisCustsAppts.add(appt);
            }
        });
        return thisCustsAppts;
    }

    /////COUNTRY FUNCTIONS/////

    public static ObservableList<String> getAllCountryNames(){
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        allCountries.forEach(country->
                countryNames.add(country.getName()));
        return countryNames;
    }
    public static int getCountryIdFromCntryName(String cntryName){
        AtomicReference<Integer> cntryId = new AtomicReference<Integer>(0);
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
    public static Contact getContactById(int id){
        AtomicReference<Contact> thisContact = new AtomicReference<Contact>();
        allContacts.forEach(contact ->{
            if(id == contact.getId()){
                thisContact.set(contact);
            }
        });
        return thisContact.get();
    }

    /////CUSTOMER FUNCTIONS/////
    public static ObservableList<Customer> getAllCustomersList(){
        return allCustomers;
    }
    public static Customer getCustomerById(int id){
        AtomicReference<Customer> thisCust = new AtomicReference<Customer>();
        allCustomers.forEach(cust ->{
            if(id == cust.getId()){
                thisCust.set(cust);
            }
        });
        return thisCust.get();
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
