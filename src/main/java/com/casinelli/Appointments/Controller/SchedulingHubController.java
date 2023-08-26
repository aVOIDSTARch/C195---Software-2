package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulingHubController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;

    ///// APPOINTMENT VARIABLES /////
    private static Appointment newAppt;
    private static Appointment selectedAppt;

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
    private TableView<Appointment> tblVwAppts;
    @javafx.fxml.FXML
    private TableColumn<Appointment,Integer> tvColAppt_ApptID;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_ApptName;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_Description;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_Location;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_ApptType;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_Start;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_End;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_Customer;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_User;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColAppt_Contact;
    @javafx.fxml.FXML
    private Button btnApptCreate;
    @javafx.fxml.FXML
    private Button btnApptUpdate;
    @javafx.fxml.FXML
    private Button btnApptDelete;

    //Appointment Getter-Setters
    public static void setNewAppt(Appointment thisAppt){
        newAppt = thisAppt;
    }
    private void setSelectedAppt(){
        if(tblVwAppts.getSelectionModel().getSelectedItem() != null){
            selectedAppt = tblVwAppts.getSelectionModel().getSelectedItem();
        }else{
            System.out.println("Failure to select appt");
        }
    }
    public static Appointment getSelectedAppt(){
        return selectedAppt;
    }

    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustomerSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/welcomehub-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("WelcomeSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/reporting-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ReportingSceneTitle"));
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
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("LoginSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void createNewAppt(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/appointment-add-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ApptAddSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void updateSelectedAppt(ActionEvent actionEvent) {
        //Set Selected Appt
        setSelectedAppt();
        //Launch Appt Mod Scene
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/appointment-mod-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ApptModSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void deleteSelectedAppt(ActionEvent actionEvent) {
        //Set Selected Appt
        setSelectedAppt();
        if(selectedAppt != null){
            Value<Integer> apptID = new Value<>(selectedAppt.getId());
            try{
                //Delete selected appt
                DBQuery.delete(Appointment.deleteApptByID, apptID);
                //Update ObservableLists in DataMgmt
                DataMgmt.initializeApplicationData();
            } catch (SQLException e) {
                System.out.println("Failed to delete from DB");
            }
        }else{
            System.out.println("Please select an appt");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTextPropsApptScene();
        initializeApptsTableView();
    }
    private void initializeApptsTableView(){
        ///// NAVIGATION BUTTONS /////
        btnApptNavWelcHub.textProperty().setValue(I18nMgmt.translate("welcomeHub"));
        btnApptNavCustScene.textProperty().setValue(I18nMgmt.translate("NavCustBtn"));
        btnApptNavScheduleScene.textProperty().setValue(I18nMgmt.translate("NavScheduleBtn"));
        btnApptNavReportsScene.textProperty().setValue(I18nMgmt.translate("NavReportsBtn"));
        btnApptNavLogout.textProperty().setValue(I18nMgmt.translate("NavLogOutBtn"));
        ///// CUSTOMER SCENE SPECIFIC BUTTONS /////
        btnApptCreate.textProperty().setValue(I18nMgmt.translate("CreateButton"));
        btnApptUpdate.textProperty().setValue(I18nMgmt.translate("UpdateButton"));
        btnApptDelete.textProperty().setValue(I18nMgmt.translate("DeleteButton"));
        ///// LABELS /////
        lblApptSceneAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblApptSceneTitle.textProperty().setValue(I18nMgmt.translate("SchedulingSceneTitle"));
        lblApptNavTitle.textProperty().setValue(I18nMgmt.translate("NavTitle"));
        lblApptUsernameLabel.textProperty().setValue(I18nMgmt.translate("UsernameLabel"));
        lblApptZoneID.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        lblApptUsername.textProperty().setValue(DataMgmt.getCurrentUser().getName());
    }

    private void populateTextPropsApptScene() {
        ///// POPULATE COLUMN NAMES /////
        tvColAppt_ApptID.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        tvColAppt_ApptName.textProperty().setValue(I18nMgmt.translate("ColNameTitle"));
        tvColAppt_Description.textProperty().setValue(I18nMgmt.translate("ColNameDesc"));
        tvColAppt_Location.textProperty().setValue(I18nMgmt.translate("ColNameLocation"));
        tvColAppt_ApptType.textProperty().setValue(I18nMgmt.translate("ColNameType"));
        tvColAppt_Start.textProperty().setValue(I18nMgmt.translate("ColNameStart"));
        tvColAppt_End.textProperty().setValue(I18nMgmt.translate("ColNameEnd"));
        tvColAppt_Customer.textProperty().setValue(I18nMgmt.translate("ColNameCustomer"));
        tvColAppt_User.textProperty().setValue(I18nMgmt.translate("ColNameUser"));
        tvColAppt_Contact.textProperty().setValue(I18nMgmt.translate("ColNameContact"));
        ///// IMPORT DATA /////
        tblVwAppts.setItems(DataMgmt.getAllApptsList());
        ///// SET UP COLUMNS FOR DATA /////
        tvColAppt_ApptID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvColAppt_ApptName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvColAppt_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvColAppt_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        tvColAppt_ApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tvColAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startString"));
        tvColAppt_End.setCellValueFactory(new PropertyValueFactory<>("endString"));
        tvColAppt_Customer.setCellValueFactory(new PropertyValueFactory<>("customerNameIdCombo"));
        tvColAppt_User.setCellValueFactory(new PropertyValueFactory<>("userNameIdCombo"));
        tvColAppt_Contact.setCellValueFactory(new PropertyValueFactory<>("contactNameIdCombo"));

    }
}