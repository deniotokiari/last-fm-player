package by.deniotokiari.lastfmmusicplay.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import by.deniotokiari.lastfmmusicplay.ContextHolder;
import by.deniotokiari.lastfmmusicplay.MainActivity;
import by.deniotokiari.lastfmmusicplay.R;

public class MusicNotification {

	private static MusicNotification instance;
	private static Context CONTEXT = ContextHolder.getInstance().getContext();
	private NotificationManager manager;
	private static final int ID = 4242;

	private MusicNotification() {
		manager = (NotificationManager) CONTEXT
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public static MusicNotification getInstance() {
		if (instance == null) {
			instance = new MusicNotification();
		}
		return instance;
	}

	public int createNotification(String message) {
		Intent intent = new Intent(CONTEXT, MainActivity.class);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				CONTEXT)
				.setSmallIcon(R.drawable.ic_launcher)
				.setAutoCancel(false)
				.setTicker(message)
				.setContentText(message)
				.setContentIntent(
						PendingIntent.getActivity(CONTEXT, 0, intent,
								PendingIntent.FLAG_CANCEL_CURRENT))
				.setWhen(System.currentTimeMillis())
				.setContentTitle(
						CONTEXT.getResources().getString(R.string.app_name))
				.setOngoing(true);
		Notification notification = builder.build();
		manager.notify(ID, notification);
		return ID;
	}

	public void destroyNotification() {
		manager.cancel(ID);
	}

}
