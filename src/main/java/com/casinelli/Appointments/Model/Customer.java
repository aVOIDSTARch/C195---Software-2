package com.casinelli.Appointments.Model;

import com.casinelli.Appointments.DAO.JDBC;
import com.casinelli.Appointments.DAO.RetrieveAllInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Vector;

public class Customer extends DBObject{
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    public static final String[] CUSTOMER_COL_NAMES = {"Customer_ID", "Customer_Name", "Address", "Postal_Code", "Phone",
            "Create_Date", "Created_By", "Last_Update", "Last_Updated_By", "Division_ID"};
    public static final RetrieveAllInterface allCustomers = () -> {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return rs;
    };
    public static final VectorOfDBObjectsInterface toVectorOfCustomers = (rs) -> {
        Vector<DBObject> customers = new Vector<DBObject>();
        while (rs.next()){
            int id = rs.getInt(CUSTOMER_COL_NAMES[0]);
            String name = rs.getString(CUSTOMER_COL_NAMES[1]);
            String address = rs.getString(CUSTOMER_COL_NAMES[2]);
            String postalCode = rs.getString(CUSTOMER_COL_NAMES[3]);
            String phone = rs.getString(CUSTOMER_COL_NAMES[4]);
            LocalDate createDate = rs.getDate(CUSTOMER_COL_NAMES[5]).toLocalDate();
            String createdBy = rs.getString(CUSTOMER_COL_NAMES[6]);
            LocalDateTime lastUpdate = rs.getTimestamp(CUSTOMER_COL_NAMES[7]).toLocalDateTime();
            String lastUpdatedBy = rs.getString(CUSTOMER_COL_NAMES[8]);
            int divisionID = rs.getInt(CUSTOMER_COL_NAMES[9]);
            Customer newCust = new Customer(id,name, address, postalCode, phone,createDate,createdBy,lastUpdate,
                    lastUpdatedBy, divisionID);
            customers.add(newCust);
        }
        return customers;
    };

    public Customer(int id, String name, String address, String postalCode, String phone,
                    LocalDate createDate, String createdBy, LocalDateTime lastUpdate,
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
    public static Vector<Customer> toCustomerVector(Vector<DBObject> vdbo){
        Vector<Customer> custVector = new Vector<Customer>();
        vdbo.forEach(dbobj -> {
            Customer newCust = (Customer) dbobj;
            custVector.add(newCust);
        });
        return custVector;
    }
}
