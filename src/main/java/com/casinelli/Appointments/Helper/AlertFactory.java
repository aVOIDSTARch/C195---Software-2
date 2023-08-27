package com.casinelli.Appointments.Helper;

import javafx.scene.control.Alert;

public abstract class AlertFactory {


    public static Alert getNewDialogAlert(Alert.AlertType type, String title, String header, String content){
        Alert newAlert = new Alert(type);
        newAlert.setTitle(I18nMgmt.translate(title));
        newAlert.setHeaderText(I18nMgmt.translate(header));
        newAlert.setContentText(I18nMgmt.translate(content));
        return newAlert;
    }


}
