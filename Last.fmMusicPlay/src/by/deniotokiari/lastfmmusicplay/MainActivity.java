package by.deniotokiari.lastfmmusicplay;


import com.bugsense.trace.BugSenseHandler;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.api.VkAuthHelper;
import by.deniotokiari.lastfmmusicplay.content.images.ImageCache;
import by.deniotokiari.lastfmmusicplay.content.images.ImageLoader;
import by.deniotokiari.lastfmmusicplay.db.DBHelper;
import by.deniotokiari.lastfmmusicplay.fragment.main.MainPagerFragment;

public class MainActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(MainActivity.this, "4634f20e");
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			return;
		}
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.content, new MainPagerFragment());
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			LastfmAuthHelper.logout();
			VkAuthHelper.logout();
			ImageLoader.getInstance().clearCache();
			// TODO database
			// TODO thread imp
			
			startActivity(new Intent(getApplicationContext(),
					StartActivity.class));
			break;
		case R.id.menu_reload:
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageLoader.getInstance().stopQueueThread();
	}

	synchronized private void ClearDatabase() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ContentResolver resolver = getContentResolver();
				for (Uri uri : DBHelper.URI_CONTRACT) {
					resolver.delete(uri, null, null);
					resolver.notifyChange(uri, null);
				}
			}
			
		});
	}
	
}
