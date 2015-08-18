package nl.gingerik.headsdown;

import android.util.Log;

public class Logger {

	private String mTag;

	public Logger(String tag) {
		mTag = tag;
	}

	public void v(String message) {
		Log.v(mTag, message);
	}

	public void d(String message) {
		Log.d(mTag, message);
	}

	public void i(String message) {
		Log.i(mTag, message);
	}

	public void e(String message) {
		Log.e(mTag, message);
	}
}