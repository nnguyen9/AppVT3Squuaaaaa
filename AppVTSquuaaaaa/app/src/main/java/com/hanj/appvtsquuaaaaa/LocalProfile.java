package com.hanj.appvtsquuaaaaa;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nam on 2/27/2016.
 */
public class LocalProfile {
    private static Context context;
    private static SharedPreferences settings;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static String nameFirst = "";
    private static String nameLast = "";
    private static String phone = "";
    private static List<User> otherUsers = null;

    private LocalProfile() {
        // Empty
    }

    public static void initialize(Context context) {
        LocalProfile.context = context;
        loadSettings();
    }

    public static String getNameFirst() {
        return nameFirst;
    }

    public static void setNameFirst(String nameFirst) {
        LocalProfile.nameFirst = nameFirst;
    }

    public static String getNameLast() {
        return nameLast;
    }

    public static void setNameLast(String nameLast) {
        LocalProfile.nameLast = nameLast;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        LocalProfile.phone = phone;
    }

    public static List<User> getOtherUsers() {
        return otherUsers;
    }

    public static void setOtherUsers(List<User> otherUsers) {
        LocalProfile.otherUsers = otherUsers;
    }

    public static void loadSettings() {
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        LocalProfile.nameFirst = settings.getString("first", "");
        LocalProfile.nameLast = settings.getString("last", "");
        LocalProfile.phone = settings.getString("phone", "");
    }

    public static void saveSettings() {
        SharedPreferences.Editor settingsEditor = settings.edit();

        settingsEditor.putString("first", nameFirst);
        settingsEditor.putString("last", nameLast);
        settingsEditor.putString("phone", phone);

        settingsEditor.apply();
    }
}
