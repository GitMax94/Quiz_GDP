package com.uni.gdp.quiz_gdp;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Max on 2018-05-11.
 */

public class PHPService
{

	public static void sendToServer(final Object object, final Method method)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					String webURL = "http://cbrell.de/bwi50205/172/op995204/QuizAuswertung.php";
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
					while (text != null)
					{
						phpOutput.append(text + "\n");
						text = br.readLine();
					}

					final String phpOutput2 = phpOutput.toString();

					answerInputStream.close();
					HttpURLVerbindung.disconnect();

					method.invoke(object, phpOutput.toString());


					/*
					MainMenuActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							et_name.setText("PHP: " + phpOutput2);
							Toast.makeText(context, phpOutput2.split("<br>")[phpOutput2.split("<br>").length-1], Toast.LENGTH_LONG).show();
						}
					});*/


				}
				catch (Exception e)
				{
					//Toast.makeText(context, "Fuck PHP", Toast.LENGTH_LONG).show();
				}

			}
		}).start();
	}
}
