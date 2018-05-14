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
	static ResultActivity resultAct;
	final static Handler hbHandler = new Handler();
	static Runnable heartbeat = new Runnable()
	{
		@Override
		public void run()
		{
			sendToServer("?func=heartbeat&userId=" + DataRepo.uuid, "checkOpponent", null, null, null, resultAct);
			Log.i("PHP", "heartbeat now");
			hbHandler.postDelayed(heartbeat, 10000);
		}
	};


	public static void sendToServer(final String phpParams, final String methodName, final MainMenuActivity mainMenu, final SelectquizActivity selectQuiz, final QuestionActivity question, final ResultActivity result)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					String webURL = "http://cbrell.de/bwi50205/172/op995204/QuizAuswertung.php" + phpParams;
					String TextParameter = URLEncoder.encode(webURL, "UTF-8");
					URL scriptURL = new URL(webURL);
					HttpURLConnection HttpURLVerbindung = (HttpURLConnection) scriptURL.openConnection();
					HttpURLVerbindung.setDoOutput(true);
					HttpURLVerbindung.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					HttpURLVerbindung.setFixedLengthStreamingMode(TextParameter.getBytes().length);

					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(HttpURLVerbindung.getOutputStream());
					outputStreamWriter.write(TextParameter);
					outputStreamWriter.flush();
					outputStreamWriter.close();

					InputStream answerInputStream = HttpURLVerbindung.getInputStream();

					BufferedReader br = new BufferedReader(new InputStreamReader(answerInputStream));

					final StringBuilder phpOutput = new StringBuilder();

					String text = br.readLine();
					while (text != null) {
						phpOutput.append(text + "\n");
						text = br.readLine();
					}

					final String phpOutput2 = phpOutput.toString();

					answerInputStream.close();
					HttpURLVerbindung.disconnect();

					Log.i("PHP Output", phpOutput.toString());

					if (!methodName.isEmpty())
					{
						if (mainMenu != null)
						{
							Method method = mainMenu.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(mainMenu, phpOutput.toString());
						}
						else if (selectQuiz != null)
						{
							Method method = selectQuiz.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(selectQuiz, phpOutput.toString());
						}
						else if (question != null)
						{
							Method method = question.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(question, phpOutput.toString());
						}
						else if (result != null)
						{
							Method method = result.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(result, phpOutput.toString());
						}
					}
				}
				catch (Exception ignored) {}

			}
		}).start();
	}
}
