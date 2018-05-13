package com.uni.gdp.quiz_gdp;

import android.os.Debug;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PHPService
{
	final static Handler hbHandler = new Handler();
	static Runnable heartbeat = new Runnable()
	{
		@Override
		public void run()
		{
			sendToServer("?anwendung=S&Nutzer_ID=" + DataRepo.uuid + "&Name=" + DataRepo.name, "", null, null, null);
			Log.i("PHP", "heartbeat now");
			hbHandler.postDelayed(heartbeat, 10000);
		}
	};


	public static void sendToServer(final String phpParams, final String methodName, final MainMenuActivity mainMenu, final QuestionActivity question, final SelectPlayerActivity selectPlayer)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					String webURL = "http://cbrell.de/bwi50205/172/op995204/QuizAuswertung.php" + phpParams;
					String TextParameter = URLEncoder.encode(webURL, "UTF-8");
					Log.i("PLAYERS", "_1");
					URL scriptURL = new URL(webURL);
					HttpURLConnection HttpURLVerbindung = (HttpURLConnection) scriptURL.openConnection();
					HttpURLVerbindung.setDoOutput(true);
					HttpURLVerbindung.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					HttpURLVerbindung.setFixedLengthStreamingMode(TextParameter.getBytes().length);

					Log.i("PLAYERS", "_2");

					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(HttpURLVerbindung.getOutputStream());
					outputStreamWriter.write(TextParameter);
					outputStreamWriter.flush();
					outputStreamWriter.close();

					InputStream answerInputStream = HttpURLVerbindung.getInputStream();

					Log.i("PLAYERS", "_3");

					BufferedReader br = new BufferedReader(new InputStreamReader(answerInputStream));

					final StringBuilder phpOutput = new StringBuilder();

					Log.i("PLAYERS", "_4");

					String text = br.readLine();
					while (text != null) {
						phpOutput.append(text + "\n");
						text = br.readLine();
					}

					final String phpOutput2 = phpOutput.toString();

					answerInputStream.close();
					HttpURLVerbindung.disconnect();

					Log.i("PLAYERS", "_5");

					if (!methodName.isEmpty())
					{
						Log.i("PLAYERS", "_6");
						if (mainMenu != null) {
							Method method = mainMenu.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(mainMenu, phpOutput.toString());
						} else if (question != null) {
							Method method = question.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(question, phpOutput.toString());
						} else if (selectPlayer != null) {
							Log.i("PLAYERS", "_6");
							Method method = selectPlayer.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(selectPlayer, phpOutput.toString());
						}
					}
				}
				catch (Exception ignored) {}

			}
		}).start();
	}
}
