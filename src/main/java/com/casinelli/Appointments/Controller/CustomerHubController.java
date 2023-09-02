package com.casinelli.Appointments.Controller;


import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
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
import java.util.ResourceBundle;

public class CustomerHubController implements Initializable {
    //Controller instance variables
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
    private Button btnCustNavCustScene;
    @javafx.fxml.FXML
    private Button btnCustNavWelcHub;
    @javafx.fxml.FXML
    private Button btnCustNavReportsScene;
    @javafx.fxml.FXML
    private Button btnCustNavScheduleScene;
    @javafx.fxml.FXML
    private Button btnCustNavLogout;
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
    @javafx.fxml.FXML
    private Button btnCustCreate;
    @javafx.fxml.FXML
    private Button btnCustUpdate;
    @javafx.fxml.FXML
    private Button btnCustDelete;
    @javafx.fxml.FXML
    private Button btnCustDisplayCustAppts;
    @javafx.fxml.FXML
    private Label lblAttnCustDeletion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fill in text from I18n resource
        populateTextPropsCustScene();
        //Set up Customer TableView
        initializeCustTblView();
    }

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

    @javafx.fxml.FXML
    public void navToCustomerScene(ActionEvent actionEvent) {
        AlertFactory.getNewDialogAlert(Alert.AlertType.INFORMATION, "CustomerSceneTitle",
                "navToCurrentSceneHeader", "navToCurrentSceneContent").showAndWait();
    }

    @javafx.fxml.FXML
    public void navToWelcomeScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/welcomehub-view.fxml")));
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
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/reporting-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("ReportingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void navToScheduleScene(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void appLogout(ActionEvent actionEvent) {
        DataMgmt.setCurrentUser(null);
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/login-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("LoginSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    public static Customer getSelectedCustomer(){
        return selectedCustomer;
    }
    private void setSelectedCustomer(){
        selectedCustomer = tblVwCustomers.getSelectionModel().getSelectedItem();
    }

    @javafx.fxml.FXML
    public void createNewCustomer(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-add-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustAddSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    @javafx.fxml.FXML
    public void updateSelectedCustomer(ActionEvent actionEvent) {
        setSelectedCustomer();
        if(getSelectedCustomer() != null){
            thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                        .getResource("/com/casinelli/Appointments/customer-mod-view.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            thisStage.setTitle(I18nMgmt.translate("CustModSceneTitle"));
            thisStage.setScene(new Scene(scene));
            thisStage.show();
        }else{
            System.out.println("Customer not selected");
        }

    }

    @javafx.fxml.FXML
    public void deleteSelectedCustomer(ActionEvent actionEvent) {
        setSelectedCustomer();
        if(selectedCustomer != null){
            //Verify Zero Appts Scheduled for Customer
            if (selectedCustomer.hasAppointments()){
                System.out.println("this bitch got appts");
            }else{
                Value<Integer> custID = new Value<>(selectedCustomer.getId());
                try{
                    //Delete selected cust
                    DBQuery.delete(Customer.deleteCustByID, custID);
                    //Update ObservableLists in DataMgmt
                    DataMgmt.initializeApplicationData();
                } catch (SQLException e) {
                    ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(),
                            LogEvent.EventType.EXCEPTION, LogEvent.AppLocation.CUSTOMERS, e );
                    Main.logger.log(event);
                    AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustomerSceneTitle", "sqlErrorHeader",
                            "sqlDeleteErrorContent");
                }
            }

        }
    }

    @javafx.fxml.FXML
    public void displayCustAppts(ActionEvent actionEvent) {
        setSelectedCustomer();
        DataMgmt.setCurrentCustomer(getSelectedCustomer());
        DataMgmt.initializeApplicationData();
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/scheduling-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("SchedulingSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.setUserData(getSelectedCustomer().getName());
        thisStage.show();
    }
}
