package com.casinelli.Appointments.Helper;

import com.casinelli.Appointments.Main;
import com.casinelli.Appointments.Model.ApplicationEvent;
import com.casinelli.Appointments.Model.Appointment;
import com.casinelli.Appointments.Model.LogEvent;

import javafx.scene.control.Alert;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to handle program specific date and time operations
 */
public abstract class DateTimeMgmt {
    ///// Locale Variables /////
    public static final Locale LOCALE_FR_CA = new Locale.Builder().setLanguage("fr").setRegion("CA").build();
    public static final Locale LOCALE_SYS = Locale.getDefault();
    //public static final Locale LOCALE_SYS = LOCALE_FR_CA; //used to test language change without changing system
    public static final ZoneId ZONE_SYS = ZoneId.systemDefault();
    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_EST = ZoneId.of("America/New_York");
    ///// Date and Time Formatters /////
    public static final DateTimeFormatter dateAndTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter timeOnlyFormat = DateTimeFormatter.ofPattern("HH:mm");

    ///// Business Time Variables in EST /////
    public static final LocalTime businessHoursStartInEST = LocalTime.parse("08:00", timeOnlyFormat);
    public static final LocalTime businessHoursEndInEST = LocalTime.parse("22:00", timeOnlyFormat);

    ///// TIME ZONE CONVERSION METHOD /////

    /**
     * Convert the time in a LocalDateTiem to a different time zone
     * @param thisLDT LocalDateTime to convert
     * @param oldZone ZoneId of old time zone
     * @param newZone ZoneId of new time zone
     * @return LocalDateTime after conversion
     */
    public static LocalDateTime convertToLDTInZone(LocalDateTime thisLDT, ZoneId oldZone, ZoneId newZone){
        return thisLDT.atZone(oldZone)
                .withZoneSameInstant(newZone)
                .toLocalDateTime();
    }

    ///// TIME AND DATE CREATION METHODS FOR COMBOBOX INPUTS /////
    /**
     * Create instance of ZonedDateTime at NOW() in Local Time Zone
     * @return ZonedDateTime in local system time zone
     */
    public static ZonedDateTime getLocalZDTNow(){
        return ZonedDateTime.now(ZONE_SYS);
    }

    /**
     * Verifies object is in same year-week
     * @param first ZoneDateTime first date to compare
     * @param second ZoneDateTime second date to compare
     * @return boolean true if the dates are in the same year-week
     */
    public static boolean isSameYearWeek(ZonedDateTime first, ZonedDateTime second){
        return (first.get(IsoFields.WEEK_BASED_YEAR) == second.get(IsoFields.WEEK_BASED_YEAR))
                && (first.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == second.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
    }
    /**
     * Verifies object is in same month
     * @param first LocalDateTime first date to compare
     * @param second LocalDateTime second date to compare
     * @return boolean true if the dates are in the same month
     */
    public static boolean isSameYearMonth(LocalDateTime first, LocalDateTime second){
        return (first.getYear() == second.getYear()) && (first.getMonth() == second.getMonth());
    }
    public static boolean isAfterNow(LocalDateTime startTime){
        boolean isAfter = startTime.isAfter(LocalDateTime.now().minusSeconds(1));
        if(isAfter){
            return true;
        }else{
            ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                    LogEvent.AppLocation.DATETIMEMGMT,"Appointment times not in chronological order");
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"ApptCreationFailureTitle",
                    "ApptInPastHeader", "ApptInPastContent").showAndWait();
            return false;
        }
    }


    /**
     * Creates a LocalDateTime object from a LocalDate and two-digit Strings for hours and minutes
     * @param date LocalDate object to append time
     * @param hours String in two digits to represent the hours
     * @param minutes String in two digits to represent the minutes
     * @return LocalDateTime object comprised of arguments' data
     */
    public static LocalDateTime getLocalDT(LocalDate date, String hours, String minutes){
        String time = padToTwoDigitsWithZero(hours) + ":" + padToTwoDigitsWithZero(minutes);
        return LocalDateTime.of(date, LocalTime.parse(time, timeOnlyFormat));
    }

    /**
     * Pads string with a leading zero
     * @param inputString String to pad
     * @return String padded string object
     */
    public static String padToTwoDigitsWithZero(String inputString){
        if (inputString.length() == 1) {
            return 0 + inputString;
        }
        return inputString;
    }

    ///// DATE AND TIME VALIDATION METHODS /////
    /**
     * Verifies that the Apointment would occur within business hours in teh EST time zone
     * @param apptStartTime LocalTime start time of Appointment
     * @param apptEndTime LocalTime end time of Appointment
     * @return boolean true if the Appointment is within business hours in EST time zone
     */
    public static boolean isBetweenBusinessHoursInEST(LocalTime apptStartTime, LocalTime apptEndTime){
        //Convert argument time objects to Eastern Standard Time Zone
        apptStartTime = DateTimeMgmt.convertToLDTInZone(LocalDateTime.of(LocalDate.now(),apptStartTime),
                DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_EST).toLocalTime();
        apptEndTime = DateTimeMgmt.convertToLDTInZone(LocalDateTime.of(LocalDate.now(),apptStartTime),
                DateTimeMgmt.ZONE_SYS,DateTimeMgmt.ZONE_EST).toLocalTime();
        //Verify both start and end time are inside the business hours in EST
        boolean result =  (apptStartTime.isAfter(DateTimeMgmt.businessHoursStartInEST.minusMinutes(1))
                && apptStartTime.isBefore(DateTimeMgmt.businessHoursEndInEST.plusMinutes(1)))
                && (apptEndTime.isAfter(DateTimeMgmt.businessHoursStartInEST.minusMinutes(1))
                && apptEndTime.isBefore(DateTimeMgmt.businessHoursEndInEST.plusMinutes(1)));
        if(!result){
            ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                    LogEvent.AppLocation.DATETIMEMGMT,"Appointment outside business hours");
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"ApptCreationFailureTitle",
                    "OutsideESTBusinessHoursHeader", "OutsideESTBusinessHoursContent").showAndWait();
        }
        return result;
    }

    /**
     * Checks if time starts in the next 15 minutes
     * @param startTime LocalTime to check
     * @return boolean true if time is in the next 15 minutes
     */
    public static boolean isInNextFifteenMinutes(LocalTime startTime){
        LocalTime todayNow = LocalTime.now().minusSeconds(1);
        LocalTime todayNowPlusFifteen = LocalTime.now().plusMinutes(15).plusSeconds(1);
        return startTime.isAfter(todayNow) && startTime.isBefore(todayNowPlusFifteen);
    }

    /**
     * Checks is LocalDateTime is today
     * @param thisDateTime LocalDateTime to check
     * @return boolean true if LDT is today
     */
    public static boolean isToday(LocalDateTime thisDateTime){
        return thisDateTime.toLocalDate().equals(LocalDate.now());
    }


    /**
     * Checks if two Appointment times spans overlap
     * @param firstStartDateTIme  LocalDateTime start of first Appointment
     * @param firstEndDateTime LocalDateTime end of first Appointment
     * @param secondStartDateTIme LocalDateTime start of second Appointment
     * @param secondEndDateTime LocalDateTime end of second Appointment
     * @return boolean true if the Appointments overlap
     */
    public static boolean isBetweenDateTime(LocalDateTime firstStartDateTIme, LocalDateTime firstEndDateTime,
                                            LocalDateTime secondStartDateTIme, LocalDateTime secondEndDateTime){
        boolean isInsideFirstRange = (secondStartDateTIme.isAfter(firstStartDateTIme.minusMinutes(1))
                && secondStartDateTIme.isBefore(firstEndDateTime.plusMinutes(1)))
                || (secondEndDateTime.isAfter(firstStartDateTIme.minusMinutes(1))
                && secondEndDateTime.isBefore(firstEndDateTime.plusMinutes(1)));
        boolean isInsideSecondRange = (firstStartDateTIme.isAfter(secondStartDateTIme.minusMinutes(1))
                && firstStartDateTIme.isBefore(secondEndDateTime.plusMinutes(1)))
                || (firstEndDateTime.isAfter(secondStartDateTIme.minusMinutes(1))
                && firstEndDateTime.isBefore(secondEndDateTime.plusMinutes(1)));
        return (isInsideFirstRange || isInsideSecondRange);
    }

    /**
     * Verifies chronological ordering of start and end time
     * @param startTime LocalDateTime start of Appointment
     * @param endTime LocalDateTime end of Appointment
     * @return boolean true if start is before end
     */
    public static boolean isInProperOrderOfTime(LocalDateTime startTime, LocalDateTime endTime) {
        boolean isInOrder = startTime.isBefore(endTime);
        if(isInOrder){
            return true;
        }else{
            ApplicationEvent event = new ApplicationEvent(DataMgmt.getCurrentUser().getName(), LogEvent.EventType.APPLICATION,
                    LogEvent.AppLocation.DATETIMEMGMT,"Appointment times not in chronological order");
            Main.logger.log(event);
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"ApptCreationFailureTitle",
                    "StartAndEndOrderIncorrectHeader", "StartAndEndOrderIncorrectContent").showAndWait();
            return false;
        }
    }

    /**
     * Checks if Appointment has overlaps in time with another appointment for the same Customer
     * @param newAppt Appointment to check
     * @return boolean true if Appointment overlaps with another Appointment for the assigned Customer
     */
    public static boolean hasApptOverlaps(Appointment newAppt) {
        AtomicBoolean result = new AtomicBoolean(false);

        DataMgmt.getAllApptsList().forEach(appt -> {
           if((appt.getCustomerId() == newAppt.getCustomerId())){
               if (isBetweenDateTime(appt.getStart(), appt.getEnd(), newAppt.getStart(), newAppt.getEnd())){
                   result.set(true);
               }
           }
        });
        if(result.get()){
            AlertFactory.getNewDialogAlert(Alert.AlertType.ERROR,"ApptOverLapTitle","ApptOverLapHeader",
                    "ApptOverLapContent").showAndWait();
        }
        return result.get();
    }
}
