package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.*;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.*;
import java.time.LocalDateTime;

import com.mysql.cj.MysqlType;

/**
 * Class for object that contains Appointment data and methods
 **/
public class Appointment extends DBObject{

    ///// Instance Variables /////
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;

    ///// Column Name Array /////
    public static final String[] APPT_COL_NAMES = {"Appointment_ID", "Title", "Description", "Location", "Type", "Start", "End",
            "Create_Date", "Created_By", "Last_Update", "Last_Updated_By", "Customer_ID", "User_ID", "Contact_ID"};


    /////QUERY LAMBDA FUNCTIONS/////

    //Get all appointments from database
    public static final RetrieveAllInterface allAppts = () -> {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    };
    //Create new Appointment in database
    public static final CreateInterface insertAppointment = (thisAppt) -> {
        //Local variable setup
        Appointment anAppt = (Appointment) thisAppt;
        //INSERT STRING
        String sql = "INSERT INTO APPOINTMENTS (TITLE, DESCRIPTION, LOCATION, TYPE, START, END, CREATE_DATE, CREATED_BY, " +
                "LAST_UPDATE, LAST_UPDATED_BY, CUSTOMER_ID, USER_ID, CONTACT_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //REMEMBER BIND VARS START INDEX AT 1
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // Throws SQLException
        ps.setString(1, anAppt.getName());
        ps.setString(2,anAppt.getDescription());
        ps.setString(3, anAppt.getLocation());
        ps.setString(4, anAppt.getType());
        ps.setObject(5,DateTimeMgmt.convertToLDTInZone(anAppt
                .getStart(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC), MysqlType.DATETIME);
        ps.setObject(6,DateTimeMgmt.convertToLDTInZone(anAppt
                .getEnd(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC), MysqlType.DATETIME);
        ps.setObject(7,DateTimeMgmt.convertToLDTInZone(anAppt
                .getCreateDate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC), MysqlType.DATETIME);
        ps.setString(8, anAppt.getCreatedBy());
        ps.setTimestamp(9, Timestamp.valueOf(DateTimeMgmt.convertToLDTInZone(anAppt
                .getLastUpdate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC)));
        ps.setString(10, anAppt.getLastUpdatedBy());
        ps.setInt(11,anAppt.getCustomerId());
        ps.setInt(12, anAppt.getUserId());
        ps.setInt(13,anAppt.getContactId());
        return ps.executeUpdate();
    };
    //Update existing Appointment in database
    public static final UpdateInterface updateAppointment = (thisAppt) -> {
        //Local variable setup
        Appointment anAppt = (Appointment) thisAppt;
        //INSERT STRING
        String sql = "UPDATE APPOINTMENTS SET TITLE = ?, DESCRIPTION = ?, LOCATION = ?, TYPE = ?, START = ?, END = ?, CREATE_DATE = ?, CREATED_BY = ?, " +
                "LAST_UPDATE = ?, LAST_UPDATED_BY = ?, CUSTOMER_ID = ?, USER_ID = ?, CONTACT_ID = ? WHERE APPOINTMENT_ID = ?";
        //REMEMBER BIND VARS START INDEX AT 1
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // Throws SQLException
        ps.setString(1, anAppt.getName());
        ps.setString(2,anAppt.getDescription());
        ps.setString(3, anAppt.getLocation());
        ps.setString(4, anAppt.getType());
        ps.setObject(5,DateTimeMgmt.convertToLDTInZone(anAppt
                .getStart(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC), MysqlType.DATETIME);
        ps.setObject(6,DateTimeMgmt.convertToLDTInZone(anAppt
                .getEnd(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC), MysqlType.DATETIME);
        ps.setObject(7,DateTimeMgmt.convertToLDTInZone(anAppt
                .getCreateDate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC), MysqlType.DATETIME);
        ps.setString(8, anAppt.getCreatedBy());
        ps.setTimestamp(9, Timestamp.valueOf(DateTimeMgmt.convertToLDTInZone(anAppt
                .getLastUpdate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC)));
        ps.setString(10, anAppt.getLastUpdatedBy());
        ps.setInt(11, anAppt.getCustomerId());
        ps.setInt(12, anAppt.getUserId());
        ps.setInt(13,anAppt.getContactId());
        ps.setInt(14, anAppt.getId());
        return ps.executeUpdate();
    };
    //Delete Appointment from database
    public static final DeleteInterface deleteApptByID = (apptID) -> {
        String sql = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, (int) apptID.getValue());
        return ps.executeUpdate();
    };

    ///// CONSTRUCTORS /////
    /**
     * Constructor for Appointment object that requires inputs for all variables
     * @param id int Appointment ID
     * @param title String Appointment title or name
     * @param createDate LocalDateTime creation date
     * @param createdBy String user that created the object
     * @param lastUpdate LocalDateTime last time object was updated
     * @param lastUpdatedBy String last user that updated the object
     * @param description String Appointment description
     * @param location String Appointment location
     * @param type String Appointment type
     * @param start LocalDateTime start time
     * @param end LocalDateTime end time
     * @param customerId int customer ID
     * @param userId int user ID
     * @param contactId int  contact ID
     */
    public Appointment(int id, String title, LocalDateTime createDate, String createdBy,
                       LocalDateTime lastUpdate, String lastUpdatedBy, String description,
                       String location, String type, LocalDateTime start, LocalDateTime end,
                       int customerId, int userId, int contactId) {
        this.id = id;
        this.name = title;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

    }
    /**
     * Constructor for Appointment object that accepts a ResultSet
     * @param rs ResultSet from Appointment table query
     * @throws SQLException occurs when SQL retrieve command fails
     */
    public Appointment(ResultSet rs) throws SQLException {
            if(rs != null) {
            this.id = rs.getInt(APPT_COL_NAMES[0]);
            this.name = rs.getString(APPT_COL_NAMES[1]);
            this.description = rs.getString(APPT_COL_NAMES[2]);
            this.location = rs.getString(APPT_COL_NAMES[3]);
            this.type = rs.getString(APPT_COL_NAMES[4]);
            this.start = DateTimeMgmt.convertToLDTInZone(rs.getDate(APPT_COL_NAMES[5]).toLocalDate()
                    .atTime(rs.getTime(APPT_COL_NAMES[5]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.end = DateTimeMgmt.convertToLDTInZone(rs.getDate(APPT_COL_NAMES[6]).toLocalDate()
                    .atTime(rs.getTime(APPT_COL_NAMES[6]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.createDate = DateTimeMgmt.convertToLDTInZone(rs.getDate(APPT_COL_NAMES[7]).toLocalDate()
                    .atTime(rs.getTime(APPT_COL_NAMES[7]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.createdBy = rs.getString(APPT_COL_NAMES[8]);
            this.lastUpdate = DateTimeMgmt.convertToLDTInZone(rs.getTimestamp(APPT_COL_NAMES[9])
                    .toLocalDateTime(),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.lastUpdatedBy = rs.getString(APPT_COL_NAMES[10]);
            this.customerId = rs.getInt(APPT_COL_NAMES[11]);
            this.userId = rs.getInt(APPT_COL_NAMES[12]);
            this.contactId = rs.getInt(APPT_COL_NAMES[13]);
        }
    }

    ///// SETTERS FOR APPTS /////
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public String getType() {
        return type;
    }
    public LocalDateTime getStart() {
        return start;
    }
    public LocalDateTime getEnd() {
        return end;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getUserId() {
        return userId;
    }
    public int getContactId() {
        return contactId;
    }
    @Override
    public int getId() {
        return this.id;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public LocalDateTime getCreateDate() {
        return this.createDate;
    }
    @Override
    public String getCreatedBy() {
        return this.createdBy;
    }
    @Override
    public LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }
    @Override
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    ///// TableView Column Value Getters /////
    public String getUserNameIdCombo(){return DataMgmt.getUserById(this.getUserId()).getIdName();}
    public String getCustomerNameIdCombo(){return DataMgmt.getCustomerById(this.getCustomerId()).getIdName();}
    public String getContactNameIdCombo(){return DataMgmt.getContactById(this.getContactId()).getIdName();}
    public String getStartString(){return getStart().format(DateTimeMgmt.dateAndTimeformatter);}
    public String getEndString(){return getEnd().format(DateTimeMgmt.dateAndTimeformatter);}
}
