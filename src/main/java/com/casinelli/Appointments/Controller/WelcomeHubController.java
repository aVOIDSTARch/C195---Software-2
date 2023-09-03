package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.Appointment;
import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Controller for the Welcome Hub Scene User Interface
 */
public class WelcomeHubController implements Initializable{
    /////// Controller instance variables /////
    Stage thisStage;
    Parent scene;
    ///// Labels /////
    @javafx.fxml.FXML
    private Label lblWHAppName;
    @javafx.fxml.FXML
    private Label lblWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblWHUsername;
    @javafx.fxml.FXML
    private Label lblWHZoneID;
    @javafx.fxml.FXML
    private HBox tfWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblWHNavTitle;
    @javafx.fxml.FXML
    ///// Navigation Buttons /////
    private Button btnWHNavCustScene;
    @javafx.fxml.FXML
    private Button btnWHNavWelcHub;
    @javafx.fxml.FXML
    private Button btnWHNavReportsScene;
    @javafx.fxml.FXML
    private Button btnWHNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnWHNavLogout;
    ///// Upcoming Appts TableView /////
    @FXML
    private TableView<Appointment> tblVwWHUpcomingAppts;
    @FXML
    private TableColumn colWHTVApptId;
    @FXML
    private TableColumn colWHTVApptTitle;
    @FXML
    private TableColumn colWHTVApptDesc;
    @FXML
    private TableColumn colWHTVApptLocation;
    @FXML
    private TableColumn colWHTVApptType;
    @FXML
    private TableColumn colWHTVApptStart;
    @FXML
    private TableColumn colWHTVApptEnd;
    @FXML
    private TableColumn colWHTVApptCustId;
    @FXML
    private TableColumn colWHTVApptUserId;
    @FXML
    private TableColumn colWHTVApptContactId;
    ///// Text Labels for Primary Window /////
    @FXML
    private Label lblWHTotalApptsText;
    @FXML
    private Label lblWHNumAppts;
    @FXML
    private Label lblWHContact1Name;
    @FXML
    private Label lblWHContact2Name;
    @FXML
    private Label lblWHContact3Name;
    @FXML
    private Label lblApptCountTextUser1;
    @FXML
    private Label lblApptCountTextUser2;
    @FXML
    private Label lblApptCountTextUser3;
    @FXML
    private Label lblNextApptUser1;
    @FXML
    private Label lblNextApptUser2;
    @FXML
    private Label lblNextApptUser3;
    @FXML
    private Label lblWHUsernameLabel;
    @FXML
    private Label lblApptCountNumForContact1;
    @FXML
    private Label lblApptCountNumForContact2;
    @FXML
    private Label lblApptCountNumForContact3;
    @FXML
    private Label lblWHApptCntToday;
    @FXML
    private Label lblWHApptsToday;

    ///// INITIALIZE SCENE AND SUPPORTING METHODS /////
    /**
     * Initializes Welcome Hub Scene User interface
     * @param url Provided by launch method
     * @param resourceBundle Provided by launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Setup scene content text
        populateTextWelcomeHub();
        populateContactSquare();
        //Initialize Tableview
        initializeTableView();
        //Check for Appts in Next 15 Minutes and Notify User
        displayUpcomingAppts(DataMgmt.getApptsInNext15Mins());
    }

    /**
     * Populates translated Welcome hub Scene text Labels
     */
    private void populateTextWelcomeHub(){
        /////BUTTONS/////
        btnWHNavWelcHub.textProperty().setValue(I18nMgmt.translate("NavWelcome"));
        btnWHNavCustScene.textProperty().setValue(I18nMgmt.translate("NavCustBtn"));
        btnWHNavScheduleScene.textProperty().setValue(I18nMgmt.translate("NavScheduleBtn"));
        btnWHNavReportsScene.textProperty().setValue(I18nMgmt.translate("NavReportsBtn"));
        btnWHNavLogout.textProperty().setValue(I18nMgmt.translate("NavLogOutBtn"));
        /////LABELS/////
        lblWHAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblWHSceneTitle.textProperty().setValue(I18nMgmt.translate("welcomeHub"));
        lblWHNavTitle.textProperty().setValue(I18nMgmt.translate("NavTitle"));
        lblWHUsernameLabel.textProperty().setValue(I18nMgmt.translate("UsernameLabel"));
        lblWHUsername.textProperty().setValue(DataMgmt.getCurrentUser().getName());
        lblWHZoneID.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        lblWHTotalApptsText.textProperty().setValue(I18nMgmt.translate("WHTotalNumApptsLabel"));
        String apptCountForToday = "Total   " + DataMgmt.getApptCountForToday();
        lblWHApptCntToday.textProperty().setValue(apptCountForToday);
        lblWHApptsToday.textProperty().setValue(I18nMgmt.translate("WHApptsForToday"));
        lblWHNumAppts.textProperty().setValue(Integer.toString(DataMgmt.getTotalNumAppts()));
    }

    /**
     * Populates Contact Square Information from database data
     */
    private void populateContactSquare(){
        //Set Contact Names
        lblWHContact1Name.textProperty().setValue(DataMgmt.getAllContactsList().get(0).getName());
        lblWHContact2Name.textProperty().setValue(DataMgmt.getAllContactsList().get(1).getName());
        lblWHContact3Name.textProperty().setValue(DataMgmt.getAllContactsList().get(2).getName());
        //Set Static Text by Lang
        lblApptCountTextUser1.textProperty().setValue(I18nMgmt.translate("WHUserApptCountText"));
        lblApptCountTextUser2.textProperty().setValue(I18nMgmt.translate("WHUserApptCountText"));
        lblApptCountTextUser3.textProperty().setValue(I18nMgmt.translate("WHUserApptCountText"));
        lblNextApptUser1.textProperty().setValue(I18nMgmt.translate("WHNextUserApptStarts"));
        lblNextApptUser2.textProperty().setValue(I18nMgmt.translate("WHNextUserApptStarts"));
        lblNextApptUser3.textProperty().setValue(I18nMgmt.translate("WHNextUserApptStarts"));
        //Set Variable Data by Contact
        lblApptCountNumForContact1.textProperty().setValue(Integer.toString(DataMgmt.getApptCountByContactId(1)));
        lblApptCountNumForContact2.textProperty().setValue(Integer.toString(DataMgmt.getApptCountByContactId(2)));
        lblApptCountNumForContact3.textProperty().setValue(Integer.toString(DataMgmt.getApptCountByContactId(3)));
    }

    /**
     * Initializes the Next 15 Minutes Appointments TableView
     */
    private void initializeTableView() {
        colWHTVApptId.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        colWHTVApptTitle.textProperty().setValue(I18nMgmt.translate("ColNameTitle"));
        colWHTVApptDesc.textProperty().setValue(I18nMgmt.translate("ColNameDesc"));
        colWHTVApptLocation.textProperty().setValue(I18nMgmt.translate("ColNameLocation"));
        colWHTVApptType.textProperty().setValue(I18nMgmt.translate("ColNameType"));
        colWHTVApptStart.textProperty().setValue(I18nMgmt.translate("ColNameStart"));
        colWHTVApptEnd.textProperty().setValue(I18nMgmt.translate("ColNameEnd"));
        colWHTVApptCustId.textProperty().setValue(I18nMgmt.translate("ColNameCustomer"));
        colWHTVApptUserId.textProperty().setValue(I18nMgmt.translate("ColNameUser"));
        colWHTVApptContactId.textProperty().setValue(I18nMgmt.translate("ColNameContact"));
        ///// IMPORT DATA /////
        tblVwWHUpcomingAppts.setItems(DataMgmt.getWeekApptsList());
        ///// SET UP COLUMNS FOR DATA /////
        colWHTVApptId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colWHTVApptTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colWHTVApptDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colWHTVApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colWHTVApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colWHTVApptStart.setCellValueFactory(new PropertyValueFactory<>("startString"));
        colWHTVApptEnd.setCellValueFactory(new PropertyValueFactory<>("endString"));
        colWHTVApptCustId.setCellValueFactory(new PropertyValueFactory<>("customerNameIdCombo"));
        colWHTVApptUserId.setCellValueFactory(new PropertyValueFactory<>("userNameIdCombo"));
        colWHTVApptContactId.setCellValueFactory(new PropertyValueFactory<>("contactNameIdCombo"));
    }

    /**
     * Alerts teh user of Appointments occuring in the next 15 minutes of Loggin in
     * @param apptsList List of Appointments that occur in the next 15 minutes
     */
    private void displayUpcomingAppts(ObservableList<Appointment> apptsList) {
        Vector<String> apptStrings = new Vector<>();
        StringBuilder apptsToDisplay = new StringBuilder();
        apptsList.forEach(appt -> {
            apptStrings.add(String.format("Appt ID: %d Date and Time: %s", appt.getId(), appt.getStartString()));
        });
        if(apptStrings.size() > 0){
            apptsToDisplay.append("Number of Upcoming Appointments: ").append(apptStrings.size());
            apptStrings.forEach(apptString -> {
                apptsToDisplay.append("\n").append(apptString);
            });
            Alert edgeCaseAlert1 = new Alert(Alert.AlertType.INFORMATION);
            edgeCaseAlert1.setTitle(I18nMgmt.translate("WelcomeSceneTitle"));
            edgeCaseAlert1.setHeaderText(I18nMgmt.translate("nextFifteenMinutesHeader"));
            edgeCaseAlert1.setContentText(apptsToDisplay.toString());
            edgeCaseAlert1.showAndWait();
        }else{
            AlertFactory.getNewDialogAlert(Alert.AlertType.INFORMATION, "WelcomeSceneTitle",
                    "nextFifteenMinutesHeader", "noApptsNextFifteen").showAndWait();
        }
    }

    /**
     * Navigates to Customer Scene
     * @param actionEvent Created upon button click
     * @throws IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    ///// NAVIGATION BUTTONS /////
    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent)  {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustomerSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Method does nothing at this time button disabled in user interface
     * @param actionEvent crated upon button click
     */
    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
        //Scene already displayed
    }

    /**
     * Navigates to Reports Scene
     * @param actionEvent created upon button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/reporting-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ReportingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Navigates to Scheduling Scene
     * @param actionEvent created upon button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
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
        thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Logs out current user and returns the user to the Login Scene
     * @param actionEvent created upon button click
     * @exception IOException occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setUserToDefault();
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("LoginSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.WELCOME_HUB, e);
        AlertFactory.getFXMLLoadErrorAlert("WelcomeSceneTitle").showAndWait();
    }
}
