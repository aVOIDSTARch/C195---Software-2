package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Vector;

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
    /////CONSTRUCTORS/////
    public Appointment(int id, String title, LocalDate createDate, String createdBy,
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
            this.start = rs.getDate(APPT_COL_NAMES[5]).toLocalDate().atTime(rs.getTime(APPT_COL_NAMES[5]).toLocalTime());
            this.end = rs.getDate(APPT_COL_NAMES[6]).toLocalDate().atTime(rs.getTime(APPT_COL_NAMES[6]).toLocalTime());
            this.createDate = rs.getDate(APPT_COL_NAMES[7]).toLocalDate();
            this.createdBy = rs.getString(APPT_COL_NAMES[8]);
            this.lastUpdate = rs.getTimestamp(APPT_COL_NAMES[9]).toLocalDateTime();
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
    int getId() {
        return this.id;
    }

    @Override
    String getName() {
        return this.name;
    }

    @Override
    LocalDate getCreateDate() {
        return this.createDate;
    }

    @Override
    String getCreatedBy() {
        return this.createdBy;
    }

    @Override
    LocalDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    @Override
    String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

}
