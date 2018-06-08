package com.example.t2sadmin.sampleapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.t2sadmin.sampleapp.main.BaseActivity;


public class PreferenceHelper {
    public static int STRING_PREFERENCE = 1;
    public static int INT_PREFERENCE = 2;
    public static int BOOLEAN_PREFERENCE = 3;

    public static void saveValueToPreference(@NonNull Context context, int preference,
                                             String key, Object value) {
        SharedPreferences sharedPreference = context.getSharedPreferences(
                AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreference.edit();
        if (preference == STRING_PREFERENCE) {
            edit.putString(key, (String) value);
        } else if (preference == INT_PREFERENCE) {
            edit.putInt(key, (Integer) value);
        } else if (preference == BOOLEAN_PREFERENCE) {
            edit.putBoolean(key, (Boolean) value);
        }
        edit.apply();
    }

    @Nullable
    public static Object getValueFromPreference(@NonNull Context context,
                                                int preference, String key) {
        SharedPreferences sharedPreference = context.getSharedPreferences(
                AppConstants.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (preference == STRING_PREFERENCE) {
            return sharedPreference.getString(key, "");
        } else if (preference == INT_PREFERENCE) {
            return sharedPreference.getInt(key, 0);
        } else if (preference == BOOLEAN_PREFERENCE) {
            return sharedPreference.getBoolean(key, false);
        }
        return null;

    }

    @Nullable
    public static String getStringValue(@NonNull Context context, String Key) {
        return (String) PreferenceHelper.getValueFromPreference(context,
                PreferenceHelper.STRING_PREFERENCE, Key);
    }

    @NonNull
    public static Integer getIntValue(@NonNull Context context, String Key) {
        int mInt;
        try {
            mInt = (int) PreferenceHelper.getValueFromPreference(context,
                    PreferenceHelper.INT_PREFERENCE, Key);
        } catch (Exception e) {
            mInt = 0;
            ((BaseActivity) context).sysOut("" + e.getMessage());
        }
        return mInt;

    }

    public static boolean getBooleanValue(@NonNull Context context, String Key) {

        return (boolean) PreferenceHelper.getValueFromPreference(context,
                PreferenceHelper.BOOLEAN_PREFERENCE, Key);
    }
}
