package by.deniotokiari.lastfmmusicplay.preferences;

import by.deniotokiari.lastfmmusicplay.ContextHolder;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesHelper {

	private static PreferencesHelper instance;
	private static final int MODE = Context.MODE_PRIVATE;

	public static PreferencesHelper getInstance() {
		if (instance == null) {
			instance = new PreferencesHelper();
		}
		return instance;
	}

	public void putString(String name, String key, String value) {
		Editor editor = ContextHolder.getInstance().getContext()
				.getSharedPreferences(name, MODE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String name, String key) {
		SharedPreferences preferences = ContextHolder.getInstance()
				.getContext().getSharedPreferences(name, MODE);
		return preferences.getString(key, null);
	}

	public void putInt(String name, String key, int value) {
		Editor editor = ContextHolder.getInstance().getContext()
				.getSharedPreferences(name, MODE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public int getInt(String name, String key) {
		SharedPreferences preferences = ContextHolder.getInstance()
				.getContext().getSharedPreferences(name, MODE);
		return preferences.getInt(key, -1);
	}

	public void putBoolean(String name, String key, boolean value) {
		Editor editor = ContextHolder.getInstance().getContext()
				.getSharedPreferences(name, MODE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String name, String key) {
		SharedPreferences preferences = ContextHolder.getInstance()
				.getContext().getSharedPreferences(name, MODE);
		return preferences.getBoolean(key, false);
	}

}
