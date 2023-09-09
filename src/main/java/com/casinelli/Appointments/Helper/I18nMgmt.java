package com.casinelli.Appointments.Helper;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class to provide internationalization of text based on user locale on system using user resource path
 */
public abstract class I18nMgmt {

    private static ResourceBundle rb;

    /**
     * Initializes ResourceBundle for any java program
     */
    public static void setup(String packagePath){
        rb = ResourceBundle.getBundle(packagePath, Locale.getDefault());
    }

    /**
     * Translates the text using key-value pairs
     * @param key String key of property to translate
     * @return String value of translated text
     */
    public static String translate(String key){
        return rb.getString(key);
    }
}
