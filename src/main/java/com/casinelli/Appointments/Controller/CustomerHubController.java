package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.ApplicationEvent;
import com.casinelli.Appointments.Model.Customer;
import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerHubController implements Initializable {
    ///// Controller instance variables /////
    Stage thisStage;
    Parent scene;
    private static Customer selectedCustomer;

    ///// JAVAFX CONTROLS /////
    @javafx.fxml.FXML
    private Label lblCustSceneAppName;
    @javafx.fxml.FXML
    private Label lblCustSceneTitle;
    @javafx.fxml.FXML
    private Label lblCustUsername;
    @javafx.fxml.FXML
    private Label lblCustUsernameLabel;
    @javafx.fxml.FXML
    private Label lblCustZoneID;
    @javafx.fxml.FXML
    private HBox tfWHSceneTitle;
    @javafx.fxml.FXML
    private Label lblCustNavTitle;
    @javafx.fxml.FXML
    private Label lblAttnCustDeletion;

    ///// Navigation Buttons /////
    @javafx.fxml.FXML
    private Button btnCustNavCustScene;
    @javafx.fxml.FXML
    private Button btnCustNavWelcHub;
    @javafx.fxml.FXML
    private Button btnCustNavReportsScene;
    @javafx.fxml.FXML
    private Button btnCustNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnCustNavLogout;

    ///// Customer TableView /////
    @javafx.fxml.FXML
    private TableView<Customer> tblVwCustomers;
    @javafx.fxml.FXML
    private TableColumn<Customer, Integer> tvColCust_CustID;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> tvColCust_CustName;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> tvColCust_CustAddress;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> tvColCust_CustPostalCode;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> tvColCust_CustPhone;
    @javafx.fxml.FXML
    private TableColumn<Customer, Integer> tvColCust_CustDiv;
    @javafx.fxml.FXML
    private TableColumn<Customer, String> tvColCust_CustCountry;

    ///// Customer Effect Buttons /////
    @javafx.fxml.FXML
    private Button btnCustCreate;
    @javafx.fxml.FXML
    private Button btnCustUpdate;
    @javafx.fxml.FXML
    private Button btnCustDelete;
    @javafx.fxml.FXML
    private Button btnCustDisplayCustAppts;

    ///// Initialize Scene Methods /////
    /**
     * Initialize the user interface
     * @param url Provided by launch method
     * @param resourceBundle Provided by launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fill in text from I18n resource
        populateTextPropsCustScene();
        //Set up Customer TableView
        initializeCustTblView();
    }
    /**
     * Populates all text properties in the main window
     */
    private void populateTextPropsCustScene() {
        /////NAVIGATION BUTTONS/////
        btnCustNavWelcHub.textProperty().setValue(I18nMgmt.translate("welcomeHub"));
        btnCustNavCustScene.textProperty().setValue(I18nMgmt.translate("NavCustBtn"));
        btnCustNavScheduleScene.textProperty().setValue(I18nMgmt.translate("NavScheduleBtn"));
        btnCustNavReportsScene.textProperty().setValue(I18nMgmt.translate("NavReportsBtn"));
        btnCustNavLogout.textProperty().setValue(I18nMgmt.translate("NavLogOutBtn"));

        /////CUSTOMER SCENE SPECIFIC BUTTONS/////
        btnCustCreate.textProperty().setValue(I18nMgmt.translate("CreateButton"));
        btnCustUpdate.textProperty().setValue(I18nMgmt.translate("UpdateButton"));
        btnCustDelete.textProperty().setValue(I18nMgmt.translate("DeleteButton"));
        btnCustDisplayCustAppts.textProperty().setValue(I18nMgmt.translate("DisplayCustAppts"));

        /////LABELS/////
        lblCustSceneAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblCustSceneTitle.textProperty().setValue(I18nMgmt.translate("custSceneTitle"));
        lblCustNavTitle.textProperty().setValue(I18nMgmt.translate("NavTitle"));
        lblCustUsernameLabel.textProperty().setValue(I18nMgmt.translate("UsernameLabel"));
        lblCustZoneID.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        lblCustUsername.textProperty().setValue(DataMgmt.getCurrentUser().getName());
        lblAttnCustDeletion.textProperty().setValue(I18nMgmt.translate("AttentionForDeletion"));
    }
    /**
     * Initializes Customer tableview with translated text and data
     */
    private void initializeCustTblView() {
        ///// POPULATE COLUMN NAMES /////
        tvColCust_CustID.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        tvColCust_CustAddress.textProperty().setValue(I18nMgmt.translate("ColNameCustName"));
        tvColCust_CustAddress.textProperty().setValue(I18nMgmt.translate("ColNameAddress"));
        tvColCust_CustPostalCode.textProperty().setValue(I18nMgmt.translate("ColNamePostalCode"));
        tvColCust_CustPhone.textProperty().setValue(I18nMgmt.translate("ColNamePhone"));
        tvColCust_CustDiv.textProperty().setValue(I18nMgmt.translate("ColNameDivision"));
        tvColCust_CustCountry.textProperty().setValue(I18nMgmt.translate("ColNameCountry"));
        ///// IMPORT DATA /////
        tblVwCustomers.setItems(DataMgmt.getAllCustomersList());
        ///// SET UP COLUMNS FOR DATA /////
        tvColCust_CustID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvColCust_CustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvColCust_CustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tvColCust_CustPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tvColCust_CustPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tvColCust_CustDiv.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        tvColCust_CustCountry.setCellValueFactory(new PropertyValueFactory<>("countryName"));
    }
    ///// Navigation Button Click Handlers /////
    /**
     * Navigates to the Customer Scene but the scene is already displayed
     * @param actionEvent Created during the button click
     */
    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
        //Already displaying
    }
    /**
     * Navigates to the Welcome Hub Scene
     * @param actionEvent Created during the button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
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
     * Navigates to the Reports Scene
     * @param actionEvent Created during the button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void navToReportsScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/reporting-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ReportingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
    /**
     * Navigates to the Scheduling Scene
     * @param actionEvent Created during the button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
    /**
     * Logs teh current user out and returns the user to teh login scene
     * @param actionEvent Created during the button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setUserToDefault();
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("LoginSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
    /**
     * Getter for the Customer object selected in the tableview
     * @return Customer - Current Selected Customer
     */
    ///// Customer Scene Getters-Setters /////
    public static Customer getSelectedCustomer(){
        return selectedCustomer;
    }
    /**
     * Sets selected customer and alerts user to error of no Customer selected
     * @return boolean - true if the selected customer is not null
     */
    private boolean setSelectedCustomer(){
        if (tblVwCustomers.getSelectionModel().getSelectedItem() != null){
            selectedCustomer = tblVwCustomers.getSelectionModel().getSelectedItem();
            return true;
        }else{
            ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                    LogEvent.AppLocation.CUSTOMERS, "No Customer Selected.");
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "CustomerSceneTitle", "nothingSelectedErrorHeader",
                    "nothingSelectedErrorContent").showAndWait();
            return false;
        }
    }
    ///// Customer Object Manipulation Button Event Handlers /////
    /**
     * Launches the Customer Add Scene
     * @param actionEvent Created upon button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void createNewCustomer(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-add-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustAddSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
    /**
     * Sets selected Customer and Launches the Customer Modify Scene
     * @param actionEvent Created upon button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void updateSelectedCustomer(ActionEvent actionEvent) {
        if(setSelectedCustomer()){
            thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                        .getResource("/com/casinelli/Appointments/customer-mod-view.fxml")));
            } catch (IOException e) {
                showAndLogNavErrorAlert(e);
            }
            thisStage.setTitle(I18nMgmt.translate("CustModSceneTitle"));
            thisStage.setScene(new Scene(scene));
            thisStage.show();
        }
    }
    /**
     * Sets and Deletes selected Customer from Database
     * @param actionEvent Created upon button click
     * @exception SQLException Error occurs when SQL command fails
     */
    @javafx.fxml.FXML
    public void deleteSelectedCustomer(ActionEvent actionEvent) {
        if(setSelectedCustomer()){
            //Verify Zero Appts Scheduled for Customer
            if (selectedCustomer.hasAppointments()){
                ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                        LogEvent.AppLocation.CUSTOMERS, "Customer has Appointments. Deletion Failed");
                Main.logger.log(event);
                AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR, "CustomerSceneTitle", "custHasApptsErrorHeader",
                        "custHasApptsErrorContent").showAndWait();
                displayCustAppts(actionEvent);
            }else{
                Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
                newAlert.setTitle(I18nMgmt.translate("ConfirmText"));
                newAlert.setHeaderText(I18nMgmt.translate("CustDeleteConfirmHeader"));
                newAlert.setContentText(I18nMgmt.translate("CustDeleteConfirmContent") +
                        "\nCustomer: " + selectedCustomer.getName());
                Optional<ButtonType> result = newAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Value<Integer> custID = new Value<>(selectedCustomer.getId());
                    try{
                        //Delete selected cust
                        DBQuery.delete(Customer.deleteCustByID, custID);
                        //Update ObservableLists in DataMgmt
                        DataMgmt.initializeApplicationData();
                        ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                                LogEvent.AppLocation.CUSTOMERS, "Cusomter Deleted");
                        Main.logger.log(event);
                        AlertFactory.getNewDialogAlert(Alert.AlertType.INFORMATION,"CustomerSceneTitle","CustDeleteSuccessHeader",
                                "CustDeleteSuccessContent").showAndWait();
                    } catch (SQLException e) {
                        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(),
                                LogEvent.EventType.EXCEPTION, LogEvent.AppLocation.CUSTOMERS, e );
                        Main.logger.log(event);
                        AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustomerSceneTitle", "sqlErrorHeader",
                                "sqlDeleteErrorContent").showAndWait();
                    }
                }
            }
        }
    }
    /**
     * Sets the selected Customer Object and navigates to Scheduling Scene
     * @param actionEvent Created upon button click
     * @exception IOException Error occurs when the FXML document is not reachable - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void displayCustAppts(ActionEvent actionEvent) {
        if(setSelectedCustomer()){
        DataMgmt.setCurrentCustomer(getSelectedCustomer());
        DataMgmt.initializeApplicationData();
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.setUserData(getSelectedCustomer().getName());
        thisStage.show();
        }
    }

    ///// Navigation Error Alert Handler /////
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.CUSTOMERS, e);
        Main.logger.log(event);
        AlertFactory.getFXMLLoadErrorAlert("CustomerSceneTitle").showAndWait();
    }
}
