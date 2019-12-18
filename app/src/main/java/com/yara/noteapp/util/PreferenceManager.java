package com.yara.noteapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_INTRO = "PREF_INTRO";

    /*-------------------->Preference<--------------------*/
    private static SharedPreferences getPreferences(Context context) {
        return android.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    /*-------------------->Intro<--------------------*/
    public static void setIntroShow(Context context) {
//        getEditor(context).putBoolean(PREF_INTRO, true).apply();
    }

    public static boolean isIntroShow(Context context) {
        return getPreferences(context).getBoolean(PREF_INTRO, false);
    }

}
