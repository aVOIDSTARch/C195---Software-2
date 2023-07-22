package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.RetrieveInterface;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.DBObject;
import com.casinelli.Appointments.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;

    @FXML
    private Button buttonLogin;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label lblZoneId;
    @FXML
    private Label lblAppTitle;
    @FXML
    private Label lblPleaseLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogin.textProperty().setValue(I18nMgmt.translate("Login"));
        buttonLogin.accessibleTextProperty().setValue(I18nMgmt.translate("loginAssessibleText"));
        tfPassword.promptTextProperty().setValue(I18nMgmt.translate("passwordPromptText"));
        tfUsername.promptTextProperty().setValue(I18nMgmt.translate("usernamePromptText"));
        lblZoneId.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        lblAppTitle.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblPleaseLogin.textProperty().setValue(I18nMgmt.translate("loginPlease"));
    }

    @FXML
    public void onLoginClick(ActionEvent actionEvent) throws SQLException {

        Value<String> username = new Value<String>(tfUsername.textProperty().getValue());
        String userPass = tfPassword.textProperty().getValue();
        String dbPassword = "";
        String colName = User.USER_COL_NAMES[2];
        ResultSet rs = DBQuery.retrieve(User.userPassword, colName, username);
        if(rs.next()){
            dbPassword = rs.getString(User.USER_COL_NAMES[2]);
            if(userPass.equals(dbPassword)){
                initializeLandingPage();
            }
        }else{
            //showAlertDialog
        }

    }
    private void initializeLandingPage(){

    }
}