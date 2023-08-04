package com.casinelli.Appointments.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustModController implements Initializable {
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
    public void updateCustomer_mod_scene(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
