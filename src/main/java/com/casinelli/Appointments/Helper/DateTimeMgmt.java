package com.casinelli.Appointments.Helper;

import java.time.*;
import java.util.Locale;

public class DateTimeMgmt {
    public static final Locale LOCALE_SYS = Locale.getDefault();
    public static final ZoneId ZONE_SYS = ZoneId.systemDefault();
    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");

    public static ZonedDateTime toUTC(ZonedDateTime zdt){
        ZonedDateTime zdtUTC = ZonedDateTime.ofInstant(zdt.toInstant(), ZONE_UTC);
        return zdtUTC;
    }


}
