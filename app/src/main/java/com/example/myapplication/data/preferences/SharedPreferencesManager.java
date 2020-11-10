package com.example.myapplication.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public final class SharedPreferencesManager {

    private static final String SETTINGS_FILE_NAME = "app_settings";

    public static final String REFRESH_TOKEN = "refresh_token_key";
    public static final String ACCESS_TOKEN = "access_token_key";

    private SharedPreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static String getAccessToken(Context context) {
        return getSharedPreferences(context).getString(ACCESS_TOKEN , null);
    }

    public static void setAccessToken(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ACCESS_TOKEN , newValue);
        editor.apply();
    }

    public static String getRefreshToken(Context context) {
        return getSharedPreferences(context).getString(REFRESH_TOKEN , null);
    }

    public static void setRefreshToken(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(REFRESH_TOKEN , newValue);
        editor.commit();
    }
}
