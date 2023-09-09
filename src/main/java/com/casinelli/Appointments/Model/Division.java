package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Class for storing Division data and methods
 */
public class Division extends DBObject {

    private int countryId;
    ///// Array of Column Names for Database Access /////
    public static final String[] DIVISION_COL_NAMES = {"Division_ID", "Division",  "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By", "Country_ID"};

    /////QUERY LAMBDA FUNCTIONS/////

    //Retrieve List of All Divisions
    public static final RetrieveAllInterface all1stLvlDivisions = () -> {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    };
    //Retrieve Divisions by Country ID
    public static final RetrieveInterface getDivisionsWithCountryId = (countryId) -> {
        int cntryID = (int) countryId.getValue();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,cntryID);
        return ps.executeQuery();
    };

    /////CONSTRUCTORS/////

    /**
     * Constructor for a Division object requiring inputs for all variables
     * @param id int division ID
     * @param name String division name
     * @param createDate LocalDateTime createion date
     * @param createdBy String name of user that created the object
     * @param lastUpdate LocalDateTime last modify date
     * @param lastUpdatedBy String name of last user to modify object
     * @param countryId int country ID
     */
    public Division(int id, String name, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                    String lastUpdatedBy, int countryId) {
        this.countryId = countryId;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

    }
    /**
     * Constructor for a Division object using a ResultSet from the database
     * @param rs ResultSet from a first level division table query
     * @throws SQLException occurs when retrieve command fails
     */
    public Division(ResultSet rs) throws SQLException {
        if (rs != null) {
            this.id = rs.getInt(DIVISION_COL_NAMES[0]);
            this.name = rs.getString(DIVISION_COL_NAMES[1]);
            this.createDate = DateTimeMgmt.convertToLDTInZone(rs.getDate(DIVISION_COL_NAMES[2]).toLocalDate()
                    .atTime(rs.getTime(DIVISION_COL_NAMES[2]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.createdBy = rs.getString(DIVISION_COL_NAMES[3]);
            this.lastUpdate = DateTimeMgmt.convertToLDTInZone(rs.getTimestamp(DIVISION_COL_NAMES[4])
                    .toLocalDateTime(),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.lastUpdatedBy = rs.getString(DIVISION_COL_NAMES[5]);
            this.countryId = rs.getInt(DIVISION_COL_NAMES[6]);
        }
    }

    ///// Getters and Setters /////
    public int getCountryId() {
        return countryId;
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