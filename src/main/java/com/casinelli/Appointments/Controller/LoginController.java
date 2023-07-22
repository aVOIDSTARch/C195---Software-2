package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
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
    }

}