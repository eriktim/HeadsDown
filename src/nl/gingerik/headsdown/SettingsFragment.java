package nl.gingerik.headsdown;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	private Logger mLog;
	private SharedPreferences mSharedPref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLog = new Logger(SettingsFragment.class.getSimpleName());
		mLog.v("onCreate");

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		mSharedPref = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		onSharedPreferenceChanged(mSharedPref, SettingsActivity.PREF_ENABLE);

		loadSettings();
	}

	@Override
	public void onResume() {
		super.onResume();
		mLog.v("onResume");
		mSharedPref.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		mLog.v("onPause");
		if (mSharedPref != null) {
			mSharedPref.unregisterOnSharedPreferenceChangeListener(this);
		}
		super.onPause();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

		switch (key) {
		case SettingsActivity.PREF_ENABLE:
			boolean flag = sharedPreferences.getBoolean(key, getResources()
					.getBoolean(R.bool.heads_up_notifications_enabled_default));
			setHeadsUpNotificationsEnabled(flag);
			break;
		}
	}

	private void setHeadsUpNotificationsEnabled(boolean enable) {
		String command = "settings put global heads_up_notifications_enabled "
				+ (enable ? "1" : "0");
		String result = exec(command);
		if (result == null) {
			mLog.e("Failed: " + command);
		} else {
			mLog.v(command);
		}
	}

	private void loadSettings() {
		mLog.v("loadSettings");
		String result = exec("settings get global heads_up_notifications_enabled");
		boolean flag = "1".equals(result);
		Editor editor = mSharedPref.edit();
		editor.putBoolean(SettingsActivity.PREF_ENABLE, flag);
		editor.apply();
	}

	private String exec(String command) {
		String result = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(command);
			process.waitFor();
			if (process.exitValue() == 0) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				StringBuilder output = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					output.append(line + "\n");
				}
				result = output.toString();
				bufferedReader.close();
			}
		} catch (Exception e) {
			mLog.e("Failed executing command: " + command);
			e.printStackTrace();
		}
		return result;
	}
}