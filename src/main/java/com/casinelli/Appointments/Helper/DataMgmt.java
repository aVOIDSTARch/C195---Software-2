package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Helper class to contain and manage locally stored data and application variables
 */
public abstract class DataMgmt {
    ///// Default Objects /////
    private static final User defaultUser = new User(9999999, "DEFAULT_BEFORE_LOGIN", "noPASS", LocalDateTime.now(),
            "DEFAULT_BEFORE_LOGIN",LocalDateTime.now(), "DEFAULT_BEFORE_LOGIN");
    private static User currentUser = defaultUser;
    private static final Customer defaultCustomer = new Customer(8888888,"DEFAULT", "ADDRESS", "POSTALCODE", "PHONE",
            LocalDateTime.now(), defaultUser.getName(), LocalDateTime.now(), defaultUser.getName(),1);
    private static Customer currentCustomer = defaultCustomer;

    ///// Month Names List Creation /////
    private static final String[] englishMonths = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
            "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private static final String[] francaisMois = {"JANVIER", "FEVRIER", "MARS", "AVRIL", "MAI", "JUIN",
            "JULI", "AOUT", "SEPTEMBRE", "OCTOBRE", "NOVEMBRE", "DECEMBRE"};
    private static final ObservableList<String> englishMonthNames = FXCollections
            .observableArrayList(Arrays.stream(englishMonths).collect(Collectors.toList()));
    private static final ObservableList<String> francaisMoisNames = FXCollections
            .observableArrayList(Arrays.stream(francaisMois).collect(Collectors.toList()));

    ///// Comparator Objects /////
    public static final AppointmentComparatorByStartDate sortApptsByStartDate = new AppointmentComparatorByStartDate();


    ///// Path to Translation Files /////
    public static final String PACKAGE_PATH = "com/casinelli/Appointments/Appts";

    /////Observable Lists From Database Tables/////
    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    private static final ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static final ObservableList<User> allUsers = FXCollections.observableArrayList();

    ///// Observable Lists Compiled for Specific Application Needs /////
    private static final ObservableList<Appointment> thisCustsAppts = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> thisWeekAppts = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> thisMonthAppts = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> apptsInNext15Mins = FXCollections.observableArrayList();
    private static final ObservableList<String> allApptTypes = FXCollections.observableArrayList();


    /////Class Specific Functions/////
    /**
     * Populates all Observable Lists for Application use from database
     */
    public static void initializeApplicationData() {
        try{
            populateAllAppts(DBQuery.retrieveAll(Appointment.allAppts));
            populateAllContacts(DBQuery.retrieveAll(Contact.allContacts));
            populateAllCountries(DBQuery.retrieveAll(Country.allCountries));
            populateAllCusts(DBQuery.retrieveAll(Customer.allCustomers));
            populateAllDivisions(DBQuery.retrieveAll(Division.all1stLvlDivisions));
            populateAllUsers(DBQuery.retrieveAll(User.allUsers));
            populateApptsInNext15Mins();
            populateThisCustsAppts(currentCustomer);
            populateThisMonthAppts();
            populateThisWeekAppts();
            populateApptsInNext15Mins();
            populateAllApptTypes();
            }catch(SQLException e){
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.DATAMGMT, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"HelperClassTitle","sqlErrorHeader",
                    "sqlRetrieveErrorContent").showAndWait();
        }
    }

    ///// Integer Generator /////

    /**
     * Creates a list of integers up to and including ending number
     * @param startingWith int to start with
     * @param endingWith int to end with (inclusive)
     * @return List of Integers
     */
    public static List<Integer> makeIntList_Inclusive(int startingWith, int endingWith){
        return IntStream.range(startingWith, endingWith + 1)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Split string into separate words and add sequentially to a String Vector
     * @param str String to be split
     * @return Vector of Strings that contains the ordered words of a string
     */
    ///// String Splitter /////
    public static Vector<String> getVectorOfWordsFromString(String str){
        Scanner scanner = new Scanner(str);
        Vector<String> words = new Vector<>();
        while (scanner.hasNext()){
            String thisWord = scanner.next();
            words.add(thisWord);
        }
        return words;
    }
    ///// Current User Getter-Setters /////
    public static User getCurrentUser() {return currentUser;}
    public static void setCurrentUser(User thisUser) {DataMgmt.currentUser = thisUser;}
    public static void setUserToDefault() {setCurrentUser(defaultUser);}
    public static void setCurrentCustomer(Customer thisCustomer) {DataMgmt.currentCustomer = thisCustomer;}

    /////List Insertion Functions/////

    //These methods add the appropriate objects to the corresponding ObservableList after clearing the list
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

    /**
     * Creates an ObservableList containing a Specific Customer object's scheduled Appointments
     * Lambda expression used to filter list by Customer provided
     * @param thisCust Customer to get Appointments from
     */
    private static void populateThisCustsAppts(Customer thisCust){
        thisCustsAppts.clear();
        allAppts.forEach(appt -> {
            if(appt.getCustomerId() == thisCust.getId()){
                thisCustsAppts.add(appt);
            }
        });
    }

    /**
     * Populates an ObservableList with Appointment objects that occur in the current month
     * Lambda expression used to filter the list by current month
     */
    private static void populateThisMonthAppts(){
        thisMonthAppts.clear();
        allAppts.forEach(appt -> {
            if(DateTimeMgmt.isSameYearMonth(appt.getStart(),LocalDateTime.now())){
                thisMonthAppts.add(appt);
            }
        });
    }

    /**
     * Populates an ObservableList with Appointment objects that occur in the current week
     * Lambda expression used to filter the list by current week
     */
    private static void populateThisWeekAppts(){
        thisWeekAppts.clear();
        allAppts.forEach(appt -> {
            if(DateTimeMgmt.isSameYearWeek(ZonedDateTime.of(appt.getStart(), DateTimeMgmt.ZONE_SYS),
                    DateTimeMgmt.getLocalZDTNow())){
                thisWeekAppts.add(appt);
            }
        });
    }
    /**
     * Populates an ObservableList with Appointment objects that occur in the next 15 minutes in the users local time
     * Lambda expression used to filter the list to Appointments that occur next 15 minutes in the users local time
     */
    private static void populateApptsInNext15Mins() {
        apptsInNext15Mins.clear();
         DataMgmt.getAllApptsList().forEach(appt -> {
                if(DateTimeMgmt.isToday(appt.getStart())
                        && DateTimeMgmt.isInNextFifteenMinutes(appt.getStart().toLocalTime())){
                    apptsInNext15Mins.add(appt);
                }
            });
    }
    private static void populateAllApptTypes(){
        allApptTypes.clear();
        Set<String> uniqueTypes = new HashSet<>();
        DataMgmt.allAppts.forEach(appt -> uniqueTypes.add(appt.getType().toUpperCase()));
        allApptTypes.addAll(uniqueTypes);
    }


    ///// ObservableList Getters /////

    //Countries and Divisions Lists are intentional not provided as they require formatting to use
    public static ObservableList<User> getAllUsersList(){return allUsers;}
    public static ObservableList<Appointment> getAllApptsList(){return allAppts;}
    public static ObservableList<Appointment> getMonthApptsList() {return thisMonthAppts;}
    public static ObservableList<Appointment> getWeekApptsList() {return thisWeekAppts;}
    public static ObservableList<Appointment> getApptsInNext15Mins() {return apptsInNext15Mins;}
    public static ObservableList<Contact> getAllContactsList(){return allContacts;}
    public static ObservableList<Customer> getAllCustomersList(){return allCustomers;}
    public static ObservableList<Appointment> getThisCustsAppts(){
        populateThisCustsAppts(currentCustomer);
        return thisCustsAppts;
    }
    public static ObservableList<String> getMonthNames(Locale locale){
        if(locale == Locale.CANADA_FRENCH){
            return francaisMoisNames;
        }else{
            return englishMonthNames;
        }
    }
    public static ObservableList<String> getAllApptTypes(){return allApptTypes;}


    ///// Class Specific Functions /////

    ///// USERS FUNCTIONS /////

    /**
     * Find a User object by ID
     * Lambda expression used to find the User object that has the matching ID
     * @param id int user id
     * @return User that matches ID
     */
    public static User getUserById(int id){
        AtomicReference<User> aUser = new AtomicReference<>();
        allUsers.forEach(user -> {
            if(user.getId() == id){
                aUser.set(user);
            }
        });
        return aUser.get();
    }

    /**
     * Find a User object by Name
     * Lambda expression used to find the User object that has the matching name
     * @param username String user name
     * @return User that matches the username
     */
    public static User getUserByName(String username){
        AtomicReference<User> aUser = new AtomicReference<>();
        allUsers.forEach(user -> {
            if(Objects.equals(user.getName(), username)){
                aUser.set(user);
            }
        });
        return aUser.get();
    }

    ///// APPOINTMENT FUNCTIONS /////

    /**
     * Gets total number of Appointments scheduled for today in local system time zone
     * Lambda expression used to reduce the list of Appointments to an integer of the count of Appointments occurring today
     * @return int total number of Appointments found
     */
    public static int getApptCountForToday(){
        AtomicInteger apptCount = new AtomicInteger();
        allAppts.forEach(appt -> {
            if(appt.getStart().toLocalDate().equals(LocalDate.now())){
                apptCount.getAndIncrement();
            }
        });
        return apptCount.get();
    }

    /**
     * Calculates total number of Appointments in system
     * @return Total number of appointments
     */
    public static int getTotalNumAppts(){
        initializeApplicationData();
        return allAppts.size();
    }

    ///// COUNTRY FUNCTIONS /////

    /**
     * Compiles a list of country names and stores in an ObservableList
     * Lambda expression used reduce Country objects to their name properties
     * @return ObservableList of Strings of all country names
     */
    public static ObservableList<String> getAllCountryNames(){
        ObservableList<String> countryNames = FXCollections.observableArrayList();
        allCountries.forEach(country->
                countryNames.add(country.getName()));
        return countryNames;
    }

    /**
     * Find Country Name from the Country ID
     * Lambda expression used to find the Country that matches the provided name
     * @param cntryName String country name
     * @return int country ID
     */
    public static int getCountryIdFromCntryName(String cntryName){
        AtomicReference<Integer> cntryId = new AtomicReference<>(0);
        allCountries.forEach(cntry ->{
            if(Objects.equals(cntry.getName(), cntryName)) {
                cntryId.set(cntry.getId());
            }
        });
        return cntryId.get();
    }

    ///// CONTACT FUNCTIONS /////
    /**
     * Calculates number of Appointments assigned to Contact
     * Lambda expression used to reduce the list of Appointments to an integer reflecting the count of Appointments assigned to the contact ID
     * @param contactId int contact ID
     * @return int number of assigned Appointments
     */
    public static int getApptCountByContactId(int contactId){
        AtomicInteger apptCount = new AtomicInteger();
        allAppts.forEach(appt ->  {
            if (appt.getContactId() == contactId){
                apptCount.getAndIncrement();
            }
        });
        return apptCount.get();
    }

    /**
     * Compiles an ObservableList of Appointment objects assigned to a specific Contact object
     * Lambda expression used to select Appointments assigned to a Contact
     * @param thisContact Contact to match for Appointment selection
     * @return ObservableList of Appointments List of Appointment objects that are assigned to the Contact provided
     */
    public static ObservableList<Appointment> getApptsByContact(Contact thisContact){
        ObservableList<Appointment> thisContactsAppts = FXCollections.observableArrayList();
        allAppts.forEach(appt -> {
            if(appt.getContactId() == thisContact.getId()){
                thisContactsAppts.add(appt);
            }
        });
        return thisContactsAppts;
    }

    /**
     * Find contact by contact ID
     * Lambda expression used to find the Contact object with the matching ID property
     * @param id int contact ID
     * @return Contact that matches ID
     */
    public static Contact getContactById(int id){
        AtomicReference<Contact> thisContact = new AtomicReference<>();
        allContacts.forEach(contact ->{
            if(id == contact.getId()){
                thisContact.set(contact);
            }
        });
        return thisContact.get();
    }

    ///// CUSTOMER FUNCTIONS /////
    /**
     * Find customer by customer ID
     * Lambda expression used to find the matching Customer object in a list by its ID
     * @param id int Customer ID
     * @return Customer that matches ID
     */
    public static Customer getCustomerById(int id){
        AtomicReference<Customer> thisCust = new AtomicReference<>();
        allCustomers.forEach(cust ->{
            if(id == cust.getId()){
                thisCust.set(cust);
            }
        });
        return thisCust.get();
    }

    /**
     * Calculate Number of Appointments scheduled for a Customer
     * Lambda expression used to reduce the Appointments list to an integer reflecting the number of Appointment objects assigned to Customer object
     * @param customer Customer to use
     * @return int Number of Appointments scheduled for Customer
     */
    public static int getNumberOfApptsByCustomer(Customer customer) {
        AtomicInteger numAppts = new AtomicInteger();
        allAppts.forEach(appt -> {
            if(appt.getCustomerId() == customer.getId()){
                numAppts.getAndIncrement();
            }
        });
        return numAppts.get();
    }

    /**
     * Builds a List of Division names based on the Country ID specified
     * @param countryId int country ID
     * @return ObservableList of Strings of Division names for the country specified
     * @throws SQLException occurs when SQL retrieve command fails
     */
    /////DIVISION FUNCTIONS/////
    public static ObservableList<String> getListOfDivNamesByCountryId(int countryId) throws SQLException {
        ObservableList<String> divNames = FXCollections.observableArrayList();
        Value<Integer> queryValue = new Value<>(countryId);
        ResultSet rs = DBQuery.retrieve(Division.getDivisionsWithCountryId, queryValue);
        while(rs.next()){
            String newDivName = rs.getString(Division.DIVISION_COL_NAMES[1]);
            divNames.add(newDivName);
        }
        return divNames;
    }
    /**
     * Provides Country Name for a Division ID
     * @param divId int Division ID
     * @return String country name
     */
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
    /**
     * Get Division Name from Division ID
     * @param divisionId int division ID
     * @return String division name
     */
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
