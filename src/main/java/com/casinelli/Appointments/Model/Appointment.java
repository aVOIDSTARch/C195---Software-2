package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public static final RetrieveAllInterface allAppts = () -> {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final VectorOfDBObjectsInterface toVectorOfAppts = (rs) -> {
        Vector<DBObject> appts = new Vector<DBObject>();
        while (rs.next()){
            int id = rs.getInt(APPT_COL_NAMES[0]);
            String title = rs.getString(APPT_COL_NAMES[1]);
            String description = rs.getString(APPT_COL_NAMES[2]);
            String location = rs.getString(APPT_COL_NAMES[3]);
            String type = rs.getString(APPT_COL_NAMES[4]);
            LocalDateTime start = rs.getDate(APPT_COL_NAMES[5]).toLocalDate().atTime(rs.getTime(APPT_COL_NAMES[5]).toLocalTime());
            LocalDateTime end = rs.getDate(APPT_COL_NAMES[6]).toLocalDate().atTime(rs.getTime(APPT_COL_NAMES[6]).toLocalTime());
            LocalDate createDate = rs.getDate(APPT_COL_NAMES[7]).toLocalDate();
            String createdBy = rs.getString(APPT_COL_NAMES[8]);
            LocalDateTime lastUpdate = rs.getTimestamp(APPT_COL_NAMES[9]).toLocalDateTime();
            String lastUpdatedBy = rs.getString(APPT_COL_NAMES[10]);
            int custID = rs.getInt(APPT_COL_NAMES[11]);
            int userID = rs.getInt(APPT_COL_NAMES[12]);
            int contactID = rs.getInt(APPT_COL_NAMES[13]);
            Appointment newAppt = new Appointment(id,title,createDate,createdBy, lastUpdate, lastUpdatedBy,description,
                    location,type,start,end,custID, userID,contactID);
            appts.add(newAppt);
        }
        return appts;
    };
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
    public static Vector<Appointment> toApptVector(Vector<DBObject> vdbo){
        Vector<Appointment> apptVector = new Vector<Appointment>();
        vdbo.forEach(dbobj -> {
            Appointment newAppt = (Appointment) dbobj;
            apptVector.add(newAppt);
        });
        return apptVector;
    }
}
