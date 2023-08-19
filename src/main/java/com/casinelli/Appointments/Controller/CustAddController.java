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
import java.util.Objects;
import java.util.ResourceBundle;

public class CustAddController implements Initializable {
    //Controller instance variables
    Stage thisStage;
    Parent scene;
    //FXML Controls
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
    private ComboBox<String> cboCustAddCustDiv;
    @javafx.fxml.FXML
    private ComboBox<String> cboCustAddCustCountry;
    @javafx.fxml.FXML
    private Button btnCustAddCreate;
    @javafx.fxml.FXML
    private Label lblWHAppName;
    @javafx.fxml.FXML
    private Button btnCustAddCancel;

    @javafx.fxml.FXML
    public void createNewCustomer_add_scene(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSceneText();
        try {
            populateCountries();
        } catch (SQLException e) {
            System.out.println("failure");
        }
    }

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

    private void populateCountries() throws SQLException {
        cboCustAddCustCountry.setItems(DataMgmt.getAllCountryNames());
        cboCustAddCustCountry.getSelectionModel().selectFirst();
        populateDivisions(cboCustAddCustCountry.getSelectionModel().getSelectedItem());
    }
    private void populateDivisions(String countryName) throws SQLException {
        cboCustAddCustDiv.setItems(DataMgmt.getListOfDivNamesByCountryId(
                DataMgmt.getCountryIdFromCntryName(countryName)));
    }

    @javafx.fxml.FXML
    private void updateDivisionCbo(ActionEvent actionEvent) throws SQLException {
        populateDivisions(cboCustAddCustCountry.getSelectionModel().getSelectedItem());
    }
    @javafx.fxml.FXML
    public void cancelCustCreate(ActionEvent actionEvent) {
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
