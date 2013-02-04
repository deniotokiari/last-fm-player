package by.deniotokiari.lastfmmusicplay.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import by.deniotokiari.lastfmmusicplay.ContextHolder;

public class LastfmAuthHelper {

	private static final Context CONTEXT = ContextHolder.getInstance()
			.getContext();
	public static final String LASTFM_SHARED_PREFS = "lastfm_session";
	public static final String URL_GRANT_ACCESS = "grantaccess";

	public static void saveSession(String session) {
		Editor editor = CONTEXT.getSharedPreferences(LASTFM_SHARED_PREFS,
				Context.MODE_PRIVATE).edit();
		editor.putString("session", session);
		editor.commit();
	}

	public static void saveUserName(String name) {
		Editor editor = CONTEXT.getSharedPreferences(LASTFM_SHARED_PREFS,
				Context.MODE_PRIVATE).edit();
		editor.putString("name", name);
		editor.commit();
	}

	public static void logout() {
		Editor editor = CONTEXT.getSharedPreferences(LASTFM_SHARED_PREFS,
				Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

	public static String getSession() {
		SharedPreferences preferences = CONTEXT.getSharedPreferences(
				LASTFM_SHARED_PREFS, Context.MODE_PRIVATE);
		return preferences.getString("session", null);
	}

	public static String getUserName() {
		SharedPreferences preferences = CONTEXT.getSharedPreferences(
				LASTFM_SHARED_PREFS, Context.MODE_PRIVATE);
		return preferences.getString("name", null);
	}

}