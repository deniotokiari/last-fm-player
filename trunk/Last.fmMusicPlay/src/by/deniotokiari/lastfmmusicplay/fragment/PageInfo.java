package by.deniotokiari.lastfmmusicplay.fragment;

import android.os.Bundle;

public class PageInfo {

	Class<?> cls;
	Bundle bundle;

	public PageInfo(Class<?> cls, Bundle bundle) {
		this.cls = cls;
		this.bundle = bundle;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public Class<?> getCls() {
		return cls;
	}

}
