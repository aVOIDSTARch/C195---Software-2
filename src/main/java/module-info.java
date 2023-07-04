module com.casinelli.c195 {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires java.sql;

    opens com.casinelli.c195 to javafx.fxml;
    exports com.casinelli.c195;
}