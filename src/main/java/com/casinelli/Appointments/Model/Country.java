package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;


/**
 * Class for object containing Country data and methods
 */
public class Country extends DBObject{
    ///// Column Names Array /////
    public static final String[] COUNTRY_COL_NAMES = {"Country_ID", "Country", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};

    /////QUERY LAMBDA FUNCTIONS/////

    //Get all countries from Database
    public static final RetrieveAllInterface allCountries = () -> {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    };

    /////CONSTRUCTORS/////
    /**
     * Constructor for Country object requiring inputs for all variables
     * @param id int country ID
     * @param name String country name
     * @param createDate LocalDateTime creation date
     * @param createdBy String user who created the object
     * @param lastUpdate LocalDateTime last tiem modified
     * @param lastUpdatedBy String user who last modified the object
     */
    public Country(int id, String name, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

    }
    /**
     * Constructor that accepts a ResultSet
     * @param rs ResultSet from Country table query
     * @throws SQLException occurs when SQL retrieve command fails
     */
    public Country(ResultSet rs) throws SQLException {
        if(rs != null) {
            this.id = rs.getInt(COUNTRY_COL_NAMES[0]);
            this.name = rs.getString(COUNTRY_COL_NAMES[1]);
            this.createDate = DateTimeMgmt.convertToLDTInZone(rs.getDate(COUNTRY_COL_NAMES[2]).toLocalDate()
                    .atTime(rs.getTime(COUNTRY_COL_NAMES[2]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.createdBy = rs.getString(COUNTRY_COL_NAMES[3]);
            this.lastUpdate =DateTimeMgmt.convertToLDTInZone(rs.getTimestamp(COUNTRY_COL_NAMES[4])
                    .toLocalDateTime(),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.lastUpdatedBy = rs.getString(COUNTRY_COL_NAMES[5]);
        }
    }

    ///// Getters and Setters /////
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
