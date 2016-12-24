package com.androprogrammer.tutorials.util;


import com.androprogrammer.tutorials.MainController;

/**
 * Application level preference work.
 * Created by Wasim on 23-11-2015.
 */
public class UserPreferenceManager {

    public static void preferencePutInteger(String key, int value) {
        MainController.getApplicationPreferenceEditor().putInt(key, value);
        MainController.getApplicationPreferenceEditor().commit();
    }

    public static int preferenceGetInteger(String key, int defaultValue) {
        return MainController.getApplicationPreference().getInt(key, defaultValue);
    }

    public static void preferencePutBoolean(String key, boolean value) {
        MainController.getApplicationPreferenceEditor().putBoolean(key, value);
        MainController.getApplicationPreferenceEditor().commit();
    }

    public static boolean preferenceGetBoolean(String key, boolean defaultValue) {
        return MainController.getApplicationPreference().getBoolean(key, defaultValue);
    }

    public static void preferencePutString(String key, String value) {
        MainController.getApplicationPreferenceEditor().putString(key, value);
        MainController.getApplicationPreferenceEditor().commit();
    }

    public static String preferenceGetString(String key, String defaultValue) {
        return MainController.getApplicationPreference().getString(key, defaultValue);
    }

    public static void preferencePutLong(String key, long value) {
        MainController.getApplicationPreferenceEditor().putLong(key, value);
        MainController.getApplicationPreferenceEditor().commit();
    }

    public static long preferenceGetLong(String key, long defaultValue) {
        return MainController.getApplicationPreference().getLong(key, defaultValue);
    }

    public static void preferenceRemoveKey(String key) {
        MainController.getApplicationPreferenceEditor().remove(key);
        MainController.getApplicationPreferenceEditor().commit();
    }

    public static void clearPreference() {
        MainController.getApplicationPreferenceEditor().clear();
        MainController.getApplicationPreferenceEditor().commit();
    }
}
