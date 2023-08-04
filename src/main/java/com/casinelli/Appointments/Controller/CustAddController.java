package com.casinelli.Appointments.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CustAddController implements Initializable {
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
    private ComboBox cboCustAddCustDiv;
    @javafx.fxml.FXML
    private ComboBox cboCustAddCustCountry;
    @javafx.fxml.FXML
    private Button btnCustAddCreate;
    @javafx.fxml.FXML
    private Label lblWHAppName;

    @javafx.fxml.FXML
    public void createNewCustomer_add_scene(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
