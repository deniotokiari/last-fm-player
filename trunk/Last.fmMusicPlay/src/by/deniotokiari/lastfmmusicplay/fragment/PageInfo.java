package by.deniotokiari.lastfmmusicplay.fragment;

import android.os.Bundle;

public class PageInfo {

	private Class<?> clss;
	private Bundle args;

	public PageInfo(Class<?> clss, Bundle args) {
		this.clss = clss;
		this.args = args;
	}

	public Class<?> getClss() {
		return clss;
	}

	public Bundle getArgs() {
		return args;
	}

}