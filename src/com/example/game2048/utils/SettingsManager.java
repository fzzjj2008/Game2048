package com.example.game2048.utils;

import com.example.game2048.Global;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsManager {
	
	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

    public static int getInteger(Context context, String key) {
        String sval = getString(context, key);
        if (sval == null) {
            return 0;
        } else {
            return Integer.parseInt(sval);
        }
    }
	
	public static String getString(Context context, String key) {
		String defaultVal = Global.defaultMap.get(key);
		return getPreferences(context).getString(key, defaultVal);
	}
	
	public static void putString(Context context, String key, int val) {
		String sval = String.valueOf(val);
		putString(context, key, sval);
	}
	
	public static void putString(Context context, String key, String val) {
		getPreferences(context).edit().putString(key, val).commit();
	}
	
	public static void initDefaultMap(Context context) {
	    Global.defaultMap.put(Global.PREF_STYLE, String.valueOf(Global.defaultStyle));
        Global.defaultMap.put(Global.PREF_CELL_NUM, String.valueOf(Global.defaultCellNum));
		Global.defaultMap.put(Global.PREF_SCORE_4X4, String.valueOf(Global.defaultScore));
        Global.defaultMap.put(Global.PREF_BEST_4X4, String.valueOf(Global.defaultBest));
        Global.defaultMap.put(Global.PREF_LAST_SCORE_4X4, String.valueOf(Global.defaultLastScore));
        Global.defaultMap.put(Global.PREF_LAST_BEST_4X4, String.valueOf(Global.defaultLastBest));
        Global.defaultMap.put(Global.PREF_SCORE_5X5, String.valueOf(Global.defaultScore));
        Global.defaultMap.put(Global.PREF_BEST_5X5, String.valueOf(Global.defaultBest));
        Global.defaultMap.put(Global.PREF_LAST_SCORE_5X5, String.valueOf(Global.defaultLastScore));
        Global.defaultMap.put(Global.PREF_LAST_BEST_5X5, String.valueOf(Global.defaultLastBest));
	}
	
	public static void removeAll(Context context) {
		getPreferences(context).edit().clear().commit();
	}
	
}
