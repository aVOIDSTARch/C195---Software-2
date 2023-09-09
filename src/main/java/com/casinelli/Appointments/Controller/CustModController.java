package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.Helper.AlertFactory;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.Customer;

import com.casinelli.Appointments.Model.ExceptionEvent;
import com.casinelli.Appointments.Model.LogEvent;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for Customer Modify Scene
 */
public class CustModController implements Initializable {
    ///// Controller instance variables /////
    Stage thisStage;
    Parent scene;
    //FXML Controls
    @javafx.fxml.FXML
    private Label lblCustModCustID;
    @javafx.fxml.FXML
    private Label lblCustModCustName;
    @javafx.fxml.FXML
    private Label lblCustModCustAddress;
    @javafx.fxml.FXML
    private Label lblCustModCustPostCode;
    @javafx.fxml.FXML
    private Label lblCustModCustPhone;
    @javafx.fxml.FXML
    private Label lblCustModCustDiv;
    @javafx.fxml.FXML
    private Label lblCustModCustCountry;
    @javafx.fxml.FXML
    private Label lblCustModAppName;

    ///// Input Textfields /////
    @javafx.fxml.FXML
    private TextField tfCustModCustID;
    @javafx.fxml.FXML
    private TextField tfCustModCustName;
    @javafx.fxml.FXML
    private TextField tfCustModCustAddress;
    @javafx.fxml.FXML
    private TextField tfCustModCustPostCode;
    @javafx.fxml.FXML
    private TextField tfCustModCustPhone;
    ///// Selection ComboBoxes /////
    @javafx.fxml.FXML
    private ComboBox<String> cboCustModCustDiv;
    @javafx.fxml.FXML
    private ComboBox<String> cboCustModCustCountry;
    ///// Buttons /////
    @javafx.fxml.FXML
    private Button btnCustModUpdate;
    @javafx.fxml.FXML
    private Button btnCustModCancel;

    ///// Error Preventing Boolean Bindings /////
    private BooleanBinding updateButtonDisabler;

    ///// Selected Customer to Update /////
    private Customer selectedCustomer;
    @javafx.fxml.FXML
    private Label lblCustModCompleteInputs;

    /**
     * Initializes the user Interface and Populates Selected Customer Data
     * @param url provided by launch method
     * @param resourceBundle provided by launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomerHubController.getSelectedCustomer();
        setUpdateButtonBindings(updateButtonDisabler);
        try {
            initializeSceneText();
            populateCountries();
            populateCustomerData();
        } catch (SQLException e) {
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.CUSTOMER_UPDATE, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustModSceneTitle","sqlErrorHeader",
                    "sqlRetrieveErrorContent").showAndWait();
        }

    }

    /**
     * Creates and binds a boolean binding that prevents the user from clicking the Create button without inputting
     * all information thus avoiding all errors for input and shows a label with instructions
     * @param binding BooleanBinding for Update button disable property
     */
    private void setUpdateButtonBindings(BooleanBinding binding) {
        binding =
                tfCustModCustName.textProperty().isEmpty()
                        .or(tfCustModCustAddress.textProperty().isEmpty())
                        .or(tfCustModCustPostCode.textProperty().isEmpty())
                        .or(tfCustModCustPhone.textProperty().isEmpty())
                        .or(cboCustModCustDiv.valueProperty().isNull())
                        .or(cboCustModCustCountry.valueProperty().isNull());
        btnCustModUpdate.disableProperty().bind(binding);
        lblCustModCompleteInputs.visibleProperty().bind(binding);
    }

    /**
     * Populate user interface with translated text and Customer Data
     * @throws SQLException occurs when SQL retrieve command fails
     */
    private void initializeSceneText() throws SQLException {
        //Text Labels
        lblCustModAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblCustModCustID.textProperty().setValue(I18nMgmt.translate("CustIdLabel"));
        lblCustModAppName.textProperty().setValue(I18nMgmt.translate("CustNameLabel"));
        lblCustModCustAddress.textProperty().setValue(I18nMgmt.translate("CustAddyLabel"));
        lblCustModCustPostCode.textProperty().setValue(I18nMgmt.translate("CustPostCodeLabel"));
        lblCustModCustPhone.textProperty().setValue(I18nMgmt.translate("CustPhoneLabel"));
        lblCustModCustDiv.textProperty().setValue(I18nMgmt.translate("FirstLvlDivLabel"));
        lblCustModCustCountry.textProperty().setValue(I18nMgmt.translate("CustCountryLabel"));
        //Button Text
        btnCustModUpdate.textProperty().setValue(I18nMgmt.translate("UpdateBtnText"));
        btnCustModCancel.textProperty().setValue(I18nMgmt.translate("CancelBtnText"));
        cboCustModCustCountry.getSelectionModel().select(DataMgmt.getCountryNameFromDivId(CustomerHubController.
                getSelectedCustomer().getDivisionId()));
        updateDivisionCbo(new ActionEvent());
        cboCustModCustDiv.getSelectionModel().select(DataMgmt.getDivisionNameFromDivId(CustomerHubController.
                getSelectedCustomer().getDivisionId()));
    }
    ///// ComboBox List Maintenance Methods /////
    /**
     * Builds a List of the country names based on the Database and assigns if to teh Countries ComboBox then calls send
     * the selected item to the populateDivisions method to complete the same for teh Divisions ComboBox
     * @throws SQLException SQL error when querying Database for country names
     */
    private void populateCountries() throws SQLException {
        cboCustModCustCountry.setItems(DataMgmt.getAllCountryNames());
        cboCustModCustCountry.getSelectionModel().selectFirst();
        populateDivisions(cboCustModCustCountry.getSelectionModel().getSelectedItem());
    }
    /**
     * Builds a List of the Divisions names based on the Database and assigns if to teh Divisions ComboBox
     * @param countryName Name of the country selected in the Countries ComboBox
     * @throws SQLException SQL error when querying Database for division names
     */
    private void populateDivisions(String countryName) throws SQLException {
        cboCustModCustDiv.setItems(DataMgmt.getListOfDivNamesByCountryId(
                DataMgmt.getCountryIdFromCntryName(countryName)));
    }
    /**
     * Updates the Divisions ComboBox whenever the Country ComboBox selection is changed
     * @param actionEvent selection changed event
     * @throws SQLException occurs when the SQL query fails to retrieve from the Database
     */
    @javafx.fxml.FXML
    private void updateDivisionCbo(ActionEvent actionEvent) {
        try{
            populateDivisions(cboCustModCustCountry.getSelectionModel().getSelectedItem());
        }catch(SQLException e){
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.CUSTOMER_UPDATE, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustModSceneTitle","sqlErrorHeader",
                    "sqlUpdateErrorContent").showAndWait();
        }
    }

    /**
     * Populates input fields with Selected Customer data
     */
    private void populateCustomerData() {
        tfCustModCustID.textProperty().setValue(String.valueOf(selectedCustomer.getId()));
        tfCustModCustName.textProperty().setValue(selectedCustomer.getName());
        tfCustModCustAddress.textProperty().setValue(selectedCustomer.getAddress());
        tfCustModCustPostCode.textProperty().setValue(selectedCustomer.getPostalCode());
        tfCustModCustPhone.textProperty().setValue(selectedCustomer.getPhone());
        cboCustModCustCountry.setValue(DataMgmt.getCountryNameFromDivId(selectedCustomer.getDivisionId()));
        cboCustModCustDiv.setValue(DataMgmt.getDivisionNameFromDivId(selectedCustomer.getDivisionId()));
    }

    ///// Button Handler Methods /////
    /**
     * Writes updated customer to database, requests local data refresh, navigates to Customer Scene
     * @param actionEvent button click event
     * @exception SQLException occurs when SQL update command fails
     * @exception IOException Occurs when FXML document cannot be found - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void updateCustomer(ActionEvent actionEvent) {
        updateSelectedCustomer();
        //Insert Customer into DB
        try {
            System.out.println(DBQuery.update(Customer.updateCustomer, selectedCustomer));
        } catch (SQLException e) {
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.CUSTOMER_UPDATE, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustModSceneTitle","sqlErrorHeader",
                    "sqlCreateErrorContent").showAndWait();
        }
        //Update ObservableLists in DataMgmt
        DataMgmt.initializeApplicationData();
        //Return Cust Scene
        thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
     * Cancels Update Customer process and returns to Customer Scene
     * @param actionEvent utton click event
     * @exception IOException Occurs when FXML document cannot be found - nested NullPointerException
     */
    @javafx.fxml.FXML
    public void cancelCustUpdate(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            showAndLogNavErrorAlert(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustomerSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

    /**
     * Updates Selected Customer object with data from input fields
     */
    ///// Customer Modification Helper Method /////
    private void updateSelectedCustomer() {
        LocalDateTime thisTime = LocalDateTime.now();
        selectedCustomer.setName(tfCustModCustName.getText());
        selectedCustomer.setAddress(tfCustModCustAddress.getText());
        selectedCustomer.setPostalCode(tfCustModCustPostCode.getText());
        selectedCustomer.setPhone(tfCustModCustPhone.getText());
        selectedCustomer.setDivisionId(cboCustModCustDiv.getSelectionModel().getSelectedIndex());
        selectedCustomer.setLastUpdatedBy(DataMgmt.getCurrentUser().getName());
        selectedCustomer.setLastUpdate(thisTime);
    }
    ///// Navigation Error Handler Method /////
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.CUSTOMER_UPDATE, e);
        Main.logger.log(event);
        AlertFactory.getFXMLLoadErrorAlert("CustModSceneTitle").showAndWait();
    }
}
