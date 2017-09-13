package com.pbteamstudio.androidhelpers.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Singleton Helper for preferences (put and get)
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class PreferencesHelper {
    private SharedPreferences preferences;
    private static PreferencesHelper ourInstance;

    /**
     * Static getter instance of this class
     * @param context - current {@link Context}
     * @return instance of {@link PreferencesHelper}
     */
    public static PreferencesHelper getInstance(Context context) {
        if (ourInstance == null) ourInstance = new PreferencesHelper(context);
        return ourInstance;
    }

    /**
     * Private constructor for {@link PreferencesHelper}
     * @param context - current {@link Context}
     */
    private PreferencesHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Putters for variable values
     * @param key - unique String identifier
     * @param value - value to put in
     */

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public void putStringSet(String key, Set<String> value) {
        preferences.edit().putStringSet(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * Getters for variable values
     * @param key - unique String identifier
     * @return value, that responds to given key
     * @throws ClassCastException - Throws  if there is a preference with this name that is not
     * a suggested class
     */

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, new HashSet<String>());
    }

    public Set<String> getStringSet(String key, HashSet<String> defValues) {
        return preferences.getStringSet(key, defValues);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }
}