package nl.gingerik.headsdown;

import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {

	public final static String PREF_ENABLE = "heads_up_notifications_enabled";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
		setTitle(R.string.settings_activity_title);
	}
}
