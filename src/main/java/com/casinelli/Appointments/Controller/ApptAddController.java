package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.Appointment;
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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.casinelli.Appointments.Helper.DateTimeMgmt.*;

public class ApptAddController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;
    //FXML Controls
    @javafx.fxml.FXML
    private Label lblApptAddApptID;
    @javafx.fxml.FXML
    private Label lblApptAddTitle;
    @javafx.fxml.FXML
    private Label lblApptAddDesc;
    @javafx.fxml.FXML
    private Label lblApptAddLocation;
    @javafx.fxml.FXML
    private Label lblApptAddType;
    @javafx.fxml.FXML
    private Label lblApptAddCustID;
    @javafx.fxml.FXML
    private Label lblApptAddUserID;
    @javafx.fxml.FXML
    private TextField tfApptAddApptID;
    @javafx.fxml.FXML
    private TextField tfApptAddTitle;
    @javafx.fxml.FXML
    private TextField tfApptAddDesc;
    @javafx.fxml.FXML
    private TextField tfApptAddLocation;
    @javafx.fxml.FXML
    private TextField tfApptAddType;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptAddCustID;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptAddUserID;
    @javafx.fxml.FXML
    private Button btnApptAddCreate;
    @javafx.fxml.FXML
    private Label lblAppName;
    @javafx.fxml.FXML
    private Button btnApptAddCancel;
    @javafx.fxml.FXML
    private DatePicker dateApptAddStart;
    @javafx.fxml.FXML
    private DatePicker dateApptAddEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> hourApptAddStart;
    @javafx.fxml.FXML
    private ComboBox<Integer> hourApptAddEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> minApptAddEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> minApptAddStart;
    @javafx.fxml.FXML
    private Label lblApptAddStart;
    @javafx.fxml.FXML
    private Label lblApptAddEnd;
    @javafx.fxml.FXML
    private Label lblApptAddHour;
    @javafx.fxml.FXML
    private Label lblApptAddMinutes;
    @javafx.fxml.FXML
    private Label lblApptAddContact;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptAddContactID;
    @javafx.fxml.FXML
    private Label lblApptAddHour1;
    @javafx.fxml.FXML
    private Label lblApptAddMinutes1;
    //Final Variables

    private BooleanBinding addBtnDisabler;

    ///// INITIALIZATION METHODS /////
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCreateButtonBindings();
        initializeSceneText();
        populateHoursAndMinsLists();
        populateContactsList();
        populateCustomersList();
        populateUsersList();
    }

    private void setCreateButtonBindings() {
        addBtnDisabler = tfApptAddTitle.textProperty().isEmpty()
                .or(tfApptAddDesc.textProperty().isEmpty())
                .or(tfApptAddLocation.textProperty().isEmpty())
                .or(tfApptAddType.textProperty().isEmpty())
                .or(dateApptAddStart.valueProperty().isNull())
                .or(hourApptAddStart.valueProperty().isNull())
                .or(minApptAddStart.valueProperty().isNull())
                .or(dateApptAddEnd.valueProperty().isNull())
                .or(hourApptAddEnd.valueProperty().isNull())
                .or(minApptAddEnd.valueProperty().isNull())
                .or(cboApptAddContactID.valueProperty().isNull())
                .or(cboApptAddCustID.valueProperty().isNull())
                .or(cboApptAddUserID.valueProperty().isNull());
        btnApptAddCreate.disableProperty().bind(addBtnDisabler);
    }

    private void populateHoursAndMinsLists() {
        ObservableList<Integer> hourList = FXCollections.observableList(DataMgmt.makeIntList(0,23));
        hourApptAddEnd.setItems(hourList);
        hourApptAddStart.setItems(hourList);
        ObservableList<Integer> minList = FXCollections.observableList(DataMgmt.makeIntList(0,59));
        minApptAddEnd.setItems(minList);
        minApptAddStart.setItems(minList);
    }

    private void populateUsersList() {
        ObservableList<String> userList = FXCollections.observableArrayList();
        DataMgmt.getAllUsersList().forEach(user -> {
            String newUserString = user.getId() + " " + user.getName();
            userList.add(newUserString);
        });
        cboApptAddUserID.setItems(userList);
    }

    private void populateCustomersList() {
        ObservableList<String> custList = FXCollections.observableArrayList();
        DataMgmt.getAllCustomersList().forEach(cust -> {
            String newCustString = cust.getId() + " " + cust.getName();
            custList.add(newCustString);
        });
        cboApptAddCustID.setItems(custList);
    }

    private void populateContactsList() {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        DataMgmt.getAllContactsList().forEach(contact -> {
            String newContactString = contact.getId() + " " + contact.getName();
            contactList.add(newContactString);
        });
        cboApptAddContactID.setItems(contactList);
    }

    private void initializeSceneText() {
        //Initialize Labels
        lblAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblApptAddApptID.textProperty().setValue(I18nMgmt.translate("ApptIDText"));
        lblApptAddTitle.textProperty().setValue(I18nMgmt.translate("ApptTitleText"));
        lblApptAddDesc.textProperty().setValue(I18nMgmt.translate("ApptDescText"));
        lblApptAddLocation.textProperty().setValue(I18nMgmt.translate("ApptLocationText"));
        lblApptAddType.textProperty().setValue(I18nMgmt.translate("ApptTypeText"));
        lblApptAddStart.textProperty().setValue(I18nMgmt.translate("ApptStartText"));
        lblApptAddEnd.textProperty().setValue(I18nMgmt.translate("ApptEndText"));
        lblApptAddHour.textProperty().setValue(I18nMgmt.translate("ApptHourtext"));
        lblApptAddHour1.textProperty().setValue(I18nMgmt.translate("ApptHourtext"));
        lblApptAddMinutes.textProperty().setValue(I18nMgmt.translate("ApptMinText"));
        lblApptAddMinutes1.textProperty().setValue(I18nMgmt.translate("ApptMinText"));
        lblApptAddContact.textProperty().setValue(I18nMgmt.translate("ApptContactText"));
        lblApptAddCustID.textProperty().setValue(I18nMgmt.translate("ApptCustText"));
        lblApptAddUserID.textProperty().setValue(I18nMgmt.translate("ApptUserText"));
        //Initialize Prompt Text

        //Button Text
        btnApptAddCreate.textProperty().setValue(I18nMgmt.translate("CreateBtnText"));
        btnApptAddCancel.textProperty().setValue(I18nMgmt.translate("CancelBtnText"));

    }

    ///// BUTTON EVENT HANDLERS /////
    @javafx.fxml.FXML
    public void createNewAppointment(ActionEvent actionEvent) {
        //Build Times for Appt
        LocalDateTime startTime = DateTimeMgmt.getLocalDT(dateApptAddStart.getValue(),
                hourApptAddStart.getSelectionModel().getSelectedItem().toString(),
                minApptAddStart.getSelectionModel().getSelectedItem().toString());
        LocalDateTime endTime = DateTimeMgmt.getLocalDT(dateApptAddEnd.getValue(),
                hourApptAddEnd.getSelectionModel().getSelectedItem().toString(),
                minApptAddEnd.getSelectionModel().getSelectedItem().toString());
        LocalDateTime thisTime = LocalDateTime.now();

        //Validate Start/End Dates and Times
        if( !isBetweenHours(startTime.toLocalTime(), endTime.toLocalTime())||
                !checkStartEndSequence(startTime, endTime)) {
            System.out.println("checks failed for times");
        }else{
            //Build Appt from Inputs
            Appointment newAppt = buildNewAppt(thisTime, startTime, endTime);
            //Insert Appt into DB
            try {
                System.out.println(DBQuery.create(Appointment.insertAppointment, newAppt));
            } catch (SQLException e) {
                System.out.println("Failed to write to DB");
            }
            if(checkApptOverlaps(newAppt)){
                System.out.println("appointment overlaps");
            }else {
                //Update ObservableLists in DataMgmt
                DataMgmt.initializeApplicationData();
                //Return Appt Scene
                thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                try {
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
                thisStage.setScene(new Scene(scene));
                thisStage.show();
            }

        }

    }



    @javafx.fxml.FXML
    public void cancelApptCreate(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    ///// SUPPORT METHODS /////
    private Appointment buildNewAppt(LocalDateTime thisTime, LocalDateTime startTime, LocalDateTime endTime) {
        //Build and return Apptointment
        return new Appointment(77, tfApptAddTitle.textProperty().getValue(), thisTime, DataMgmt.getCurrentUser().getName(),
                thisTime,DataMgmt.getCurrentUser().getName(),tfApptAddDesc.textProperty().getValue(),
                tfApptAddLocation.textProperty().getValue(), tfApptAddType.textProperty().getValue(),
                startTime,endTime, cboApptAddCustID.getSelectionModel().getSelectedIndex() + 1,
                cboApptAddUserID.getSelectionModel().getSelectedIndex() + 1,
                cboApptAddContactID.getSelectionModel().getSelectedIndex() + 1);
    }







}
