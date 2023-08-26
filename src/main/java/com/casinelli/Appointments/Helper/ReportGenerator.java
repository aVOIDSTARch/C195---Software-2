package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.DAO.Value;
import com.casinelli.Appointments.Model.Appointment;
import javafx.collections.ObservableList;

import java.util.Vector;

public abstract class ReportGenerator {
    private static Vector<String> reportResults;

    private static void clearResults(){reportResults.clear();}
    public static Vector<String> generateApptsByTypeAndMonthReport(ObservableList<Appointment> allAppts){

    }
    public static Vector<String> generateScheduleByContactReport(ObservableList<Appointment> allAppts){

    }
    public static Vector<String> generateApplicationExceptionReport(ObservableList<Appointment> allAppts){

    }
}
