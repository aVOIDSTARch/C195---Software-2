package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
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

    /**
     * Clears Sting Vector for new report
     */
    private static void clearResults(){ reportResults.clear(); }

    ///// Report 1 Generation Methods /////
    /**
     * Creates a Vector of String Objects contains a properly formatted and translate report of Appointment count for the
     * month and date specified
     * @param allAppts ObservableList<Appointment> containing all Appointments in the Database
     * @param month String month selected by the user
     * @param type String type of Appointment selected by the user
     * @return Vector<String> containing the lines of text for the report is sequential order
     */
    public static Vector<String> generateApptsByTypeAndMonthReport(ObservableList<Appointment> allAppts,
                                                                   String month, String type){
        clearResults();
        insertReport1Title();
        reportResults.add(getApptCountStringWithMonthAndType(getApptsByMonthAndType(allAppts, month, type)));
        return reportResults;
    }
    /**
     * Add the Title String to the Report 1 Vector
     */
    private static void insertReport1Title(){
        reportResults.add(I18nMgmt.translate("Report1Title"));
        reportResults.add("");
    }

    /**
     * Creates an ObservableList of Appointment objects that match the user input criteria
     * Lambda expression used in forEach to reduce list of appointments
     * @param allApptsList ObservableList<Appointment> containing all Appointments in the Database
     * @param month String month selected by the user
     * @param type String type of Appointment selected by the user
     * @return ObservableList<Appointment> a list reduced using the criteria supplied by user input
     */
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

    /**
     * Creates a translated and formatted String to output to the  USer for Report 1 Data
     * @param finalApptList ObservableList<Appointment> containing the list of Appointments after reduced by user input
     * @return String fianl report string to include in Report 1
     */
    private static String getApptCountStringWithMonthAndType(ObservableList<Appointment> finalApptList){
        int numAppt = finalApptList.size();
        if(numAppt > 0){
            String month = I18nMgmt.translate(finalApptList.get(0).getStart().getMonth().toString());
            String type = finalApptList.get(0).getType();
            return String.format(getTranslatedApptCountFormatString(),
                    month, type, numAppt);
        }else{
            return I18nMgmt.translate("noApptsFound");
        }

    }

    /**
     * Selects the correct formatting string based on the user's system settings
     *
     * @return String translated into the local user's preferred language
     */
    private static String getTranslatedApptCountFormatString(){
        return (DateTimeMgmt.LOCALE_SYS == DateTimeMgmt.LOCALE_FR_CA)
                ? apptCountStringFormatFrancais : apptCountStringFormatEnglish;
    }

    ///// Report 2 Generation Methods /////

    /**
     * Creates a Vector of Strings to output to the user containing all the Contact's Schedules in Chronological order
     * @param allContacts ObservableList<Contact> containing all contacts in the Database
     * @return Vector<String> containing all the lines of Report 2
     */
    public static Vector<String> generateScheduleByContactReport(ObservableList<Contact> allContacts){
        clearResults();
        insertReport2Title();
        allContacts.forEach(ReportGenerator::insertContactSection);
        return reportResults;
    }
    /**
     * Adds the translated title string to the Report 2 Vector
     */
    private static void insertReport2Title(){
        reportResults.add(I18nMgmt.translate("Report2Title"));
        reportResults.add("");
    }

    /**
     * Inserts the schedule of one Contact object
     * Lambda expression used in forEach to write string to vector
     * @param thisContact Contact whose schedule should be written to the Report 2 Vector
     */
    private static void insertContactSection(Contact thisContact){
        reportResults.add(String.format(getTranslatedContactFormatString(),
                thisContact.getId(), thisContact.getName(), thisContact.getEmail()));

        ObservableList<Appointment> apptsForThisContact = DataMgmt.getApptsByContact(thisContact);
        apptsForThisContact.sort(sortApptsByStartDate);
        if(apptsForThisContact.size() > 0){
            apptsForThisContact.forEach(appt -> reportResults.add(String.format(getTranslatedApptFormatString(),
                    appt.getId(),appt.getName(),appt.getType(),appt.getDescription(),appt.getStartString(),
                    appt.getEndString(),appt.getContactId())));
            reportResults.add("Total: " + DataMgmt.getApptsByContact(thisContact).size());
            reportResults.add("");
        }else{
            reportResults.add(I18nMgmt.translate("EmptySchedule"));
        }
    }

    /**
     * Selects the correct formatting string based on the user's system settings
     * @return String translated into the local user's preferred language
     */
    private static String getTranslatedApptFormatString(){
        return (DateTimeMgmt.LOCALE_SYS == DateTimeMgmt.LOCALE_FR_CA)
                ? appointmentStringFormatFrancais : appointmentStringFormatEnglish;
    }

    /**
     * Selects the correct formatting string based on the user's system settings
     *
     * @return String translated into the local user's preferred language
     */
    private static String getTranslatedContactFormatString(){
        return (DateTimeMgmt.LOCALE_SYS == DateTimeMgmt.LOCALE_FR_CA)
                ? contactStringFormatFrancais : contactStringFormatEnglish;
    }

    ///// Report 3 Generation Methods /////
    /**
     * Creates the Vector of String objects to Display the selected log file to the user
     * @param filename String log file name
     * @return Vector<String> containing the lines of Report 3
     * @throws FileNotFoundException occurs when the report generator cannot locate the log file with the name provided
     */
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
