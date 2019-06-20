package stp.cuonghq.upde.commons;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import static stp.cuonghq.upde.commons.Constants.SharePreferenceConstants.MODE;
import static stp.cuonghq.upde.commons.Constants.SharePreferenceConstants.NAME;

public class AppSharePreferences {

    private static SharedPreferences sInstance;

    public static SharedPreferences getInstance() {
        return getInstance(null);
    }

    public static SharedPreferences getInstance(Context context) {
        if (sInstance == null) {
            sInstance = context.getSharedPreferences(NAME, MODE);
        }
        return sInstance;
    }

    public static void removeFromSP(String...key) {
        if (getInstance() != null) {
            SharedPreferences.Editor editor = getInstance().edit();
            for (String string : key) {
                editor.remove(string);
            }
            editor.apply();
        }
    }

    public static <T> void saveToSP(String key, T t) {
        if (getInstance() != null) {
            Gson gson = new Gson();
            String json = gson.toJson(t);
            SharedPreferences.Editor editor = getInstance().edit();
            editor.putString(key, json);
            editor.apply();
        }
    }

    public static <T> T getFromSP(String key, Type typeOfT) {
        if (getInstance() != null) {
            String json = getInstance().getString(key, "");
            Gson gson = new Gson();
            return gson.fromJson(json, typeOfT);
        }
        return null;
    }

    public static void saveToSP(String key, String value) {
        if (getInstance() != null) {
            SharedPreferences.Editor editor = getInstance().edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public static String getStringFromSP(String key) {
        if (getInstance(AppContext.getInstance()) != null) {
            return getInstance().getString(key, "");
        }
        return null;
    }

    public static void saveToSP(String key, int value) {
        if (getInstance() != null) {
            SharedPreferences.Editor editor = getInstance().edit();
            editor.putInt(key, value);
            editor.apply();
        }
    }

    public static int getIntegerFromSP(String key) {
        if (getInstance() != null) {
            return getInstance().getInt(key, 0);
        }
        return 0;
    }
}
