package com.casinelli.Appointments.Helper;

import java.time.*;
import java.util.Locale;

public abstract class DateTimeMgmt {
    public static final Locale LOCALE_SYS = Locale.getDefault();
    //public static final Locale LOCALE_SYS = new Locale("fr", "CA");
    public static final ZoneId ZONE_SYS = ZoneId.systemDefault();
    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");

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


}
