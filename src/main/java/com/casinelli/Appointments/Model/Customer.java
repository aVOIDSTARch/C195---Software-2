package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.*;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;

import java.sql.*;
import java.time.*;


public class Customer extends DBObject{
    ///// Instance Variables /////
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    ///// Column Name Array /////
    public static final String[] CUSTOMER_COL_NAMES = {"Customer_ID", "Customer_Name", "Address", "Postal_Code", "Phone",
            "Create_Date", "Created_By", "Last_Update", "Last_Updated_By", "Division_ID"};

    ///// QUERY LAMBDA FUNCTIONS /////

    //Get all Customers from DB
    public static final RetrieveAllInterface allCustomers = () -> {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        return ps.executeQuery();
    };
    //Create new Customer in Database
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
    //Update existing Customer in Database
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
    //Delete Customer from Database
    public static final DeleteInterface deleteCustByID = (custID) -> {
        String sql = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, (int) custID.getValue());
        return ps.executeUpdate();
    };

    ///// CONSTRUCTORS /////
    /**
     * Constructor for Customer Object requiring inputs for all variables
     * @param id int customer ID
     * @param name String customer name
     * @param address String customer address
     * @param postalCode String customer postal code
     * @param phone String customer phone number
     * @param createDate LocalDateTime creation date
     * @param createdBy String user who create the object
     * @param lastUpdate LocalDateTime last update date
     * @param lastUpdatedBy String user who last updated teh object
     * @param divisionId int division ID
     */
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
    /**
     * Constructor for Customer object from ResultSet
     * @param rs ResultSet from Customer table query
     * @throws SQLException occurs when the SQL retrieve command fails
     */
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address){this.address = address;}
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode){this.postalCode = postalCode;}
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {this.phone = phone;}
    public int getDivisionId() {
        return divisionId;
    }
    public void setDivisionId(int divisionId) {this.divisionId = divisionId;}

    /**
     * Checks if this customer has any Appointments scheduled
     * @return boolean true if Customer has Appointments scheduled
     */
    public boolean hasAppointments(){
        return DataMgmt.getNumberOfApptsByCustomer(this) > 0;
    }
}
