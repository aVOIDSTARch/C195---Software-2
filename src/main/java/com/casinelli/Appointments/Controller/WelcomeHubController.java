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
    private void populateTextWelcomeHub(){
        /////BUTTONS/////
        btnWHNavWelcHub.textProperty().setValue(I18nMgmt.translate("welcomeHub"));
        btnWHNavCustScene.textProperty().setValue(I18nMgmt.translate("WHNavCustBtn"));
        btnWHNavScheduleScene.textProperty().setValue(I18nMgmt.translate("WHNavScheduleBtn"));
        btnWHNavReportsScene.textProperty().setValue(I18nMgmt.translate("WHNavReportsBtn"));
        btnWHNavLogout.textProperty().setValue(I18nMgmt.translate("WHNavLogOutBtn"));
        /////LABELS/////
        lblWHAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblWHAppName.textProperty().setValue(I18nMgmt.translate("welcomeHub"));
        lblWHNavTitle.textProperty().setValue(I18nMgmt.translate("WHNavTitle"));
        lblWHUsernameLabel.textProperty().setValue(I18nMgmt.translate("WHUsernameLabel"));
        lblWHUsername.textProperty().setValue(DataMgmt.getCurrentUser().getName());
        lblWHZoneID.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        lblWHTotalApptsText.textProperty().setValue(I18nMgmt.translate("WHApptsForToday"));
        String apptCountForToday = "Total   " + DataMgmt.getApptCountForToday();
        lblWHApptCntToday.textProperty().setValue(apptCountForToday);
    }
    private void populateContactSquare(int contactId){
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
}
