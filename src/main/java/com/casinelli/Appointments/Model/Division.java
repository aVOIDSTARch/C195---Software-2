package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class Division extends DBObject {

    private int countryId;
    public static final String[] DIVISION_COL_NAMES = {"Division_ID", "Division",  "Create_Date", "Created_By", "Last_Update",
            "Last_Updated_By", "Country_ID"};
    public static final RetrieveAllInterface all1stLvlDivisions = () -> {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final VectorOfDBObjectsInterface toVectorOfDivisions = (rs) -> {
        Vector<DBObject> divisions = new Vector<DBObject>();
        while (rs.next()){
            int id = rs.getInt(DIVISION_COL_NAMES[0]);
            String name = rs.getString(DIVISION_COL_NAMES[1]);
            LocalDate createDate = rs.getDate(DIVISION_COL_NAMES[2]).toLocalDate();
            String createdBy = rs.getString(DIVISION_COL_NAMES[3]);
            LocalDateTime lastUpdate = rs.getTimestamp(DIVISION_COL_NAMES[4]).toLocalDateTime();
            String lastUpdatedBy = rs.getString(DIVISION_COL_NAMES[5]);
            int countryID = rs.getInt(DIVISION_COL_NAMES[6]);
            Division newDivision = new Division(id,name,createDate,createdBy, lastUpdate, lastUpdatedBy, countryID);
            divisions.add(newDivision);
        }
        return divisions;
    };

    public Division(int id, String name, LocalDate createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.countryId = countryId;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;

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
    public static Vector<Division> toDivisionVector(Vector<DBObject> vdbo){
        Vector<Division> divVector = new Vector<Division>();
        vdbo.forEach(dbobj -> {
            Division newDiv = (Division) dbobj;
            divVector.add(newDiv);
        });
        return divVector;
    }
}