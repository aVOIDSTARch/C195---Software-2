module com.casinelli.c195LJC {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.j;

    opens com.casinelli.Appointments to javafx.fxml;
    exports com.casinelli.Appointments;
    exports com.casinelli.Appointments.Controller;
    opens com.casinelli.Appointments.Controller to javafx.fxml;
}