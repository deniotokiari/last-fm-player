package by.deniotokiari.lastfmmusicplay;

import android.app.Application;

public class ContextApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ContextHolder.getInstance().setContext(this);
	}

}
