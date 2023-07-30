package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.LogEvent;
import com.casinelli.Appointments.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WelcomeHubController implements Initializable{
    //Controller instance variables
    Stage thisStage;
    Parent scene;

    @javafx.fxml.FXML
    private Label lblWHAppName;
    @javafx.fxml.FXML
    private Label lblWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblWHUsername;
    @javafx.fxml.FXML
    private Label tfWHUsernameLabel;
    @javafx.fxml.FXML
    private Label lblWHZoneID;
    @javafx.fxml.FXML
    private HBox tfWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblWHNavTitle;
    @javafx.fxml.FXML
    private Button btnWHNavCustScene;
    @javafx.fxml.FXML
    private Button btnWHNavWelcHub;
    @javafx.fxml.FXML
    private Button btnWHNavReportsScene;
    @javafx.fxml.FXML
    private Button btnWHNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnWHNavLogout;
    @FXML
    private TableView tblVwWHUpcomingAppts;
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
    private Label lblApptCountTextUser1lblApptCountNumUser1;
    @FXML
    private Label lblApptCountTextUser2;
    @FXML
    private Label lblApptCountTextUser1lblApptCountNumUser2;
    @FXML
    private Label lblApptCountTextUser3;
    @FXML
    private Label lblApptCountTextUser1lblApptCountNumUser3;
    @FXML
    private Label lblNextApptUser1;
    @FXML
    private Label lblNextApptUser2;
    @FXML
    private Label lblNextApptUser3;

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
        DataMgmt.setCurrentUser(null);
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/login-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
