package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.Logger;
import com.casinelli.Appointments.Helper.ReportGenerator;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class ReportingHubController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;
    ///// JAVAFX CONTROLS /////
    @javafx.fxml.FXML
    private Label lblRptSceneAppName;
    @javafx.fxml.FXML
    private Label lblRptSceneTitle;
    @javafx.fxml.FXML
    private Label lblRptUsername;
    @javafx.fxml.FXML
    private Label lblRptUsernameLabel;
    @javafx.fxml.FXML
    private Label lblRptZoneID;
    @javafx.fxml.FXML
    private HBox tfWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblRptNavTitle;
    @javafx.fxml.FXML
    private Button btnRptNavCustScene;
    @javafx.fxml.FXML
    private Button btnRptNavWelcHub;
    @javafx.fxml.FXML
    private Button btnRptNavReportsScene;
    @javafx.fxml.FXML
    private Button btnRptNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnRptNavLogout;
    @javafx.fxml.FXML
    private Button btnDisplayReport1;
    @javafx.fxml.FXML
    private Button btnDisplayReport2;
    @javafx.fxml.FXML
    private Button btnDisplayErrorLog;
    @javafx.fxml.FXML
    private TextArea txtAreaRptOutput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    ///// NAVIGATION METHODS /////
    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Customers");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/welcomehub-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Welcome Hub");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Scheduling");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setCurrentUser(null);
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Customers");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    ///// REPORT GENERATOR BUTTON LISTENERS /////
    @javafx.fxml.FXML
    public void displayRptApptsTypeMonth(ActionEvent actionEvent) {
        displayReport(ReportGenerator.generateApptsByTypeAndMonthReport(DataMgmt.getAllApptsList()));
    }

    @javafx.fxml.FXML
    public void displayRptApptByContact(ActionEvent actionEvent) {
        displayReport(ReportGenerator.generateScheduleByContactReport(DataMgmt.getAllApptsList()));
    }

    @javafx.fxml.FXML
    public void displayRptExceptionLog(ActionEvent actionEvent) {
        try {
            displayReport(ReportGenerator.generateApplicationExceptionReport(Logger.exceptionsFileName));
        } catch (FileNotFoundException e) {
            ExceptionEvent fileNotFound = new ExceptionEvent(DataMgmt.getCurrentUser().getName(),
                    LogEvent.EventType.EXCEPTION, LogEvent.AppLocation.REPORTING, e);
            Main.logger.log(fileNotFound);
        }
    }


    ///// TEXT AREA FUNCTIONS /////
    private void displayReport(Vector<String> reportData){
        txtAreaRptOutput.clear();;
        reportData.forEach(line -> {
            txtAreaRptOutput.appendText(line);
            txtAreaRptOutput.appendText("\n");
        });
    }

}
