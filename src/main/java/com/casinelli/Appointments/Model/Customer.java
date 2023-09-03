package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.*;
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
        return ps.executeQuery();
    };
    public static final RetrieveInterface getCustById = (custId) -> {
        String sql = "SELECT * FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(custId.getValue().toString()));
        return ps.executeQuery();
    };
    public static final RetrieveInterface getCustByDivId = (divId) -> {
        String sql = "SELECT * FROM CUSTOMERS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Integer.getInteger(divId.getValue().toString()));
        return ps.executeQuery();
    };
    public static final CreateInterface insertCustomer = (thisCust) -> {
        //Local variable setup
        Customer aCustomer = (Customer) thisCust;
        //INSERT STRING
        String sql = "INSERT INTO CUSTOMERS (CUSTOMER_NAME, ADDRESS, POSTAL_CODE, PHONE, CREATE_DATE, CREATED_BY, " +
                "LAST_UPDATE, LAST_UPDATED_BY, DIVISION_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        //REMEMBER BIND VARS START INDEX AT 1
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // Throws SQLException
        ps.setString(1, aCustomer.getName());
        ps.setString(2, aCustomer.getAddress());
        ps.setString(3, aCustomer.getPostalCode());
        ps.setString(4, aCustomer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(
                DateTimeMgmt.convertToLDTInZone(aCustomer
                        .getCreateDate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC)));
        ps.setString(6, aCustomer.getCreatedBy());
        ps.setTimestamp(7, Timestamp.valueOf(
                DateTimeMgmt.convertToLDTInZone(aCustomer
                        .getLastUpdate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC)));
        ps.setString(8, aCustomer.getLastUpdatedBy());
        ps.setInt(9,aCustomer.getDivisionId());
        return ps.executeUpdate();
    };
    public static final UpdateInterface updateCustomer = (thisCust) -> {
        //Local variable setup
        Customer aCustomer = (Customer) thisCust;
        //INSERT STRING
        String sql = "UPDATE CUSTOMERS SET CUSTOMER_NAME = ?, ADDRESS = ?, POSTAL_CODE = ?, PHONE = ?, CREATE_DATE = ?, " +
                "CREATED_BY = ?, LAST_UPDATE = ?, LAST_UPDATED_BY = ?, DIVISION_ID = ? WHERE CUSTOMER_ID = ? ";
        //REMEMBER BIND VARS START INDEX AT 1
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // Throws SQLException
        ps.setString(1, aCustomer.getName());
        ps.setString(2, aCustomer.getAddress());
        ps.setString(3, aCustomer.getPostalCode());
        ps.setString(4, aCustomer.getPhone());
        ps.setTimestamp(5, Timestamp.valueOf(DateTimeMgmt.convertToLDTInZone(aCustomer
                .getCreateDate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC)));
        ps.setString(6, aCustomer.getCreatedBy());
        ps.setTimestamp(7, Timestamp.valueOf(DateTimeMgmt.convertToLDTInZone(aCustomer
                .getLastUpdate(),DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_UTC)));
        ps.setString(8, aCustomer.getLastUpdatedBy());
        ps.setInt(9,aCustomer.getDivisionId());
        ps.setInt(10, aCustomer.getId());
        return ps.executeUpdate();
    };
    public static final DeleteInterface deleteCustByID = (custID) -> {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, (int) custID.getValue());
        return ps.executeUpdate();
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
            this.createDate = DateTimeMgmt.convertToLDTInZone(rs.getDate(CUSTOMER_COL_NAMES[5]).toLocalDate()
                    .atTime(rs.getTime(CUSTOMER_COL_NAMES[5]).toLocalTime()),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.createdBy = rs.getString(CUSTOMER_COL_NAMES[6]);
            this.lastUpdate = DateTimeMgmt.convertToLDTInZone(rs.getTimestamp(CUSTOMER_COL_NAMES[7])
                    .toLocalDateTime(),DateTimeMgmt.ZONE_UTC,DateTimeMgmt.ZONE_SYS);
            this.lastUpdatedBy = rs.getString(CUSTOMER_COL_NAMES[8]);
            this.divisionId = rs.getInt(CUSTOMER_COL_NAMES[9]);
        }
    }
    ///// GETTERS AND SETTERS /////
    public String getAddress() {
        return address;
    }
    public void setAddress(String address){this.address = address;}

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode){this.postalCode = postalCode;};

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {this.phone = phone;}

    public int getDivisionId() {
        return divisionId;
    }
    public void setDivisionId(int divisionId) {this.divisionId = divisionId;}

    ///// SUPPLEMENTAL DATA PROVIDERS /////
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
    public boolean hasAppointments(){
        return DataMgmt.getNumberOfApptsByCustomer(this) > 0;
    }
}
