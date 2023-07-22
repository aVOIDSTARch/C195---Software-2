package com.casinelli.Appointments.Helper;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class I18nMgmt {
    private static Locale locale;
    private static String packagePath;
    private static ResourceBundle rb;

    public static void setup(){
        locale = DateTimeMgmt.LOCALE_SYS;
        packagePath = "com/casinelli/Appointments/Appts";
        rb = ResourceBundle.getBundle(packagePath, locale);;
    }
    public static String translate(String key){
        String str = rb.getString(key);
        return str;
    }

}
