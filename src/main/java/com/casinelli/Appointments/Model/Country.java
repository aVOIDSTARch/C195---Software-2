package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.Vector;

public class Country extends DBObject{
    public static final String[] COUNTRY_COL_NAMES = {"Country_ID", "Country", "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By"};
    public static final RetrieveAllInterface allCountries = () -> {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final VectorOfDBObjectsInterface toVectorOfCountries = (rs) -> {
        Vector<DBObject> countries = new Vector<DBObject>();
        while (rs.next()){
            int id = rs.getInt(COUNTRY_COL_NAMES[0]);
            String name = rs.getString(COUNTRY_COL_NAMES[1]);
            LocalDate createDate = rs.getDate(COUNTRY_COL_NAMES[2]).toLocalDate();
            String createdBy = rs.getString(COUNTRY_COL_NAMES[3]);
            LocalDateTime lastUpdate = rs.getTimestamp(COUNTRY_COL_NAMES[4]).toLocalDateTime();
            String lastUpdatedBy = rs.getString(COUNTRY_COL_NAMES[5]);
            Country newCountry = new Country(id,name,createDate,createdBy, lastUpdate, lastUpdatedBy);
            countries.add(newCountry);
        }
        return countries;
    };

    public Country(int id, String name, LocalDate createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

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
    public static Vector<Country> toCountryVector(Vector<DBObject> vdbo){
        Vector<Country> countryVector = new Vector<Country>();
        vdbo.forEach(dbobj -> {
            Country newCountry = (Country) dbobj;
            countryVector.add(newCountry);
        });
        return countryVector;
    }
}
