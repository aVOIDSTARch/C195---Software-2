package com.casinelli.Appointments.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SchedulingHubController implements Initializable {
    @javafx.fxml.FXML
    private HBox tfWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblApptSceneAppName;
    @javafx.fxml.FXML
    private Label lblApptSceneTitle;
    @javafx.fxml.FXML
    private Label lblApptUsername;
    @javafx.fxml.FXML
    private Label lblApptUsernameLabel;
    @javafx.fxml.FXML
    private Label lblApptZoneID;
    @javafx.fxml.FXML
    private Label lblApptNavTitle;
    @javafx.fxml.FXML
    private Button btnApptNavCustScene;
    @javafx.fxml.FXML
    private Button btnApptNavWelcHub;
    @javafx.fxml.FXML
    private Button btnApptNavReportsScene;
    @javafx.fxml.FXML
    private Button btnApptNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnApptNavLogout;
    @javafx.fxml.FXML
    private TableView tblVwAppts;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_ApptID;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_ApptName;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_Description;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_Location;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_ApptType;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_Start;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_End;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_Customer;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_User;
    @javafx.fxml.FXML
    private TableColumn tvColAppt_Contact;
    @javafx.fxml.FXML
    private Button btnApptCreate;
    @javafx.fxml.FXML
    private Button btnApptUpdate;
    @javafx.fxml.FXML
    private Button btnApptDelete;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
    }

    @Deprecated
    public void displayRptApptsTypeMonth(ActionEvent actionEvent) {
    }

    @Deprecated
    public void displayRptApptByContact(ActionEvent actionEvent) {
    }

    @Deprecated
    public void displayRptExceptionLog(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void createNewAppt(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateSelectedAppt(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteSelectedAppt(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}