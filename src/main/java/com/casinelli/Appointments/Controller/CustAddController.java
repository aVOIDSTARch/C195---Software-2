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
 * Controller object for Customer Add Scene
 */
public class CustAddController implements Initializable {
    ///// Controller instance variables /////
    Stage thisStage;
    Parent scene;

    ///// FXML Controls /////
    @javafx.fxml.FXML
    private Label lblCustAddCustID;
    @javafx.fxml.FXML
    private Label lblCustAddCustName;
    @javafx.fxml.FXML
    private Label lblCustAddCustAddress;
    @javafx.fxml.FXML
    private Label lblCustAddCustPostCode;
    @javafx.fxml.FXML
    private Label lblCustAddCustPhone;
    @javafx.fxml.FXML
    private Label lblCustAddCustDiv;
    @javafx.fxml.FXML
    private Label lblCustAddCustCountry;
    @javafx.fxml.FXML
    private Label lblWHAppName;
    @javafx.fxml.FXML
    private Label lblCustAddCompleteInputs;
    ///// Input Textfields /////
    @javafx.fxml.FXML
    private TextField tfCustAddCustID;
    @javafx.fxml.FXML
    private TextField tfCustAddCustName;
    @javafx.fxml.FXML
    private TextField tfCustAddCustAddress;
    @javafx.fxml.FXML
    private TextField tfCustAddCustPostCode;
    @javafx.fxml.FXML
    private TextField tfCustAddCustPhone;
    @javafx.fxml.FXML
    ///// Selection ComboBoxes /////
    private ComboBox<String> cboCustAddCustDiv;
    @javafx.fxml.FXML
    private ComboBox<String> cboCustAddCustCountry;
    ///// Buttons /////
    @javafx.fxml.FXML
    private Button btnCustAddCreate;
    @javafx.fxml.FXML
    private Button btnCustAddCancel;




    /**
     * Initializes User Interface for Customer Add Scene
     * @param url provided by launch method
     * @param resourceBundle provided by launch method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBindings();

        initializeSceneText();
        try {
            populateCountries();
        } catch (SQLException e) {
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.CUSTOMER_CREATE, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustAddSceneTitle","sqlErrorHeader",
                    "sqlRetrieveErrorContent").showAndWait();
        }
    }

    /**
     * Creates and binds a boolean binding that prevents the user from clicking the Create button without inputting
     * all information thus avoiding all errors for input and shows a label with instructions
     */
    private void setBindings() {
        BooleanBinding binding = tfCustAddCustName.textProperty().isEmpty()
                .or(tfCustAddCustAddress.textProperty().isEmpty())
                .or(tfCustAddCustPostCode.textProperty().isEmpty())
                .or(tfCustAddCustPhone.textProperty().isEmpty())
                .or(cboCustAddCustDiv.valueProperty().isNull())
                .or(cboCustAddCustCountry.valueProperty().isNull());
        btnCustAddCreate.disableProperty().bind(binding);
        lblCustAddCompleteInputs.visibleProperty().bind(binding);
    }


    /**
     * Populates all translated text for scene
     */
    private void initializeSceneText() {
        //Text Labels
        lblWHAppName.textProperty().setValue(I18nMgmt.translate("labelAppName"));
        lblCustAddCustID.textProperty().setValue(I18nMgmt.translate("CustIdLabel"));
        lblCustAddCustName.textProperty().setValue(I18nMgmt.translate("CustNameLabel"));
        lblCustAddCustAddress.textProperty().setValue(I18nMgmt.translate("CustAddyLabel"));
        lblCustAddCustPostCode.textProperty().setValue(I18nMgmt.translate("CustPostCodeLabel"));
        lblCustAddCustPhone.textProperty().setValue(I18nMgmt.translate("CustPhoneLabel"));
        lblCustAddCustDiv.textProperty().setValue(I18nMgmt.translate("FirstLvlDivLabel"));
        lblCustAddCustCountry.textProperty().setValue(I18nMgmt.translate("CustCountryLabel"));
        //Prompt Text
        tfCustAddCustID.promptTextProperty().setValue(I18nMgmt.translate("CustIDPlchldrText"));
        tfCustAddCustName.promptTextProperty().setValue(I18nMgmt.translate("CustNamePlchldrText"));
        tfCustAddCustAddress.promptTextProperty().setValue(I18nMgmt.translate("CustAddyPlchldrText"));
        tfCustAddCustPostCode.promptTextProperty().setValue(I18nMgmt.translate("CustPostCodePlchldrText"));
        tfCustAddCustPhone.promptTextProperty().setValue(I18nMgmt.translate("CustPhonePlchldrText"));
        //Button Text
        btnCustAddCreate.textProperty().setValue(I18nMgmt.translate("CreateBtnText"));
        btnCustAddCancel.textProperty().setValue(I18nMgmt.translate("CancelBtnText"));
    }

    ///// ComboBox List Maintenance Methods /////
    /**
     * Builds a List of the country names based on the Database and assigns if to teh Countries ComboBox then calls send
     * the selected item to the populateDivisions method to complete the same for teh Divisions ComboBox
     * @throws SQLException SQL error when querying Database for country names
     */
    private void populateCountries() throws SQLException {
        cboCustAddCustCountry.setItems(DataMgmt.getAllCountryNames());
        cboCustAddCustCountry.getSelectionModel().selectFirst();
        populateDivisions(cboCustAddCustCountry.getSelectionModel().getSelectedItem());
    }

    /**
     * Builds a List of the Divisions names based on the Database and assigns if to teh Divisions ComboBox
     * @param countryName Name of the country selected in the Countries ComboBox
     * @throws SQLException SQL error when querying Database for division names
     */
    private void populateDivisions(String countryName) throws SQLException {
        cboCustAddCustDiv.setItems(DataMgmt.getListOfDivNamesByCountryId(
                DataMgmt.getCountryIdFromCntryName(countryName)));
    }

    /**
     * Updates the Divisions ComboBox whenever the Country ComboBox selection is changed
     * @param actionEvent selection changed event
     */
    @javafx.fxml.FXML
    private void updateDivisionCbo(ActionEvent actionEvent) {
        try{
            populateDivisions(cboCustAddCustCountry.getSelectionModel().getSelectedItem());
        }catch(SQLException e){
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.CUSTOMER_CREATE, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustAddSceneTitle","sqlErrorHeader",
                    "sqlRetrieveErrorContent").showAndWait();
        }
    }

    ///// Button Click Handler Methods /////
    /**
     * Requests a new Customer object and attempts to store it in database and sends a request to update local data storage
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void createNewCustomer_add_scene(ActionEvent actionEvent) {
        Customer newCust = buildNewCustomer();
        try {
            System.out.println(DBQuery.create(Customer.insertCustomer, newCust));
        } catch (SQLException e) {
            ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                    LogEvent.AppLocation.CUSTOMER_CREATE, e);
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"CustAddSceneTitle","sqlErrorHeader",
                    "sqlCreateErrorContent").showAndWait();
        }
        //Update ObservableLists in DataMgmt
        DataMgmt.initializeApplicationData();
        //Return Appt Scene
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
     * Cancels Create Customer process and returns to Customer Scene
     * @param actionEvent button click event
     */
    @javafx.fxml.FXML
    public void cancelCustCreate(ActionEvent actionEvent) {
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

    ///// Object Factory Helper Method /////
    /**
     * Creates a new Customer object from the information input in the user interface
     * @return Customer object
     */
    private Customer buildNewCustomer() {
        LocalDateTime thisTime = LocalDateTime.now();
        return new Customer(
                8888,
                tfCustAddCustName.getText(),
                tfCustAddCustAddress.getText(),
                tfCustAddCustPostCode.getText(),
                tfCustAddCustPhone.getText(),
                thisTime,
                DataMgmt.getCurrentUser().getName(),
                thisTime,
                DataMgmt.getCurrentUser().getName(),
                cboCustAddCustDiv.getSelectionModel().getSelectedIndex()
        );
    }
    ///// Disabled Button Label Displayer ////


    ///// Navigation Error Handler Method /////
    /**
     * Displays a navigation error to user and logs it to Exception Log
     * @param e IOException passed down from parent function
     */
    private void showAndLogNavErrorAlert(Exception e){
        ExceptionEvent event = new ExceptionEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.EXCEPTION,
                LogEvent.AppLocation.CUSTOMER_CREATE, e);
        Main.logger.log(event);
        AlertFactory.getFXMLLoadErrorAlert("CustAddSceneTitle").showAndWait();
    }
}
