package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.DateTimeMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerHubController implements Initializable {
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
    ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fill in text from I18n resource
        populateTextPropsCustScene();
        //Get All Customers ResultSet
        ResultSet allCustomerSet = null;
        try {
            allCustomerSet = DBQuery.retrieveAll(Customer.allCustomers);
        } catch (SQLException e) {
            System.out.println("Failed to get all customers from db");
        }
        //Populate allCustomersList
        try {
            populateAllCustomersList(allCustomerSet);
        } catch (SQLException e) {
            System.out.println("failed to populate all customers list");
        }
        //Set up Customer TableView
        initializeCustTblView();
    }

    private void initializeCustTblView() {
        /////Column Names
        tvColCust_CustID.textProperty().setValue(I18nMgmt.translate("ColNameID"));
        tvColCust_CustAddress.textProperty().setValue(I18nMgmt.translate("ColNameCustName"));
        tvColCust_CustAddress.textProperty().setValue(I18nMgmt.translate("ColNameAddress"));
        tvColCust_CustPostalCode.textProperty().setValue(I18nMgmt.translate("ColNamePostalCode"));
        tvColCust_CustPhone.textProperty().setValue(I18nMgmt.translate("ColNamePhone"));
        tvColCust_CustDiv.textProperty().setValue(I18nMgmt.translate("ColNameDivision"));
        tvColCust_CustCountry.textProperty().setValue(I18nMgmt.translate("ColNameCountry"));
        /////SET UP COLUMNS FOR DATA/////
        tblVwCustomers.setItems(allCustomersList);
        tvColCust_CustID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tvColCust_CustName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tvColCust_CustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tvColCust_CustPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        tvColCust_CustDiv.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        //tvColCust_CustCountry.setCellValueFactory(new PropertyValueFactory<>(""));


    }

    private void populateAllCustomersList(ResultSet rs) throws SQLException {
        while(rs.next()){
            allCustomersList.add(new Customer(rs));
        }
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
        /////LABELS/////
        lblCustSceneAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblCustSceneTitle.textProperty().setValue(I18nMgmt.translate("custSceneTitle"));
        lblCustNavTitle.textProperty().setValue(I18nMgmt.translate("NavTitle"));
        lblCustUsernameLabel.textProperty().setValue(I18nMgmt.translate("UsernameLabel"));
        lblCustZoneID.textProperty().setValue(DateTimeMgmt.ZONE_SYS.toString());
        lblCustUsername.textProperty().setValue(DataMgmt.getCurrentUser().getName());



    }

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
    }

    @javafx.fxml.FXML
    public void createNewCustomer(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateSelectedCustomer(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteSelectedCustomer(ActionEvent actionEvent) {
    }
}
