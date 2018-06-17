package com.okayama.shop.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceHelper {

    private static final String KEY_IS_AUTHORIZED = "IS_AUTHORIZED";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_PHONE = "PHONE";

    private SharedPreferences preferences;

    public PreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setAuthorized(boolean isAuthorized) {
            putBoolean(KEY_IS_AUTHORIZED, isAuthorized);
    }

    public boolean isAuthorized() {
        return preferences.getBoolean(KEY_IS_AUTHORIZED, false);
    }

    public void setEmail(String email) {
        if (!TextUtils.isEmpty(email)) {
            putString(KEY_EMAIL, email);
        } else {
            remove(KEY_EMAIL);
        }
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    public void setPhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            putString(KEY_PHONE, phone);
        } else {
            remove(KEY_PHONE);
        }
    }

    public String getPhone() {
        return preferences.getString(KEY_PHONE, null);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
