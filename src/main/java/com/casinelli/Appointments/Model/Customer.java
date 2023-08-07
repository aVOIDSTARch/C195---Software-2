package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.CreateInterface;
import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.*;
import java.time.*;
import java.util.Vector;

public class Customer extends DBObject{
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    public static final String[] CUSTOMER_COL_NAMES = {"Customer_ID", "Customer_Name", "Address", "Postal_Code", "Phone",
            "Create_Date", "Created_By", "Last_Update", "Last_Updated_By", "Division_ID"};

    /////QUERY LAMBDA FUNCTIONS/////
    public static final RetrieveAllInterface allCustomers = () -> {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getCustById = (custId) -> {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(custId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final RetrieveInterface getCustByDivId = (divId) -> {
        String sql = "SELECT * FROM CUSTOMERS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(divId.getValue().toString()));
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final CreateInterface insertCustomer = (thisCust) -> {
        //Local variable setup
        Customer aCustomer = (Customer) thisCust;
        String username = "DEFAULT NAME";
        //Update Username
        if (DataMgmt.getCurrentUser() != null){
            username = DataMgmt.getCurrentUser().getName();
        }
        //INSERT STRING
        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_NAME, ADDRESS, POSTAL_CODE, PHONE, CREATE_DATE, CREATED_BY, " +
                "LAST_UPDATE, LAST_UPDATED_BY, DIVISION_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        //REMEMBER BIND VARS START INDEX AT 1
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // Throws SQLException
        ps.setString(1, aCustomer.getName());
        ps.setString(2, aCustomer.getAddress());
        ps.setString(3, aCustomer.getPostalCode());
        ps.setString(4, aCustomer.getPhone());
        //ps.setDate(5, Date.valueOf(aCustomer.getCreateDate()));
        ps.setString(6, username);
        ps.setTimestamp(7, Timestamp.valueOf(aCustomer.getLastUpdate()));
        ps.setString(8, username);
        ps.setInt(9,aCustomer.getDivisionId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    };

    /////CONSTRUCTORS/////
    public Customer(int id, String name, String address, String postalCode, String phone,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                    String lastUpdatedBy, int divisionId) {
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;


    }
    public Customer(ResultSet rs) throws SQLException {
        if (rs != null){
            this.id = rs.getInt(CUSTOMER_COL_NAMES[0]);
            this.name = rs.getString(CUSTOMER_COL_NAMES[1]);
            this.address = rs.getString(CUSTOMER_COL_NAMES[2]);
            this.postalCode = rs.getString(CUSTOMER_COL_NAMES[3]);
            this.phone = rs.getString(CUSTOMER_COL_NAMES[4]);
            this.createDate = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getDate(CUSTOMER_COL_NAMES[5]).toLocalDate()
                    .atTime(rs.getTime(CUSTOMER_COL_NAMES[5]).toLocalTime()));
            this.createdBy = rs.getString(CUSTOMER_COL_NAMES[6]);
            this.lastUpdate = DateTimeMgmt.convertUTCtoLocalTimeZone(rs.getTimestamp(CUSTOMER_COL_NAMES[7]).toLocalDateTime());
            this.lastUpdatedBy = rs.getString(CUSTOMER_COL_NAMES[8]);
            this.divisionId = rs.getInt(CUSTOMER_COL_NAMES[9]);
        }
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public int getDivisionId() {
        return divisionId;
    }
    public String getCountryName(){
        return DataMgmt.getCountryNameFromDivId(this.divisionId);
    }
    public String getDivisionName(){
        return DataMgmt.getDivisionNameFromDivId(this.divisionId);
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
    public static Vector<Customer> toCustomerVector(Vector<DBObject> vdbo){
        Vector<Customer> custVector = new Vector<Customer>();
        vdbo.forEach(dbobj -> {
            Customer newCust = (Customer) dbobj;
            custVector.add(newCust);
        });
        return custVector;
    }
}
