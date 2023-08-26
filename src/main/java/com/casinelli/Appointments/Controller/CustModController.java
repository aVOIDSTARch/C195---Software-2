package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.DAO.DBQuery;
import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
import com.casinelli.Appointments.Model.Appointment;
import com.casinelli.Appointments.Model.Customer;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustModController implements Initializable {
    //Controller instance variables
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
    private TextField tfCustModCustID;
    @javafx.fxml.FXML
    private TextField tfCustModCustName;
    @javafx.fxml.FXML
    private TextField tfCustModCustAddress;
    @javafx.fxml.FXML
    private TextField tfCustModCustPostCode;
    @javafx.fxml.FXML
    private TextField tfCustModCustPhone;
    @javafx.fxml.FXML
    private ComboBox<String> cboCustModCustDiv;
    @javafx.fxml.FXML
    private ComboBox<String> cboCustModCustCountry;
    @javafx.fxml.FXML
    private Button btnCustModUpdate;
    @javafx.fxml.FXML
    private Label lblCustModAppName;
    @javafx.fxml.FXML
    private Button btnCustModCancel;

    private BooleanBinding updateButtonDisabler;
    private Customer selectedCustomer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomerHubController.getSelectedCustomer();
        setUpdateButtonBindings();
        try {
            initializeSceneText();
            populateCustomerData();
            populateCountries();
        } catch (SQLException e) {
            System.out.println("failure");
        }

    }

    private void setUpdateButtonBindings() {
        updateButtonDisabler =
                tfCustModCustName.textProperty().isEmpty()
                        .or(tfCustModCustAddress.textProperty().isEmpty())
                        .or(tfCustModCustPostCode.textProperty().isEmpty())
                        .or(tfCustModCustPhone.textProperty().isEmpty())
                        .or(cboCustModCustDiv.valueProperty().isNull())
                        .or(cboCustModCustCountry.valueProperty().isNull());
        btnCustModUpdate.disableProperty().bind(updateButtonDisabler);
    }
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
        //Fill in Properties of Selected Object
        tfCustModCustID.textProperty().setValue(String.valueOf(CustomerHubController.getSelectedCustomer().getId()));
        tfCustModCustName.textProperty().setValue(CustomerHubController.getSelectedCustomer().getName());
        tfCustModCustAddress.textProperty().setValue(CustomerHubController.getSelectedCustomer().getAddress());
        tfCustModCustPostCode.textProperty().setValue(CustomerHubController.getSelectedCustomer().getPostalCode());
        tfCustModCustPhone.textProperty().setValue(CustomerHubController.getSelectedCustomer().getPhone());
        //Button Text
        btnCustModUpdate.textProperty().setValue(I18nMgmt.translate("UpdateBtnText"));
        btnCustModCancel.textProperty().setValue(I18nMgmt.translate("CancelBtnText"));
        cboCustModCustCountry.getSelectionModel().select(DataMgmt.getCountryNameFromDivId(CustomerHubController.
                getSelectedCustomer().getDivisionId()));
        updateDivisionCbo(new ActionEvent());
        cboCustModCustDiv.getSelectionModel().select(DataMgmt.getDivisionNameFromDivId(CustomerHubController.
                getSelectedCustomer().getDivisionId()));
    }
    private void populateCustomerData() {
        tfCustModCustID.textProperty().setValue(String.valueOf(selectedCustomer.getId()));
        tfCustModCustName.textProperty().setValue(selectedCustomer.getName());
        tfCustModCustAddress.textProperty().setValue(selectedCustomer.getAddress());
        tfCustModCustPostCode.textProperty().setValue(selectedCustomer.getPostalCode());
        tfCustModCustPhone.textProperty().setValue(selectedCustomer.getPhone());
        cboCustModCustCountry.setValue(DataMgmt.getCountryNameFromDivId(selectedCustomer.getDivisionId()));
        cboCustModCustDiv.setValue(DataMgmt.getDivisionNameFromDivId(selectedCustomer.getDivisionId()));
    }
    private void populateCountries() throws SQLException {
        cboCustModCustCountry.setItems(DataMgmt.getAllCountryNames());
        cboCustModCustCountry.getSelectionModel().selectFirst();
        populateDivisions(cboCustModCustCountry.getSelectionModel().getSelectedItem());
    }
    private void populateDivisions(String countryName) throws SQLException {
        cboCustModCustDiv.setItems(DataMgmt.getListOfDivNamesByCountryId(
                DataMgmt.getCountryIdFromCntryName(countryName)));
    }

    @javafx.fxml.FXML
    private void updateDivisionCbo(ActionEvent actionEvent) throws SQLException {
        populateDivisions(cboCustModCustCountry.getSelectionModel().getSelectedItem());
    }
    @javafx.fxml.FXML
    public void updateCustomer_mod_scene(ActionEvent actionEvent) {
        updateSelectedCustomer();
        //Insert Cust into DB
        try {
            System.out.println(DBQuery.update(Customer.updateCustomer, selectedCustomer));
        } catch (SQLException e) {
            System.out.println("Failed to write to DB");
        }
        //Update ObservableLists in DataMgmt
        DataMgmt.initializeApplicationData();
        //Return Cust Scene
        thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(Objects.requireNonNull(getClass()
                    .getResource("/com/casinelli/Appointments/customer-view.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustomerSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }

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

    @javafx.fxml.FXML
    public void cancelCustUpdate(ActionEvent actionEvent) {
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
}
