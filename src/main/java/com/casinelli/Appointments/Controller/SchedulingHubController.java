package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.DataMgmt;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SchedulingHubController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;
    ///// JAVAFX CONTROLS /////
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
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/customer-view.fxml"));
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
            scene = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/welcomehub-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Welcome Hub");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/reporting-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Reports");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setCurrentUser(null);
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/login-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Customers");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
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