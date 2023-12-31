package com.casinelli.Appointments.DAO;

import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.DatabaseEvent;
import com.casinelli.Appointments.Model.LogEvent;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Class to control Database connectivity
 */
public abstract class JDBC {

    ///// Connection String Particles /////
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password

    ///// Connection Interface Object /////
    public static Connection connection;

    /**
     * Open database connection
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            DatabaseEvent event = new DatabaseEvent("DEFAULT_BEFORE_LOGIN", LogEvent.EventType.EXCEPTION,
                    DatabaseEvent.DBEventType.CONNECTION);
            Main.logger.log(event);
        }
    }
    /**
     * Close Database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            DatabaseEvent event = new DatabaseEvent("DEFAULT_BEFORE_LOGIN", LogEvent.EventType.EXCEPTION,
                    DatabaseEvent.DBEventType.CONNECTION);
            Main.logger.log(event);
        }
    }
}
