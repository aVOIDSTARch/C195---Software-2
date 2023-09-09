package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.Appointment;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.casinelli.Appointments.Helper.DateTimeMgmt.*;

public class ApptModController implements Initializable {
    //Instance Variables
    Stage thisStage;
    Parent scene;
    BooleanBinding updateButtonDisabler;
    private Appointment apptToMod;
    private BooleanBinding modBtnDisabler;
    //FXML Controls
    @javafx.fxml.FXML
    private Label lblApptModApptID;
    @javafx.fxml.FXML
    private Label lblApptModTitle;
    @javafx.fxml.FXML
    private Label lblApptModDesc;
    @javafx.fxml.FXML
    private Label lblApptModLocation;
    @javafx.fxml.FXML
    private Label lblApptModType;
    @javafx.fxml.FXML
    private Label lblApptModCustID;
    @javafx.fxml.FXML
    private Label lblApptModUserID;
    @javafx.fxml.FXML
    private Label lblAppName;
    @javafx.fxml.FXML
    private Label lblApptModStart;
    @javafx.fxml.FXML
    private Label lblApptModEnd;
    @javafx.fxml.FXML
    private Label lblApptModHour;
    @javafx.fxml.FXML
    private Label lblApptModMinutes;
    @javafx.fxml.FXML
    private Label lblApptModContact;
    @javafx.fxml.FXML
    private Label lblApptModHour1;
    @javafx.fxml.FXML
    private Label lblApptModMinutes1;
    ///// Text Fields /////
    @javafx.fxml.FXML
    private TextField tfApptModApptID;
    @javafx.fxml.FXML
    private TextField tfApptModTitle;
    @javafx.fxml.FXML
    private TextField tfApptModDesc;
    @javafx.fxml.FXML
    private TextField tfApptModLocation;
    @javafx.fxml.FXML
    private TextField tfApptModType;
    ///// ComboBoxes /////
    @javafx.fxml.FXML
    private ComboBox<String> cboApptModCustID;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptModUserID;
    @javafx.fxml.FXML
    private ComboBox<Integer> hourApptModStart;
    @javafx.fxml.FXML
    private ComboBox<Integer> hourApptModEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> minApptModEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> minApptModStart;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptModContactID;
    ///// Buttons /////
    @javafx.fxml.FXML
    private Button btnApptModUpdate;
    @javafx.fxml.FXML
    private Button btnApptModCancel;
    ///// Date Pickers /////
    @javafx.fxml.FXML
    private DatePicker dateApptModStart;
    @javafx.fxml.FXML
    private DatePicker dateApptModEnd;
    @javafx.fxml.FXML
    private Label lblApptModCompleteInputs;


    ///// INITIALIZATION METHODS /////
    /**
     * Initializes Appointment Add Scene User Interface
     * @param url provided by launch method
     * @param resourceBundle provided bby launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptToMod = SchedulingHubController.getSelectedAppt();
        setUpdateButtonBindings(updateButtonDisabler);
        initializeSceneText();
        populateCurrentAppointmentData();
        populateHoursAndMinsLists();
        populateContactsList();
        populateCustomersList();
        populateUsersList();
    }

    /**
     * Creates and binds a boolean binding that prevents the user from clicking the Update button without inputting
     * all information thus avoiding all errors for input and shows a label with instructions
     * @param binding BooleanBinding for Update button disable property
     */
    private void setUpdateButtonBindings(BooleanBinding binding) {
        binding = tfApptModTitle.textProperty().isEmpty()
                .or(tfApptModDesc.textProperty().isEmpty())
                .or(tfApptModLocation.textProperty().isEmpty())
                .or(tfApptModType.textProperty().isEmpty())
                .or(dateApptModStart.valueProperty().isNull())
                .or(hourApptModStart.valueProperty().isNull())
                .or(minApptModStart.valueProperty().isNull())
                .or(dateApptModEnd.valueProperty().isNull())
                .or(hourApptModEnd.valueProperty().isNull())
                .or(minApptModEnd.valueProperty().isNull())
                .or(cboApptModContactID.valueProperty().isNull())
                .or(cboApptModCustID.valueProperty().isNull())
                .or(cboApptModUserID.valueProperty().isNull());
        btnApptModUpdate.disableProperty().bind(binding);
        lblApptModCompleteInputs.visibleProperty().bind(binding);
    }

    /**
     * Populates user interface with translated text
     */
    private void initializeSceneText() {
        //Initialize Labels
        lblAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblApptModApptID.textProperty().setValue(I18nMgmt.translate("ApptIDText"));
        lblApptModTitle.textProperty().setValue(I18nMgmt.translate("ApptTitleText"));
        lblApptModDesc.textProperty().setValue(I18nMgmt.translate("ApptDescText"));
        lblApptModLocation.textProperty().setValue(I18nMgmt.translate("ApptLocationText"));
        lblApptModType.textProperty().setValue(I18nMgmt.translate("ApptTypeText"));
        lblApptModStart.textProperty().setValue(I18nMgmt.translate("ApptStartText"));
        lblApptModEnd.textProperty().setValue(I18nMgmt.translate("ApptEndText"));
        lblApptModHour.textProperty().setValue(I18nMgmt.translate("ApptHourtext"));
        lblApptModHour1.textProperty().setValue(I18nMgmt.translate("ApptHourtext"));
        lblApptModMinutes.textProperty().setValue(I18nMgmt.translate("ApptMinText"));
        lblApptModMinutes1.textProperty().setValue(I18nMgmt.translate("ApptMinText"));
        lblApptModContact.textProperty().setValue(I18nMgmt.translate("ApptContactText"));
        lblApptModCustID.textProperty().setValue(I18nMgmt.translate("ApptCustText"));
        lblApptModUserID.textProperty().setValue(I18nMgmt.translate("ApptUserText"));
        //Button Text
        btnApptModUpdate.textProperty().setValue(I18nMgmt.translate("UpdateBtnText"));
        btnApptModCancel.textProperty().setValue(I18nMgmt.translate("CancelBtnText"));
    }

    //// POPULATE COMBO BOXES /////
    /**
     * Populates input fields and boxes with the data from the object selected previously
     */
    private void populateCurrentAppointmentData() {
        tfApptModApptID.textProperty().setValue(String.valueOf(apptToMod.getId()));
        tfApptModTitle.textProperty().setValue(apptToMod.getName());
        tfApptModDesc.textProperty().setValue(apptToMod.getDescription());
        tfApptModLocation.textProperty().setValue(apptToMod.getLocation());
        tfApptModType.textProperty().setValue(apptToMod.getType());
        dateApptModStart.setValue(apptToMod.getStart().toLocalDate());
        hourApptModStart.setValue(apptToMod.getStart().getHour());
        minApptModStart.setValue(apptToMod.getStart().getMinute());
        dateApptModEnd.setValue(apptToMod.getEnd().toLocalDate());
        hourApptModEnd.setValue(apptToMod.getEnd().getHour());
        minApptModEnd.setValue(apptToMod.getEnd().getMinute());
        cboApptModContactID.setValue(apptToMod.getContactNameIdCombo());
        cboApptModCustID.setValue(apptToMod.getCustomerNameIdCombo());
        cboApptModUserID.setValue(apptToMod.getUserNameIdCombo());
    }

    /**
     * Populates the Hours and Minutes ComboBoxes
     */
    private void populateHoursAndMinsLists() {
        ObservableList<Integer> hourList = FXCollections.observableList(DataMgmt.makeIntList_Inclusive(0,23));
        hourApptModEnd.setItems(hourList);
        hourApptModStart.setItems(hourList);
        ObservableList<Integer> minList = FXCollections.observableList(DataMgmt.makeIntList_Inclusive(0,59));
        minApptModEnd.setItems(minList);
        minApptModStart.setItems(minList);
    }

    /**
     * Populates the Customer ID ComboBox
     */
    private void populateCustomersList() {
        ObservableList<String> custList = FXCollections.observableArrayList();
        DataMgmt.getAllCustomersList().forEach(cust -> {
            String newCustString = cust.getId() + " " + cust.getName();
            custList.add(newCustString);
        });
        cboApptModCustID.setItems(custList);
    }
    /**
     * Populates the Contact ID ComboBox
     */
    private void populateContactsList() {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        DataMgmt.getAllContactsList().forEach(contact -> {
            String newContactString = contact.getId() + " " + contact.getName();
            contactList.add(newContactString);
        });
        cboApptModContactID.setItems(contactList);
    }
    /**
     * Populates the User ID ComboBox
     */
    private void populateUsersList() {
        ObservableList<String> userList = FXCollections.observableArrayList();
        DataMgmt.getAllUsersList().forEach(user -> {
            String newUserString = user.getId() + " " + user.getName();
            userList.add(newUserString);
        });
        cboApptModUserID.setItems(userList);
    }

    ///// BUTTON EVENT HANDLERS /////

    /**
     * Updates the existing object and writes it to the database
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void updateAppointment(ActionEvent actionEvent) {
        //Build Times for Appt
        LocalDateTime startTime = DateTimeMgmt.getLocalDT(dateApptModStart.getValue(),
                hourApptModStart.getSelectionModel().getSelectedItem().toString(),
                minApptModStart.getSelectionModel().getSelectedItem().toString());
        LocalDateTime endTime = DateTimeMgmt.getLocalDT(dateApptModEnd.getValue(),
                hourApptModEnd.getSelectionModel().getSelectedItem().toString(),
                minApptModEnd.getSelectionModel().getSelectedItem().toString());
        LocalDateTime thisTime = LocalDateTime.now();

        //Validate Start/End Dates and Times
        if (isBetweenBusinessHoursInEST(startTime.toLocalTime(), endTime.toLocalTime()) &&
                isInProperOrderOfTime(startTime, endTime) && isAfterNow(startTime)) {
                    //Build Appt from Inputs
                    updateApptToMod(thisTime, startTime, endTime);
            if (!hasApptOverlaps(apptToMod)) {
                //Insert Appt into DB
                try {
                    System.out.println(DBQuery.update(Appointment.updateAppointment, apptToMod));
                } catch (SQLException e) {
                    ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                            LogEvent.AppLocation.APPOINTMENT_UPDATE, e);
                    Main.logger.log(event);
                    AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"ApptModSceneTitle","sqlErrorHeader",
                            "sqlUpdateErrorContent").showAndWait();
                }
                //Update ObservableLists in DataMgmt
                DataMgmt.initializeApplicationData();
                //Return Appt Scene
                thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
        }
    }
    /**
     * Cancels Appointment Modification and returns user to Scheduling Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void cancelApptCreate(ActionEvent actionEvent) {
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
     * Modifies the existing Appointment using updated user inputs and validated times
     * @param startTime LocalDateTime the time the Appointment starts
     * @param endTime LocalDateTime the time the Appointment end
     * @param thisTime LocalDateTime the time the Appointment was created
     */
    private void updateApptToMod(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime thisTime) {
        apptToMod.setName(tfApptModTitle.textProperty().getValue());
        apptToMod.setDescription(tfApptModDesc.textProperty().getValue());
        apptToMod.setLocation(tfApptModLocation.textProperty().getValue());
        apptToMod.setType(tfApptModType.textProperty().getValue());
        apptToMod.setStart(startTime);
        apptToMod.setEnd(endTime);
        apptToMod.setContactId(cboApptModContactID.getSelectionModel().getSelectedIndex() + 1);
        apptToMod.setCustomerId(cboApptModCustID.getSelectionModel().getSelectedIndex() + 1);
        apptToMod.setUserId(cboApptModUserID.getSelectionModel().getSelectedIndex() + 1);
        apptToMod.setLastUpdate(thisTime);
        apptToMod.setLastUpdatedBy(DataMgmt.getCurrentUser().getName());

    }

    ///// Navigation Error Handler Method /////
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.APPOINTMENT_UPDATE, e);
        Main.logger.log(event);
        AlertFactory.getFXMLLoadErrorAlert("ApptModSceneTitle").showAndWait();
    }
}


