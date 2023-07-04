module com.casinelli.c195LJC {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.j;

    opens com.casinelli.c195LJC to javafx.fxml;
    exports com.casinelli.c195LJC;
}