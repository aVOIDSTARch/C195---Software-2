package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ApptModController implements Initializable {
    Stage thisStage;
    Parent scene;
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
    private TextField tfApptModApptID;
    @javafx.fxml.FXML
    private TextField tfApptModTitle;
    @javafx.fxml.FXML
    private TextField tfApptModDesc;
    @javafx.fxml.FXML
    private TextField tfApptModLocation;
    @javafx.fxml.FXML
    private TextField tfApptModType;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptModCustID;
    @javafx.fxml.FXML
    private ComboBox<String> cboApptModUserID;
    @javafx.fxml.FXML
    private Button btnApptModUpdate;
    @javafx.fxml.FXML
    private Label lblAppName;
    @javafx.fxml.FXML
    private DatePicker dateApptModStart;
    @javafx.fxml.FXML
    private DatePicker dateApptModEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> hourApptModStart;
    @javafx.fxml.FXML
    private ComboBox<Integer> hourApptModEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> minApptModEnd;
    @javafx.fxml.FXML
    private ComboBox<Integer> minApptModStart;
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
    private ComboBox<String> cboApptModContact;
    @javafx.fxml.FXML
    private Label lblApptModHour1;
    @javafx.fxml.FXML
    private Label lblApptModMinutes1;
    @javafx.fxml.FXML
    private Button btnApptModCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSceneText();
        populateHoursAndMinsLists();
        populateContactsList();
        populateCustomersList();
        populateUsersList();


    }



    private void populateUsersList() {
        ObservableList<String> userList = FXCollections.observableArrayList();
        DataMgmt.getAllUsersList().forEach(user -> {
            String newUserString = user.getId() + " " + user.getName();
            userList.add(newUserString);
        });
        cboApptModUserID.setItems(userList);
    }

    private void populateCustomersList() {
        ObservableList<String> custList = FXCollections.observableArrayList();
        DataMgmt.getAllCustomersList().forEach(cust -> {
            String newCustString = cust.getId() + " " + cust.getName();
            custList.add(newCustString);
        });
        cboApptModCustID.setItems(custList);
    }

    private void populateContactsList() {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        DataMgmt.getAllCustomersList().forEach(contact -> {
            String newContactString = contact.getId() + " " + contact.getName();
            contactList.add(newContactString);
        });
        cboApptModContact.setItems(contactList);
    }
    private void populateHoursAndMinsLists() {
        hourApptModEnd.setItems((ObservableList<Integer>) DataMgmt.makeIntList(0,23));
        hourApptModStart.setItems((ObservableList<Integer>) DataMgmt.makeIntList(0,23));
        minApptModEnd.setItems((ObservableList<Integer>) DataMgmt.makeIntList(0,59));
        minApptModStart.setItems((ObservableList<Integer>) DataMgmt.makeIntList(0,59));
    }

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
        //Initialize Prompt Text

        //Button Text
        btnApptModUpdate.textProperty().setValue(I18nMgmt.translate("UpdateBtnText"));
        btnApptModCancel.textProperty().setValue(I18nMgmt.translate("CancelBtnText"));
    }

    @javafx.fxml.FXML
    public void updateAppointment(ActionEvent actionEvent) {
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
}
