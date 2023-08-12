package com.casinelli.Appointments.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.*;

public class ApptModController implements Initializable {
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
    private ComboBox cboApptModCustID;
    @javafx.fxml.FXML
    private ComboBox cboApptModUserID;
    @javafx.fxml.FXML
    private Button btnApptModUpdate;
    @javafx.fxml.FXML
    private Label lblAppName;
    @javafx.fxml.FXML
    private Button btnApptAddCancel;
    @javafx.fxml.FXML
    private DatePicker dateApptModStart;
    @javafx.fxml.FXML
    private DatePicker dateApptModEnd;
    @javafx.fxml.FXML
    private ComboBox hourApptModStart;
    @javafx.fxml.FXML
    private ComboBox hourApptModEnd;
    @javafx.fxml.FXML
    private ComboBox minApptModEnd;
    @javafx.fxml.FXML
    private ComboBox minApptModStart;
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
    private ComboBox cboApptModContact;
    @javafx.fxml.FXML
    private Label lblApptModHour1;
    @javafx.fxml.FXML
    private Label lblApptModMinutes1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void updateAppointment(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cancelApptCreate(ActionEvent actionEvent) {
    }
}
