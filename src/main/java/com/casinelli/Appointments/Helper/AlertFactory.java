package com.casinelli.Appointments.Helper;

import javafx.scene.control.Alert;

/**
 * Abstract class to create Alert Objects throughout application
 */
public abstract class AlertFactory {

    /**
     * Create new Alert object
     * @param type Alert.AlertType type of Alert to display
     * @param title String title of Alert
     * @param header String header of Alert
     * @param content String content of Alert
     * @return Alert to display
     */
    public static Alert getNewDialogAlert(Alert.AlertType type, String title, String header, String content){
        Alert newAlert = new Alert(type);
        newAlert.setTitle(I18nMgmt.translate(title));
        newAlert.setHeaderText(I18nMgmt.translate(header));
        newAlert.setContentText(I18nMgmt.translate(content));
        return newAlert;
    }

    /**
     * Create a Universal FXML Load Error Alert
     * @param title String title to display
     * @return Alert for Navigation error
     */
    public static Alert getFXMLLoadErrorAlert(String title){
        Alert newAlert = new Alert(Alert.AlertType.ERROR);
        newAlert.setTitle(I18nMgmt.translate(I18nMgmt.translate(title)));
        newAlert.setHeaderText(I18nMgmt.translate("FXMLLoadErrorHeader"));
        newAlert.setContentText(I18nMgmt.translate("FXMLLoadErrorContent"));
        return newAlert;
    }
}
