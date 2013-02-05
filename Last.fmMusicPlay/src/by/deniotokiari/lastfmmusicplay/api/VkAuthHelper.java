package by.deniotokiari.lastfmmusicplay.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import by.deniotokiari.lastfmmusicplay.ContextHolder;

public class VkAuthHelper {

	private static final String KEY_ACCESS_TOKEN = "access_token";
	private static final Context CONTEXT = ContextHolder.getInstance()
			.getContext();
	public static final String VK_SHARED_PREFS = "vk_session";
	public static final String APP_ID = "3336066";
	public static final String SCOPE = "audio,offline";
	public static final String REDIRECT_URL = "http://oauth.vk.com/blank.html";
	public static final String AUTORIZATION_URL = "http://oauth.vk.com/authorize?client_id="
			+ APP_ID
			+ "&scope="
			+ SCOPE
			+ "&redirect_uri="
			+ REDIRECT_URL
			+ "&display=touch&response_type=token";

	public static void saveSession(String session) {
		Editor editor = CONTEXT.getSharedPreferences(VK_SHARED_PREFS,
				Context.MODE_PRIVATE).edit();
		editor.putString("session", session);
		editor.commit();
	}

	@SuppressWarnings("unused")
	public static void logout() {
		try {
			CookieSyncManager cookieSyncManager = CookieSyncManager
					.createInstance(CONTEXT);
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeAllCookie();
		} catch (Exception e) {

		}

		Editor editor = CONTEXT.getSharedPreferences(VK_SHARED_PREFS,
				Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
	}

	public static String getSession() {
		SharedPreferences preferences = CONTEXT.getSharedPreferences(
				VK_SHARED_PREFS, Context.MODE_PRIVATE);
		return preferences.getString("session", null);
	}

	public static boolean isRedirect(String url) {
		return url.startsWith(REDIRECT_URL);
	}

	public static boolean processUrl(String url) {
		if (isRedirect(url)) {
			Uri parseUrl = Uri.parse(url.replace("#", "?"));
			final String session = parseUrl.getQueryParameter(KEY_ACCESS_TOKEN);
			if (session == null) {
				return false;
			}
			saveSession(session);
			return true;
		}
		return false;
	}

}
