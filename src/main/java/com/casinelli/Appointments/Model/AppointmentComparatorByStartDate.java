package com.casinelli.Appointments.Model;

import java.util.Comparator;

/**
 * Comparator for sorting Appointment objects by LocalDateTime in Start property
 */
public class AppointmentComparatorByStartDate implements Comparator<Appointment> {
    /**
     * Compares the start dates for the Appointment objects provided
     * @param appt1 the first object to be compared.
     * @param appt2 the second object to be compared.
     * @return int -1 if before, 0 if equal, 1 if after
     */
    @Override
    public int compare(Appointment appt1, Appointment appt2) {
        return appt1.getStart().compareTo(appt2.getStart());
    }
}
