package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.*;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.ApplicationEvent;
import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Controller for the Reporting Hub Scene
 */
public class ReportingHubController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;

    ///// JAVAFX CONTROLS /////


    ///// Text Labels for User Interface /////
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
    private Label lblRptNavTitle;
    @javafx.fxml.FXML
    private Label lblReportApptType;
    @javafx.fxml.FXML
    private Label lblReportApptMonth;
    @javafx.fxml.FXML
    private Label lblRptSelectLog;
    ///// Navigation Buttons /////
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
    /////Report Display Buttons /////
    private Button btnDisplayReport1;
    @javafx.fxml.FXML
    private Button btnDisplayReport2;
    @javafx.fxml.FXML
    private Button btnDisplaySelectedLog;
    ///// Test Area for Report Display /////
    @javafx.fxml.FXML
    private TextArea txtAreaRptOutput;
    ///// Report 1 Input Selection ComboBoxes /////
    @javafx.fxml.FXML
    private ComboBox<String> cboReportApptType;
    @javafx.fxml.FXML
    private ComboBox<String> cboReportApptMonth;
    @javafx.fxml.FXML
    private ComboBox<String> cboRptLogSelector;


    /**
     * Initializes all User Interface components
     * @param url provided by launch method
     * @param resourceBundle provided by launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTranslatedTextLabels();
        cboReportApptType.setItems(DataMgmt.getAllApptTypes().sorted());
        cboReportApptMonth.setItems(DataMgmt.getMonthNames(DateTimeMgmt.LOCALE_SYS));
        bindReport1Btn();
        populateSelectLogCBO();
    }

    /**
     * Prevents the clicking of the Report1 Button until the user has selected the required inputs
     */
    private void bindReport1Btn() {
        BooleanBinding report1BtnDisabler = cboReportApptMonth.valueProperty().isNull()
                .or(cboReportApptType.valueProperty().isNull());
        btnDisplayReport1.disableProperty().bind(report1BtnDisabler);
    }
    /**
     * Populate the Log Selection ComboBox
     */
    private void populateSelectLogCBO() {
        ObservableList<String> logList = FXCollections.observableArrayList();
        logList.add(I18nMgmt.translate("AppLogText"));
        logList.add(I18nMgmt.translate("ExceptionLogText"));
        logList.add(I18nMgmt.translate("LoginLogText"));
        logList.add(I18nMgmt.translate("DBLogText"));
        cboRptLogSelector.setItems(logList);
    }
    /**
     * Populates translated text for user interface objects
     */
    private void populateTranslatedTextLabels() {
        /////BUTTONS/////
        btnRptNavWelcHub.textProperty().setValue(I18nMgmt.translate("NavWelcome"));
        btnRptNavCustScene.textProperty().setValue(I18nMgmt.translate("NavCustBtn"));
        btnRptNavScheduleScene.textProperty().setValue(I18nMgmt.translate("NavScheduleBtn"));
        btnRptNavReportsScene.textProperty().setValue(I18nMgmt.translate("NavReportsBtn"));
        btnRptNavLogout.textProperty().setValue(I18nMgmt.translate("NavLogOutBtn"));
        /////LABELS/////
        lblRptSceneAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblRptSceneTitle.textProperty().setValue(I18nMgmt.translate("welcomeHub"));
        lblRptNavTitle.textProperty().setValue(I18nMgmt.translate("NavTitle"));
        lblRptUsernameLabel.textProperty().setValue(I18nMgmt.translate("UsernameLabel"));
        lblRptUsername.textProperty().setValue(DataMgmt.getCurrentUser().getName());
        lblRptZoneID.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        ///// REPORT SCENE SPECIFIC TEXT /////
        lblReportApptType.setText(I18nMgmt.translate("RptApptType"));
        lblReportApptMonth.setText(I18nMgmt.translate("RptApptMonth"));
        lblRptSelectLog.setText(I18nMgmt.translate("SelectLogText"));
        btnDisplayReport1.textProperty().setValue(I18nMgmt.translate("RptApptsMonthTypeBtnText"));
        btnDisplayReport2.textProperty().setValue(I18nMgmt.translate("RptApptContactBtnText"));
        btnDisplaySelectedLog.textProperty().setValue(I18nMgmt.translate("RptLogBtnText"));
    }

    ///// NAVIGATION METHODS /////
    /**
     * Navigates to Customer Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle("Customers");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
    /**
     * Navigates to Welcome Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/welcomehub-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle("Welcome Hub");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Navigates to Scheduling Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle("Scheduling");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Logs the current user out of the system and returns the user interface to the Log In Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setCurrentUser(null);
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle("Customers");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Writes a vector of texts strings to the Text Area to display the requested report to the user
     * @param reportData Vector<String> containing report broke down by line of text to write
     */
    ///// TEXT AREA FUNCTIONS /////
    private void displayReport(Vector<String> reportData){
        txtAreaRptOutput.clear();
        reportData.forEach(line -> {
            txtAreaRptOutput.appendText(line);
            txtAreaRptOutput.appendText("\n");
        });
    }
    /**
     * Displays a report containing Appointments according to the user specific constraints
     * @param actionEvent button click event
     */
    ///// REPORT GENERATOR BUTTON LISTENERS /////
    @javafx.fxml.FXML
    public void displayRptApptsTypeMonth(ActionEvent actionEvent) {
        actionEvent.consume();
        displayReport(ReportGenerator.generateApptsByTypeAndMonthReport(DataMgmt.getAllApptsList(),
                    cboReportApptMonth.getSelectionModel().getSelectedItem(),
                    cboReportApptType.getSelectionModel().getSelectedItem()));
    }

    /**
     * Display report of Appointments by Contact
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void displayRptApptByContact(ActionEvent actionEvent) {
        actionEvent.consume();
        displayReport(ReportGenerator.generateScheduleByContactReport(DataMgmt.getAllContactsList()));
    }
    /**
     * Display the log file of the selected log
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void displayRptSelectedLog(ActionEvent actionEvent) {
        actionEvent.consume();
        String filename = getSelectedLogFileName(cboRptLogSelector.getSelectionModel().getSelectedIndex());
        if(!filename.isEmpty()){
            try {
                displayReport(ReportGenerator.generateLogReport(filename));
            } catch (FileNotFoundException e) {
                ExceptionEvent fileNotFound = new ExceptionEvent(DataMgmt.getCurrentUser().getName(),
                        LogEvent.EventType.EXCEPTION, LogEvent.AppLocation.REPORTING, e);
                Main.logger.log(fileNotFound);
            }
        }else{
            ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                    LogEvent.AppLocation.REPORTING, "Selection Not Made");
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "ReportingSceneTitle",
                    "NotSelectedHeader", "LogNotSelectedContent").showAndWait();
        }
    }

    /**
     * Returns the requested Log File Name
     * @param selectionIndex int indicating which file name to return
     * @return String file name of log file
     */
    private static String getSelectedLogFileName(int selectionIndex){
        switch (selectionIndex){
            case 0:
                return Logger.applicationFileName;
            case 1:
                return Logger.exceptionsFileName;
            case 2:
                return Logger.loginFileName;
            case 3:
                return Logger.databaseFileName;
            default:
                return "";
        }
    }

    ///// Navigation Error Handler Method /////
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.CUSTOMER_CREATE, e);
        Main.logger.log(event);
        AlertFactory.getFXMLLoadErrorAlert("CustAddSceneTitle").showAndWait();
    }
}
