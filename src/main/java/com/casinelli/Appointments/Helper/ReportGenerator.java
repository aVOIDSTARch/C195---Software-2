package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Model.Appointment;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public abstract class ReportGenerator {
    private static final Vector<String> reportResults = new Vector<>();

    private static void clearResults(){ reportResults.clear(); }
    public static Vector<String> generateApptsByTypeAndMonthReport(ObservableList<Appointment> allAppts){
        return reportResults;
    }
    public static Vector<String> generateScheduleByContactReport(ObservableList<Appointment> allAppts){
        return reportResults;
    }
    public static Vector<String> generateApplicationExceptionReport(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            reportResults.add(scanner.nextLine());
        }
        return reportResults;
    }
}
