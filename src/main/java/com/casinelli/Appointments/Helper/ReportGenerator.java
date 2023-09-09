package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Month;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

import static com.casinelli.Appointments.Helper.DataMgmt.sortApptsByStartDate;

public abstract class ReportGenerator {
    ///// Report Strings Vector/////
    private static final Vector<String> reportResults = new Vector<>();
    //////// Report String Formats ////////

    ///// Report 1 Format Strings /////
    private static final String apptCountStringFormatEnglish
            = "The total number of appointments for the month of %s with the type of %s is %d.";
    private static final String apptCountStringFormatFrancais
            = "Le nombre total de rendez-vous pour le mois de %s avec le type of %s est de %d.";

    ///// Report 2 Format Strings /////
    private static final String appointmentStringFormatEnglish =
            "Appt ID: %d Title: %s Type: %s Description: %s Start: %s End: %s with Customer ID: %s.";
    private static final String appointmentStringFormatFrancais =
            "Rendez-vous ID: %d Titre: %s Type: %s Description: %s Commencer A: %s Fini A: %s avec Client ID: %d.";
    private static final String contactStringFormatEnglish = "Contact: %d Name: %s Email: %s";
    private static final String contactStringFormatFrancais = "Contact: %d Name: %s Email: %s";

    private static void clearResults(){ reportResults.clear(); }

    ///// Report 1 Generation Methods /////
    public static Vector<String> generateApptsByTypeAndMonthReport(ObservableList<Appointment> allAppts,
                                                                   String month, String type){
        clearResults();
        insertReport1Title();
        final ObservableList<Appointment> apptsByMonth = FXCollections.observableArrayList();
        reportResults.add(getApptCountStringWithMonthAndType(getApptsByMonthAndType(allAppts, month, type)));
        return reportResults;
    }

    private static void insertReport1Title(){
        reportResults.add(I18nMgmt.translate("Report1Title"));
        reportResults.add("");
    }

   private static ObservableList<Appointment> getApptsByMonthAndType(ObservableList<Appointment> allApptsList,
                                                                     String month, String type){
        String thisMonth = I18nMgmt.translate(month);
        final ObservableList<Appointment> apptsByMonthAndTypeList = FXCollections.observableArrayList();
        allApptsList.forEach(appt -> {
           if((appt.getStart().getMonth().toString().equalsIgnoreCase(thisMonth) &&
           appt.getType().equalsIgnoreCase(type))){
               apptsByMonthAndTypeList.add(appt);
           }
        });
        return apptsByMonthAndTypeList;
    }

    private static String getApptCountStringWithMonthAndType(ObservableList<Appointment> finalApptList){
        int numAppt = finalApptList.size();
        if(numAppt > 0){
            String month = I18nMgmt.translate(finalApptList.get(0).getStart().getMonth().toString());
            String type = finalApptList.get(0).getType();
            return String.format(getTranslatedApptCountFormatString(DateTimeMgmt.LOCALE_SYS),
                    month, type, numAppt);
        }else{
            return I18nMgmt.translate("noApptsFound");
        }

    }
    private static String getTranslatedApptCountFormatString(Locale locale){

        return (locale == DateTimeMgmt.LOCALE_FR_CA)
                ? apptCountStringFormatFrancais : apptCountStringFormatEnglish;
    }

    ///// Report 2 Generation Methods /////
    public static Vector<String> generateScheduleByContactReport(ObservableList<Contact> allContacts){
        clearResults();
        insertReport2Title();
        allContacts.forEach(ReportGenerator::insertContactSection);
        return reportResults;
    }

    private static void insertReport2Title(){
        reportResults.add(I18nMgmt.translate("Report2Title"));
        reportResults.add("");
    }
    private static void insertContactSection(Contact thisContact){
        reportResults.add(String.format(getTranslatedContactFormatString(DateTimeMgmt.LOCALE_SYS),
                thisContact.getId(), thisContact.getName(), thisContact.getEmail()));

        ObservableList<Appointment> apptsForThisContact = DataMgmt.getApptsByContact(thisContact);
        apptsForThisContact.sort(sortApptsByStartDate);
        if(apptsForThisContact.size() > 0){
            apptsForThisContact.forEach(appt -> {
                reportResults.add(String.format(getTranslatedApptFormatString(DateTimeMgmt.LOCALE_SYS),
                        appt.getId(),appt.getName(),appt.getType(),appt.getDescription(),appt.getStartString(),
                        appt.getEndString(),appt.getContactId()));
            });
            reportResults.add("Total: " + DataMgmt.getApptsByContact(thisContact).size());
            reportResults.add("");
        }else{
            reportResults.add(I18nMgmt.translate("EmptySchedule"));
        }
    }
    private static String getTranslatedApptFormatString(Locale locale){
        return (locale == DateTimeMgmt.LOCALE_FR_CA)
                ? appointmentStringFormatFrancais : appointmentStringFormatEnglish;
    }

    /**
     * @param locale
     * @return
     */
    private static String getTranslatedContactFormatString(Locale locale){
        return (locale == DateTimeMgmt.LOCALE_FR_CA)
                ? contactStringFormatFrancais : contactStringFormatEnglish;
    }

    ///// Report 2 Generation Methods /////
    public static Vector<String> generateLogReport(String filename) throws FileNotFoundException {
        clearResults();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            reportResults.add(scanner.nextLine());
        }
        return reportResults;
    }
}
