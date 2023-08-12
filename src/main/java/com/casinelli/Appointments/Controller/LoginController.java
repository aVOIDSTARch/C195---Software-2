package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.LogEvent;
import com.casinelli.Appointments.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void onLoginClick(ActionEvent actionEvent) {
        boolean successfulLogin = false;
        Value<String> username = new Value<String>(tfUsername.textProperty().getValue());
        String userPass = tfPassword.textProperty().getValue();
        successfulLogin = verifyPassword(username, userPass);
        LogEvent loginAttempt = new LogEvent(username.getValue(), successfulLogin, LogEvent.EventType.LOGIN_ATTEMPT );
        Main.logger.log(loginAttempt);
        if(successfulLogin){
            try {
                setCurrentUser(username);
                initializeLandingScene(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
    private void initializeLandingScene(ActionEvent ae) throws IOException {
        thisStage = (Stage) ((Button)ae.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/welcomehub-view.fxml"));
        thisStage.setTitle(I18nMgmt.translate("WelcomeSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    private boolean verifyPassword(Value<String> username, String userPass) {
        boolean isSuccesful = false;
        String dbPassword = "";
        try{
        ResultSet rs = DBQuery.retrieve(User.userPassword, username);
        if(rs.next()){
            dbPassword = rs.getString(User.USER_COL_NAMES[2]);
        }
        }catch(SQLException sqle){
            //add dialog
            return isSuccesful;
        }
        if(userPass.equals(dbPassword)){
            isSuccesful = true;
        }
        return isSuccesful;
    }
    private void setCurrentUser(Value<String> userName){
        try{
            User newUser = new User( DBQuery.retrieve(User.getUserByName, userName));
            DataMgmt.setCurrentUser(newUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}