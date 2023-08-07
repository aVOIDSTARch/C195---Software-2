package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;


public class Country extends DBObject{
    public static final String[] COUNTRY_COL_NAMES = {"Country_ID", "Country", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};
    /////QUERY LAMBDA FUNCTIONS/////
    public static final RetrieveAllInterface allCountries = () -> {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getCountryById = (countryId) -> {
        String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, Integer.getInteger(countryId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };

    /////CONSTRUCTORS/////
    public Country(int id, String name, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

    }
    public Country(ResultSet rs) throws SQLException {
        if(rs != null) {
            this.id = rs.getInt(COUNTRY_COL_NAMES[0]);
            this.name = rs.getString(COUNTRY_COL_NAMES[1]);
            this.createDate = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getDate(COUNTRY_COL_NAMES[2]).toLocalDate()
                    .atTime(rs.getTime(COUNTRY_COL_NAMES[2]).toLocalTime()));
            this.createdBy = rs.getString(COUNTRY_COL_NAMES[3]);
            this.lastUpdate =DateTimeMgmt.convertUTCtoLocalTimeZone( rs.getTimestamp(COUNTRY_COL_NAMES[4]).toLocalDateTime());
            this.lastUpdatedBy = rs.getString(COUNTRY_COL_NAMES[5]);
        }
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
