package com.uni.gdp.quiz_gdp;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class PHPService
{
	//send a request to the php-script with params and invokes a method in an activity
	static void sendToServer(final String phpParams, final String methodName, final MainMenuActivity mainMenu, final QuestionActivity question, final ResultActivity result)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					//sends request
					String webURL = "http://cbrell.de/bwi50207/181/op995204/QuizService.php" + phpParams;
					String textParameter = URLEncoder.encode(webURL, "UTF-8");
					URL scriptURL = new URL(webURL);
					HttpURLConnection httpURLConnection = (HttpURLConnection) scriptURL.openConnection();
					httpURLConnection.setDoOutput(true);
					httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					httpURLConnection.setFixedLengthStreamingMode(textParameter.getBytes().length);

					//gets answer
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
					outputStreamWriter.write(textParameter);
					outputStreamWriter.flush();
					outputStreamWriter.close();

					//converts php output to string
					InputStream answerInputStream = httpURLConnection.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(answerInputStream));
					final StringBuilder phpOutput = new StringBuilder();
					String text = br.readLine();
					while (text != null) {
						phpOutput.append(text).append("\n");
						text = br.readLine();
					}
					final String phpOutput2 = phpOutput.toString();
					Log.i("PHP Output", phpOutput2);

					//closes connection
					answerInputStream.close();
					httpURLConnection.disconnect();

					//invokes methods in appropriate class, add php output as argument
					if (!methodName.isEmpty())
					{
						if (mainMenu != null)
						{
							Method method = mainMenu.getClass().getMethod(methodName, (new Class[1])[0] = String.class);
							method.invoke(mainMenu, phpOutput.toString());
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
