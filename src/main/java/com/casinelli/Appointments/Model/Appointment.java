package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.CreateInterface;
import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.*;
import java.time.LocalDateTime;

public class Appointment extends DBObject{
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    public static final String[] APPT_COL_NAMES = {"Appointment_ID", "Title", "Description", "Location", "Type", "Start", "End",
            "Create_Date", "Created_By", "Last_Update", "Last_Updated_By", "Customer_ID", "User_ID", "Contact_ID"};
    /////QUERY LAMBDA FUNCTIONS/////
    public static final RetrieveAllInterface allAppts = () -> {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getApptById = (apptId) -> {
        String sql = "SELECT * FROM APPOINTMENTS WHERE APPOINTMENT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(apptId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getApptsByUserId = (userId) -> {
        String sql = "SELECT * FROM APPOINTMENTS WHERE USER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(userId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getApptsByType = (type) -> {
        String sql = "SELECT * FROM APPOINTMENTS WHERE TYPE = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, (type.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getApptsByContactId = (contactId) -> {
        String sql = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(contactId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final CreateInterface insertAppointment = (thisAppt) -> {
        //Local variable setup
        Appointment anAppt = (Appointment) thisAppt;
        String username = "DEFAULT NAME";
        //Update Username
        if (DataMgmt.getCurrentUser() != null){
            username = DataMgmt.getCurrentUser().getName();
        }
        //INSERT STRING
        String sql = "INSERT INTO APPOINTMENTS (TITLE, DESCRIPTION, LOCATION, TYPE, START, END, CREATE_DATE, CREATED_BY, " +
                "LAST_UPDATE, LAST_UPDATED_BY, CUSTOMER_ID, USER_ID, CONTACT_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //REMEMBER BIND VARS START INDEX AT 1
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // Throws SQLException
        ps.setString(1, anAppt.getName());
        ps.setString(2,anAppt.getDescription());
        ps.setString(3, anAppt.getLocation());
        ps.setString(4, anAppt.getType());
        ps.setTimestamp(5, Timestamp.valueOf(DateTimeMgmt.convertLocalTZtoUTC(anAppt.getStart())));
        ps.setTimestamp(6, Timestamp.valueOf(DateTimeMgmt.convertLocalTZtoUTC(anAppt.getEnd())));
        ps.setTimestamp(7, Timestamp.valueOf(DateTimeMgmt.convertLocalTZtoUTC(anAppt.getCreateDate())));
        ps.setString(8, username);
        ps.setTimestamp(9, Timestamp.valueOf(DateTimeMgmt.convertLocalTZtoUTC(anAppt.getLastUpdate())));
        ps.setString(10, username);
        ps.setInt(11,anAppt.getCustomerId());
        ps.setInt(12, anAppt.getUserId());
        ps.setInt(13,anAppt.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    };
    /////CONSTRUCTORS/////
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
    public Appointment(ResultSet rs) throws SQLException {
            if(rs != null) {
            this.id = rs.getInt(APPT_COL_NAMES[0]);
            this.name = rs.getString(APPT_COL_NAMES[1]);
            this.description = rs.getString(APPT_COL_NAMES[2]);
            this.location = rs.getString(APPT_COL_NAMES[3]);
            this.type = rs.getString(APPT_COL_NAMES[4]);
            this.start = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getDate(APPT_COL_NAMES[5]).toLocalDate()
                    .atTime(rs.getTime(APPT_COL_NAMES[5]).toLocalTime()));
            this.end = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getDate(APPT_COL_NAMES[6]).toLocalDate()
                    .atTime(rs.getTime(APPT_COL_NAMES[6]).toLocalTime()));
            this.createDate = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getDate(APPT_COL_NAMES[7]).toLocalDate()
                    .atTime(rs.getTime(APPT_COL_NAMES[7]).toLocalTime()));
            this.createdBy = rs.getString(APPT_COL_NAMES[8]);
            this.lastUpdate = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getTimestamp(APPT_COL_NAMES[9]).toLocalDateTime());
            this.lastUpdatedBy = rs.getString(APPT_COL_NAMES[10]);
            this.customerId = rs.getInt(APPT_COL_NAMES[11]);
            this.userId = rs.getInt(APPT_COL_NAMES[12]);
            this.contactId = rs.getInt(APPT_COL_NAMES[13]);
        }
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

}
