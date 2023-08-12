package com.casinelli.Appointments.Controller;

import com.casinelli.Appointments.Helper.DataMgmt;
import com.casinelli.Appointments.Helper.I18nMgmt;
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
    private ComboBox cboCustModCustDiv;
    @javafx.fxml.FXML
    private ComboBox cboCustModCustCountry;
    @javafx.fxml.FXML
    private Button btnCustModUpdate;
    @javafx.fxml.FXML
    private Label lblCustModAppName;
    @javafx.fxml.FXML
    private Button btnCustModCancel;

    @javafx.fxml.FXML
    public void updateCustomer_mod_scene(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            populateCountries();
            initializeSceneText();
        } catch (SQLException e) {
            System.out.println("failure");
        }

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
        btnCustModUpdate.textProperty().setValue(I18nMgmt.translate("CustUpdateBtnText"));
        cboCustModCustCountry.getSelectionModel().select(DataMgmt.getCountryNameFromDivId(CustomerHubController.
                getSelectedCustomer().getDivisionId()));
        updateDivisionCbo(new ActionEvent());
        cboCustModCustDiv.getSelectionModel().select(DataMgmt.getDivisionNameFromDivId(CustomerHubController.
                getSelectedCustomer().getDivisionId()));
    }
    private void populateCountries() throws SQLException {
        cboCustModCustCountry.setItems(DataMgmt.getAllCountryNames());
        cboCustModCustCountry.getSelectionModel().selectFirst();
        populateDivisions(cboCustModCustCountry.getSelectionModel().getSelectedItem().toString());
    }
    private void populateDivisions(String countryName) throws SQLException {
        cboCustModCustDiv.setItems(DataMgmt.getListOfDivNamesByCountryId(
                DataMgmt.getCountryIdFromCntryName(countryName)));
    }

    @javafx.fxml.FXML
    private void updateDivisionCbo(ActionEvent actionEvent) throws SQLException {
        populateDivisions(cboCustModCustCountry.getSelectionModel().getSelectedItem().toString());
    }
    private void returnToCustomerScene(){

    }

    @javafx.fxml.FXML
    public void cancelCustUpdate(ActionEvent actionEvent) {
        thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("/com/casinelli/Appointments/customer-view.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        thisStage.setTitle(I18nMgmt.translate("CustomerSceneTitle"));
        thisStage.setScene(new Scene(scene));
        thisStage.show();
    }
}
