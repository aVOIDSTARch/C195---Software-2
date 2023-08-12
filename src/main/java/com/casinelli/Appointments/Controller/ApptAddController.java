package com.casinelli.Appointments.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ResourceBundle;

public class ApptAddController implements Initializable {
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
    private ComboBox cboApptAddCustID;
    @javafx.fxml.FXML
    private ComboBox cboApptAddUserID;
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
    private ComboBox hourApptAddStart;
    @javafx.fxml.FXML
    private ComboBox hourApptAddEnd;
    @javafx.fxml.FXML
    private ComboBox minApptAddEnd;
    @javafx.fxml.FXML
    private ComboBox minApptAddStart;
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
    private ComboBox cboApptAddContact;
    @javafx.fxml.FXML
    private Label lblApptAddHour1;
    @javafx.fxml.FXML
    private Label lblApptAddMinutes1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void createNewAppointment(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cancelApptCreate(ActionEvent actionEvent) {
    }
}
