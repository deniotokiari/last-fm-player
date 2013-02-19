package by.deniotokiari.lastfmmusicplay;

import com.bugsense.trace.BugSenseHandler;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.api.VkAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.images.ImageLoader;
import by.deniotokiari.lastfmmusicplay.db.DBHelper;
import by.deniotokiari.lastfmmusicplay.fragment.main.MainPagerFragment;
import by.deniotokiari.lastfmmusicplay.playlist.PlaylistManager;

public class MainActivity extends FragmentActivity {

	private ProgressDialog mProgressDialog;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(MainActivity.this, "4634f20e");
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			return;
		}
		mHandler = new Handler();
		mProgressDialog = new ProgressDialog(MainActivity.this);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.content, new MainPagerFragment());
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);	
		MenuItem me = menu.add(0, 1, 0, "Preferences");
		me.setIntent(new Intent(this, PrefActivity.class));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			LastfmAuthHelper.logout();
			VkAuthHelper.logout();
			ImageLoader.getInstance().clearCache();
			Editor playlist = getSharedPreferences(PlaylistManager.PREF_NAME,
					Context.MODE_PRIVATE).edit();
			playlist.clear();
			playlist.commit();
			clearDatabase();
			startActivity(new Intent(getApplicationContext(),
					StartActivity.class));
			break;
		case R.id.menu_reload:
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setMessage(getResources().getString(
					R.string.processing));
			mProgressDialog.show();
			clearDatabase();
			break;
		case R.id.menu_share:
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, PlaylistManager.getInstance().getTrack());
			startActivity(Intent.createChooser(intent, "Share..."));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageLoader.getInstance().stopQueueThread();
	}

	private void clearDatabase() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ContentResolver resolver = getContentResolver();
				for (Uri uri : DBHelper.URI_CONTRACT) {
					resolver.delete(uri, null, null);
					resolver.notifyChange(uri, null);
				}
				mHandler.post(dismissProgressDialog);
			}

		}).start();
	}

	private Runnable dismissProgressDialog = new Runnable() {

		@Override
		public void run() {
			mProgressDialog.dismiss();
		}

	};

}
