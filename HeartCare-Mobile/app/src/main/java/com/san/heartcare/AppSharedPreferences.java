package com.san.heartcare;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {

    public static final String LOGGED_IN_PREF = "is_user_logged_in";

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedStatus(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static boolean userIsLoggedIn(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }
}
