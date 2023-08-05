package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.DataMgmt;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportingHubController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;
    ///// JAVAFX CONTROLS /////
    @javafx.fxml.FXML
    private Label lblRptSceneAppName;
    @javafx.fxml.FXML
    private Label lblRptSceneTitle;
    @javafx.fxml.FXML
    private Label lblRptUsername;
    @javafx.fxml.FXML
    private Label lblRptUsernameLabel;
    @javafx.fxml.FXML
    private Label lblRptZoneID;
    @javafx.fxml.FXML
    private HBox tfWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblRptNavTitle;
    @javafx.fxml.FXML
    private Button btnRptNavCustScene;
    @javafx.fxml.FXML
    private Button btnRptNavWelcHub;
    @javafx.fxml.FXML
    private Button btnRptNavReportsScene;
    @javafx.fxml.FXML
    private Button btnRptNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnRptNavLogout;
    @javafx.fxml.FXML
    private Button btnDisplayReport1;
    @javafx.fxml.FXML
    private Button btnDisplayReport2;
    @javafx.fxml.FXML
    private Button btnDisplayErrorLog;
    @javafx.fxml.FXML
    private TextArea txtAreaRptOutput;

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
    }

    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/scheduling-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle("Scheduling");
        thisStage.setScene(new Scene(scene));
        thisStage.show();
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
    public void displayRptApptsTypeMonth(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void displayRptApptByContact(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void displayRptExceptionLog(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
