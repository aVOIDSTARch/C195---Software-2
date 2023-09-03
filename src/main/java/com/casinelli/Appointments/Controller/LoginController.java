package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;
import com.casinelli.Appointments.Model.LoginEvent;
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
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Controls the Login Scene User Interface
 */
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

    /**
     * @param url Provided by the JavaFx launch() function
     * @param resourceBundle Provided by the JavaFx launch() function
     * Populates the translated text required to build the scene
     */
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

    /**
     * @param actionEvent Button clicked event
     * Verifies user login was successful and logs event to file
     * Switches scene to Welcome HUb when Successful
     */
    @FXML
    public void onLoginClick(ActionEvent actionEvent) {
        boolean successfulLogin = false;
        Value<String> username = new Value<String>(tfUsername.textProperty().getValue());
        String userPass = tfPassword.textProperty().getValue();
        successfulLogin = verifyPassword(username, userPass);
        LogEvent loginAttempt = new LoginEvent(username.getValue(), LogEvent.EventType.LOGIN_ATTEMPT,
                successfulLogin );
        Main.logger.log(loginAttempt);
        if(successfulLogin){
            try {
                setCurrentUser(username);
                initializeLandingScene(actionEvent);
            } catch (IOException e) {
                ExceptionEvent ioFailure = new ExceptionEvent(DataMgmt.getCurrentUser().getName(),
                        LogEvent.EventType.EXCEPTION, LogEvent.AppLocation.LOGIN_SCENE, e);
                Main.logger.log(ioFailure);
                AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "LoginSceneTitle", "failedLogEventHeader",
                        "failedLogEventContent" + "\n" + e.getMessage()).showAndWait();
            }
        }else{
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "LoginSceneTitle", "wrongPasswordHeader",
                    "wrongPasswordContent").showAndWait();
        }
    }

    /**
     * @param ae ActionEvent object passed from onCLickLogin method and executes teh scene switch to the Welcome Hub
     * @throws IOException Occurs when the FXML file cannot be found - nested NullPointerException
     * Changes scene to Welcome Hub
     */
    private void initializeLandingScene(ActionEvent ae) throws IOException {
        thisStage = (Stage) ((Button)ae.getSource()).getScene().getWindow();
        scene   = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("/com/casinelli/Appointments/welcomehub-view.fxml")));
        thisStage.setTitle(I18nMgmt.translate("WelcomeSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * @param username From tfUsername text field
     * @param userPass From tfPassword text field
     * @return True is the password and username match
     * Queries DB and verifies that password and username match records
     */
    private boolean verifyPassword(Value<String> username, String userPass) {
        boolean isSuccesful = false;
        String dbPassword = "";
        try{
        ResultSet rs = DBQuery.retrieve(User.getUserByName, username);
        if(rs.next()){
            dbPassword = rs.getString(User.USER_COL_NAMES[2]);
        }
        }catch(SQLException sqle){
                AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "LoginSceneTitle", "sqlErrorHeader",
                        "sqlRetrieveErrorContent").showAndWait();
                return false;
        }
        if(userPass.equals(dbPassword)){
            isSuccesful = true;
        }
        return isSuccesful;
    }

    /**
     * @param userName username from tfUsername text field
     * Updates Helper class from default user to currently verified user
     */
    private void setCurrentUser(Value<String> userName){
       DataMgmt.setCurrentUser(DataMgmt.getUserByName(userName.getValue()));
    }
}