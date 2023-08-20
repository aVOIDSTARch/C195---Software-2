package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Model.Appointment;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DateTimeMgmt {
    public static final Locale LOCALE_SYS = Locale.getDefault();
    //public static final Locale LOCALE_SYS = new Locale("fr", "CA");
    public static final ZoneId ZONE_SYS = ZoneId.systemDefault();
    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter timeOnlyFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static final LocalTime businessHoursStart = LocalTime.parse("08:00", timeOnlyFormat);
    public static final LocalTime businessHoursEnd = LocalTime.parse("22:00", timeOnlyFormat);

    ///// TIME CONVERSION METHODS /////
    public static LocalDateTime convertLocalTZtoUTC(LocalDateTime localLDT){
        LocalDateTime newUTCldt = localLDT.atZone(DateTimeMgmt.ZONE_SYS)
                .withZoneSameInstant(ZONE_UTC)
                .toLocalDateTime();
        return newUTCldt;
    }

    public static LocalDateTime convertUTCtoLocalTimeZone(LocalDateTime UTCldt){
        LocalDateTime newLocalLDT = UTCldt.atZone(DateTimeMgmt.ZONE_UTC)
                .withZoneSameInstant(ZONE_SYS)
                .toLocalDateTime();
        return newLocalLDT;

    }

    ///// TIME AND DATE CREATION METHODS /////
    public static LocalDateTime getLocalDT(LocalDate date, String hours, String minutes){
        String time = padToTwoDigitsWithZero(hours) + ":" + padToTwoDigitsWithZero(minutes);
        return LocalDateTime.of(date, LocalTime.parse(time, timeOnlyFormat));
    }
    public static String padToTwoDigitsWithZero(String inputString){
        if (inputString.length() == 1) {
            return 0 + inputString;
        }
        return inputString;
    }
    ///// DATE AND TIME VALIDATION METHODS /////

    public static boolean isBetweenHours(LocalTime apptStartTime, LocalTime apptEndTime){
        return (apptStartTime.isAfter(DateTimeMgmt.businessHoursStart.minusMinutes(1))
                && apptStartTime.isBefore(DateTimeMgmt.businessHoursEnd.plusMinutes(1)))
                && (apptEndTime.isAfter(DateTimeMgmt.businessHoursStart.minusMinutes(1))
                && apptEndTime.isBefore(DateTimeMgmt.businessHoursEnd.plusMinutes(1))) ? true : false;
    }

    public static boolean isBetweenDateTime(LocalDateTime firstStartDateTIme, LocalDateTime firstEndDateTime,
                                            LocalDateTime secondStartDateTIme, LocalDateTime secondEndDateTime){
        boolean isInsideFirstRange = (secondStartDateTIme.isAfter(firstStartDateTIme.minusMinutes(1))
                && secondStartDateTIme.isBefore(firstEndDateTime.plusMinutes(1)))
                || (secondEndDateTime.isAfter(firstStartDateTIme.minusMinutes(1))
                && secondEndDateTime.isBefore(firstEndDateTime.plusMinutes(1))) ? true : false;
        boolean isInsideSecondRange = (firstStartDateTIme.isAfter(secondStartDateTIme.minusMinutes(1))
                && firstStartDateTIme.isBefore(secondEndDateTime.plusMinutes(1)))
                || (firstEndDateTime.isAfter(secondStartDateTIme.minusMinutes(1))
                && firstEndDateTime.isBefore(secondEndDateTime.plusMinutes(1))) ? true : false;
        return (isInsideFirstRange || isInsideSecondRange);
    }
    public static boolean checkStartEndSequence(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isBefore(endTime);
    }

    public static boolean checkApptOverlaps(Appointment newAppt) {
        AtomicBoolean result = new AtomicBoolean(false);
        DataMgmt.getAllApptsList().forEach(appt -> {
           if (isBetweenDateTime(appt.getStart(), appt.getEnd(), newAppt.getStart(), newAppt.getEnd())){
                result.set(true);
            }
        });
        return result.get();
    }
}
