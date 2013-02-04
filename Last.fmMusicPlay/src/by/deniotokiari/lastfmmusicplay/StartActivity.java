package by.deniotokiari.lastfmmusicplay;

import by.deniotokiari.lastfmmusicplay.api.LastfmAuthHelper;
import by.deniotokiari.lastfmmusicplay.api.VkAuthHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity implements OnClickListener,
		OnDismissListener, OnCancelListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		if (LastfmAuthHelper.getSession() != null
				&& VkAuthHelper.getSession() != null) {
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			finish();
		} else {
			AlertDialog builder = new AlertDialog.Builder(this)
					.setNegativeButton(
							getResources().getString(R.string.btn_negative),
							this)
					.setPositiveButton(
							getResources().getString(R.string.btn_positive),
							this)
					.setMessage(getResources().getString(R.string.dlg_msg))
					.create();
			builder.setOnCancelListener(this);
			builder.setOnDismissListener(this);
			builder.show();
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case Dialog.BUTTON_POSITIVE:
			if (LastfmAuthHelper.getSession() == null) {
				startActivity(new Intent(getApplicationContext(),
						LastfmAuthActivity.class));
				finish();
			} else if (VkAuthHelper.getSession() == null) {
				startActivity(new Intent(getApplicationContext(),
						VkAuthActivity.class));
				finish();
			}
		}
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		finish();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		finish();
	}

}
