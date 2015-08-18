package nl.gingerik.headsdown;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import android.os.AsyncTask;

public class SUProcess extends AsyncTask<String, Void, String> {

	private Logger mLog;
	private DataOutputStream mStdin;
	private BufferedReader mStdout;
	private String mResult;
	private Runtime mRuntime;
	private Process mProcess;

	protected void onPreExecute() {
		mLog = new Logger(SUProcess.class.getSimpleName());
		mLog.v("onPreExecute");

		mResult = null;
		mRuntime = Runtime.getRuntime();
		try {
			mProcess = mRuntime.exec("su");
			mStdin = new DataOutputStream(mProcess.getOutputStream());
			mStdout = new BufferedReader(new InputStreamReader(
					mProcess.getInputStream()));
		} catch (Exception e) {
			mLog.e("Failed obtaining superuser access");
		}
	}

	protected String doInBackground(String... commands) {
		mLog.v("doInBackground");

		int count = commands.length;
		try {
			for (int i = 0; i < count; i++) {
				String command = commands[i];
				mStdin.writeBytes(command + "\n");
				if (isCancelled()) {
					break;
				}
			}
			mStdin.writeBytes("exit\n");
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = mStdout.readLine()) != null) {
				output.append(line + "\n");
				if (isCancelled()) {
					break;
				}
			}
			mStdin.close();
			mStdout.close();
			mProcess.waitFor();
			if (mProcess.exitValue() == 0) {
				mResult = output.toString();
			}
		} catch (Exception e) {
			mLog.e("Failed executing command(s)");
			e.printStackTrace();
		}
		return mResult;
	}
}
