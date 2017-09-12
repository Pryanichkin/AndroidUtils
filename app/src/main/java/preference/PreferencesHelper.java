package preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created on 12.09.2017 in 16:46.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class PreferencesHelper {
    private SharedPreferences preferences;
    private static PreferencesHelper ourInstance;

    public static PreferencesHelper getInstance(Context context) {
        if (ourInstance == null) ourInstance = new PreferencesHelper(context);
        return ourInstance;
    }

    private PreferencesHelper(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

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

}