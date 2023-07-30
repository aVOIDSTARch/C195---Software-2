package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class Division extends DBObject {

    private int countryId;
    public static final String[] DIVISION_COL_NAMES = {"Division_ID", "Division",  "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By", "Country_ID"};
    /////QUERY LAMBDA FUNCTIONS/////
    public static final RetrieveAllInterface all1stLvlDivisions = () -> {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getDivisionsWithCountryId = (countryId) -> {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(countryId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getDivisionById = (divId) -> {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(divId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };

    /////CONSTRUCTORS/////
    public Division(int id, String name, LocalDate createDate, String createdBy, LocalDateTime lastUpdate,
                    String lastUpdatedBy, int countryId) {
        this.countryId = countryId;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

    }
    public Division(ResultSet rs) throws SQLException {
        if (rs.next()) {
            this.id = rs.getInt(DIVISION_COL_NAMES[0]);
            this.name = rs.getString(DIVISION_COL_NAMES[1]);
            this.createDate = rs.getDate(DIVISION_COL_NAMES[2]).toLocalDate();
            this.createdBy = rs.getString(DIVISION_COL_NAMES[3]);
            this.lastUpdate = rs.getTimestamp(DIVISION_COL_NAMES[4]).toLocalDateTime();
            this.lastUpdatedBy = rs.getString(DIVISION_COL_NAMES[5]);
            this.countryId = rs.getInt(DIVISION_COL_NAMES[6]);
        }
    }
    public int getCountryId() {
        return countryId;
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