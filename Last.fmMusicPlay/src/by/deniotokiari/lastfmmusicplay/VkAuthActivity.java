package by.deniotokiari.lastfmmusicplay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import by.deniotokiari.lastfmmusicplay.api.VkAuthHelper;

public class VkAuthActivity extends Activity {

	private WebView mWebView;
	private ProgressBar mProgressBar;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				mProgressBar.setVisibility(View.GONE);
				mWebView.setVisibility(View.VISIBLE);
				if (VkAuthHelper.processUrl(url)) {
					mWebView.setVisibility(View.GONE);
					mProgressBar.setVisibility(View.VISIBLE);
					startActivity(new Intent(getApplicationContext(),
							StartActivity.class));
					finish();
				}
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mWebView.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.VISIBLE);
			}

		});
		mWebView.loadUrl(VkAuthHelper.AUTORIZATION_URL);
	}

}
