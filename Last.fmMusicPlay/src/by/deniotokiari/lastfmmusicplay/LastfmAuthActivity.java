package by.deniotokiari.lastfmmusicplay;

import by.deniotokiari.lastfmmusicplay.api.LastFmAPI;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.Callback;
import by.deniotokiari.lastfmmusicplay.content.json.CommonJson;
import by.deniotokiari.lastfmmusicplay.http.RequestManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class LastfmAuthActivity extends Activity {

	public static final String KEY_TOKEN = "token";
	public static final String KEY_SESSION = "session";
	public static final String KEY_SESSION_KEY = "key";
	public static final String KEY_NAME = "name";
	private WebView mWebView;
	private ProgressBar mProgressBar;
	private String mToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		mWebView = (WebView) findViewById(R.id.webView);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mProgressBar.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
				if (url.contains(LastfmAuthHelper.URL_GRANT_ACCESS)) {
					mProgressBar.setVisibility(View.VISIBLE);
					mWebView.setVisibility(View.GONE);
					RequestManager.getInstance().get(new Callback<Object>() {

						@Override
						public void onSuccess(Object t, Object... objects) {
							CommonJson session = new CommonJson((String) t,
									KEY_SESSION);
							LastfmAuthHelper.saveSession(session
									.getString(KEY_SESSION_KEY));
							LastfmAuthHelper.saveUserName(session
									.getString(KEY_NAME));
							startActivity(new Intent(getApplicationContext(),
									VkAuthActivity.class));
							finish();
						}

						@Override
						public void onError(Throwable e, Object... objects) {

						}

					}, LastFmAPI.authGetSession(mToken));
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mWebView.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.VISIBLE);
			}

		});
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		RequestManager.getInstance().get(new Callback<Object>() {

			@Override
			public void onSuccess(Object t, Object... objects) {
				mToken = (new CommonJson((String) t)).getString(KEY_TOKEN);
				mWebView.loadUrl(LastFmAPI.grantAccessUrl(mToken));
			}

			@Override
			public void onError(Throwable e, Object... objects) {

			}

		}, LastFmAPI.authGetToken());
	}

}
