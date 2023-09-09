package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Scheduling Scene
 */
public class SchedulingHubController implements Initializable {
    ///// Controller instance variables/////
    Stage thisStage;
    Parent scene;

    ///// SELECTED APPOINTMENT VARIABLE /////
    private static Appointment selectedAppt;

    ///// JAVAFX CONTROLS /////

    ///// Labels /////
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
    ///// Navigation Buttons /////
    private Button btnApptNavCustScene;
    @javafx.fxml.FXML
    private Button btnApptNavWelcHub;
    @javafx.fxml.FXML
    private Button btnApptNavReportsScene;
    @javafx.fxml.FXML
    private Button btnApptNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnApptNavLogout;
    ///// Appointment Effect Buttons /////
    @javafx.fxml.FXML
    private Button btnApptCreate;
    @javafx.fxml.FXML
    private Button btnApptUpdate;
    @javafx.fxml.FXML
    private Button btnApptDelete;

    ///// Tab Pane With TableViews /////
    @javafx.fxml.FXML
    private TabPane tabPaneApptBundle;
    // All Appointments TablebView
    @javafx.fxml.FXML
    private Tab tabAllAppts;
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

    //This Week's Appointemnts TableView
    @javafx.fxml.FXML
    private Tab tabThisWeeksAppts;
    @javafx.fxml.FXML
    private TableView<Appointment> tblVwThisWeekAppts;
    @javafx.fxml.FXML
    private TableColumn<Appointment,Integer> tvColWeekAppt_ApptID;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_ApptName;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_Description;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_Location;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_ApptType;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_Start;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_End;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_Customer;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_User;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColWeekAppt_Contact;

    //This Month's Appointments TableView
    @javafx.fxml.FXML
    private Tab tabThisMonthsAppts;
    @javafx.fxml.FXML
    private TableView<Appointment> tblVwThisMonthsAppts;
    @javafx.fxml.FXML
    private TableColumn<Appointment,Integer> tvColMonthAppt_ApptID;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_ApptName;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_Description;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_Location;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_ApptType;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_Start;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_End;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_Customer;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_User;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColMonthAppt_Contact;

    //This Customer's Appointments TableView
    @javafx.fxml.FXML
    private Tab tabThisCustsAppts1;
    @javafx.fxml.FXML
    private TableView<Appointment> tblVwThisCustAppts;
    @javafx.fxml.FXML
    private TableColumn<Appointment,Integer> tvColCustAppt_ApptID;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_ApptName;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_Description;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_Location;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_ApptType;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_Start;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_End;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_Customer;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_User;
    @javafx.fxml.FXML
    private TableColumn<Appointment,String> tvColCustAppt_Contact;

    ///// Initialization Methods and Helpers /////
    /**
     * Initializes the Scheduling Scene User Interface
     * @param url provided by launch method
     * @param resourceBundle provided by launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTextPropsApptScene();
        initializeApptsTableView();
        initializeThisWeekTableView();
        initializeThisMonthTableView();
        initializeThisCustTableView();
    }

    /**
     * Populates all translated text labels in Scheduling scene
     */
    private void populateTextPropsApptScene() {
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

    /**
     * Initializes All Appts TableView with Translated labels and data
     */
    private void initializeApptsTableView(){
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
    /**
     * Initializes All Selected Customer TableView with Translated labels and data
     */
    private void initializeThisCustTableView() {
        ///// POPULATE COLUMN NAMES /////
        tvColCustAppt_ApptID.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        tvColCustAppt_ApptName.textProperty().setValue(I18nMgmt.translate("ColNameTitle"));
        tvColCustAppt_Description.textProperty().setValue(I18nMgmt.translate("ColNameDesc"));
        tvColCustAppt_Location.textProperty().setValue(I18nMgmt.translate("ColNameLocation"));
        tvColCustAppt_ApptType.textProperty().setValue(I18nMgmt.translate("ColNameType"));
        tvColCustAppt_Start.textProperty().setValue(I18nMgmt.translate("ColNameStart"));
        tvColCustAppt_End.textProperty().setValue(I18nMgmt.translate("ColNameEnd"));
        tvColCustAppt_Customer.textProperty().setValue(I18nMgmt.translate("ColNameCustomer"));
        tvColCustAppt_User.textProperty().setValue(I18nMgmt.translate("ColNameUser"));
        tvColCustAppt_Contact.textProperty().setValue(I18nMgmt.translate("ColNameContact"));
        ///// IMPORT DATA /////
        tblVwThisCustAppts.setItems(DataMgmt.getThisCustsAppts());
        ///// SET UP COLUMNS FOR DATA /////
        tvColCustAppt_ApptID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvColCustAppt_ApptName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvColCustAppt_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvColCustAppt_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        tvColCustAppt_ApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tvColCustAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startString"));
        tvColCustAppt_End.setCellValueFactory(new PropertyValueFactory<>("endString"));
        tvColCustAppt_Customer.setCellValueFactory(new PropertyValueFactory<>("customerNameIdCombo"));
        tvColCustAppt_User.setCellValueFactory(new PropertyValueFactory<>("userNameIdCombo"));
        tvColCustAppt_Contact.setCellValueFactory(new PropertyValueFactory<>("contactNameIdCombo"));
    }
    /**
     * Initializes All This Month's Appointments TableView with Translated labels and data
     */
    private void initializeThisMonthTableView() {
        ///// POPULATE COLUMN NAMES /////
        tvColMonthAppt_ApptID.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        tvColMonthAppt_ApptName.textProperty().setValue(I18nMgmt.translate("ColNameTitle"));
        tvColMonthAppt_Description.textProperty().setValue(I18nMgmt.translate("ColNameDesc"));
        tvColMonthAppt_Location.textProperty().setValue(I18nMgmt.translate("ColNameLocation"));
        tvColMonthAppt_ApptType.textProperty().setValue(I18nMgmt.translate("ColNameType"));
        tvColMonthAppt_Start.textProperty().setValue(I18nMgmt.translate("ColNameStart"));
        tvColMonthAppt_End.textProperty().setValue(I18nMgmt.translate("ColNameEnd"));
        tvColMonthAppt_Customer.textProperty().setValue(I18nMgmt.translate("ColNameCustomer"));
        tvColMonthAppt_User.textProperty().setValue(I18nMgmt.translate("ColNameUser"));
        tvColMonthAppt_Contact.textProperty().setValue(I18nMgmt.translate("ColNameContact"));
        ///// IMPORT DATA /////
        tblVwThisMonthsAppts.setItems(DataMgmt.getMonthApptsList());
        ///// SET UP COLUMNS FOR DATA /////
        tvColMonthAppt_ApptID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvColMonthAppt_ApptName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvColMonthAppt_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvColMonthAppt_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        tvColMonthAppt_ApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tvColMonthAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startString"));
        tvColMonthAppt_End.setCellValueFactory(new PropertyValueFactory<>("endString"));
        tvColMonthAppt_Customer.setCellValueFactory(new PropertyValueFactory<>("customerNameIdCombo"));
        tvColMonthAppt_User.setCellValueFactory(new PropertyValueFactory<>("userNameIdCombo"));
        tvColMonthAppt_Contact.setCellValueFactory(new PropertyValueFactory<>("contactNameIdCombo"));
    }
    /**
     * Initializes All This Week's Appointments TableView with Translated labels and data
     */
    private void initializeThisWeekTableView() {
        ///// POPULATE COLUMN NAMES /////
        tvColWeekAppt_ApptID.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        tvColWeekAppt_ApptName.textProperty().setValue(I18nMgmt.translate("ColNameTitle"));
        tvColWeekAppt_Description.textProperty().setValue(I18nMgmt.translate("ColNameDesc"));
        tvColWeekAppt_Location.textProperty().setValue(I18nMgmt.translate("ColNameLocation"));
        tvColWeekAppt_ApptType.textProperty().setValue(I18nMgmt.translate("ColNameType"));
        tvColWeekAppt_Start.textProperty().setValue(I18nMgmt.translate("ColNameStart"));
        tvColWeekAppt_End.textProperty().setValue(I18nMgmt.translate("ColNameEnd"));
        tvColWeekAppt_Customer.textProperty().setValue(I18nMgmt.translate("ColNameCustomer"));
        tvColWeekAppt_User.textProperty().setValue(I18nMgmt.translate("ColNameUser"));
        tvColWeekAppt_Contact.textProperty().setValue(I18nMgmt.translate("ColNameContact"));
        ///// IMPORT DATA /////
        tblVwThisWeekAppts.setItems(DataMgmt.getWeekApptsList());
        ///// SET UP COLUMNS FOR DATA /////
        tvColWeekAppt_ApptID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvColWeekAppt_ApptName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvColWeekAppt_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvColWeekAppt_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        tvColWeekAppt_ApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tvColWeekAppt_Start.setCellValueFactory(new PropertyValueFactory<>("startString"));
        tvColWeekAppt_End.setCellValueFactory(new PropertyValueFactory<>("endString"));
        tvColWeekAppt_Customer.setCellValueFactory(new PropertyValueFactory<>("customerNameIdCombo"));
        tvColWeekAppt_User.setCellValueFactory(new PropertyValueFactory<>("userNameIdCombo"));
        tvColWeekAppt_Contact.setCellValueFactory(new PropertyValueFactory<>("contactNameIdCombo"));
    }

    ///// Appointment Getter-Setters /////
    /**
     * Gets the tabaleview that the user ahs made the selection in
     * @param visibleTab current tab displayed in tabPane
     * @return TableView with currently selected item
     */
    private TableView<Appointment> getTableViewSelected(Tab visibleTab){
      AnchorPane pane =  (AnchorPane)  visibleTab.getContent();
      TableView<Appointment> tv = (TableView<Appointment>) pane.getChildren().get(0);
        System.out.println(tv.getSelectionModel().getSelectedItem().getName());
        return tv;
    }

    /**
     * Sets the currently selected Appointment object if not null
     * @param tv TableView that contains selected Appointment
     * @return boolean true if not null
     */
    private boolean setSelectedAppt(TableView<Appointment> tv){
        if(tv.getSelectionModel().getSelectedItem() != null){
            selectedAppt = tv.getSelectionModel().getSelectedItem();
            return true;
        }else{
            ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                    LogEvent.AppLocation.SCHEDULING, "No Appointment Selected.");
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "SchedulingSceneTitle", "nothingSelectedErrorHeader",
                    "nothingSelectedErrorContent").showAndWait();
            return false;
        }
    }

    /**
     * Gets currently selected Appointment object
     * @return Appointment that is currently selected in TableView
     */
    public static Appointment getSelectedAppt(){
        return selectedAppt;
    }

    ///// Navigation Methods /////
    /**
     * Navigates to Customer Scene
     * @param actionEvent button click event
     */

    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustomerSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Navigates to Welcome Hub Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/welcomehub-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("WelcomeSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Navigates to Reports Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/reporting-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ReportingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Logs out current user and return to the Login Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setCurrentUser(null);
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("LoginSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    ///// Button Event Methods /////
    /**
     * Navigates to Create Appointment Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void createNewAppt(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/appointment-add-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ApptAddSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Launches Modify Appointment Scene and sets currently selected appointment
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void updateSelectedAppt(ActionEvent actionEvent) {
        //Set Selected Appt
        if(setSelectedAppt(getTableViewSelected(tabPaneApptBundle.getSelectionModel().getSelectedItem()))){
            //Launch Appt Mod Scene
            thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                        .getResource("/com/casinelli/Appointments/appointment-mod-view.fxml")));
            } catch (IOException e) {
                showAndLogNavErrorAlert(e);
            }
            thisStage.setTitle(I18nMgmt.translate("ApptModSceneTitle"));
            thisStage.setScene(new Scene(scene));
            thisStage.show();
        }
    }

    /**
     * Sets currently selected Appointment and deletes Appointment from Database
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void deleteSelectedAppt(ActionEvent actionEvent) {
        actionEvent.consume();
        //Set Selected Appt
        if(setSelectedAppt(getTableViewSelected(tabPaneApptBundle.getSelectionModel().getSelectedItem()))){
            //Confirm not null
            if(selectedAppt != null){
                //Present Alert to confirm deletion
                Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                newAlert.setTitle(I18nMgmt.translate("ConfirmText"));
                newAlert.setHeaderText(I18nMgmt.translate("ApptDeleteConfirmHeader"));
                newAlert.setContentText(I18nMgmt.translate("ApptDeleteConfirmContent") + "\n Appointment of Type: " +
                        selectedAppt.getType());
                Optional<ButtonType> result = newAlert.showAndWait();
                //Get results of Alert Window
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Value<Integer> apptID = new Value<>(selectedAppt.getId());
                    try{
                        //Delete selected appt
                        DBQuery.delete(Appointment.deleteApptByID, apptID);
                        //Update ObservableLists in DataMgmt
                        DataMgmt.initializeApplicationData();
                        ApplicationEvent eventSuccess = new ApplicationEvent(DataMgmt.getCurrentUser().getName(),
                                LogEvent.EventType.APPLICATION, LogEvent.AppLocation.SCHEDULING, "Appointment Deleted");
                        Main.logger.log(eventSuccess);
                        AlertFactory.getNewDialogAlert(Alert.AlertType.INFORMATION,"SchedulingSceneTitle",
                                "ApptDeleteSuccessHeader", "ApptDeleteSuccessContent");
                    } catch (SQLException e) {
                        ApplicationEvent eventFailure = new ApplicationEvent(DataMgmt.getCurrentUser().getName(),
                                LogEvent.EventType.DB_ACCESS, LogEvent.AppLocation.SCHEDULING, "Appointment Deletion Failure");
                        Main.logger.log(eventFailure);
                        AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"SchedulingSceneTitle", "sqlErrorHeader",
                                "sqlDeleteErrorContent");
                    }
                }
            }
        }
    }
    ///// Edge Case Interface Update Methods /////
    /**
     * Set the Current Customer Appointments Tab text to the Customer's name
     * @param event on selection changed event
     */
    @javafx.fxml.FXML
    public void changeTabText(Event event) {
        event.consume();
        //Display Tab
        tabPaneApptBundle.getSelectionModel().select(tabThisCustsAppts1);
        //Set name to Tab
        if(tblVwThisCustAppts.getItems().size() > 0){
            tabPaneApptBundle.getSelectionModel().getSelectedItem().textProperty()
                    .setValue(DataMgmt.getCustomerById(tblVwThisCustAppts.getItems().get(0).getCustomerId()).getName());
        }
    }
    ///// Navigation Error Alert Handler /////
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.SCHEDULING, e);
        Main.logger.log(event);
        AlertFactory.getFXMLLoadErrorAlert("SchedulingSceneTitle").showAndWait();
    }
}