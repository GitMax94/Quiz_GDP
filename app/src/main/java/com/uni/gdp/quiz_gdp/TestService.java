package com.uni.gdp.quiz_gdp;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

class TestService
{
	static Quiz[] MakeTestQuizzes(int numberQizzes)
	{
		Quiz[] r = new Quiz[numberQizzes];
		for (int i = 0; i < r.length; i++)
		{
			r[i] = new Quiz();
			r[i].name = "TEST_quiz " + i;
			r[i].questions = MakeTestQuiz(12, r[i].name);
		}
		return r;
	}



    static Question[] MakeTestQuiz(int numberQuestions, String testName)
    {
        Question[] r = new Question[numberQuestions];

        for (int i = 0; i < r.length; i++)
        {
			r[i] = new Question();
            r[i].question = testName + ": Lorem ipsum dolor sit amet Question " + i + "?";
            r[i].answers = new String[4];
            for (int j = 0; j < r[i].answers.length; j++)
            {
                r[i].answers[j] = testName + ": TestAnswer " + i + " / " + j;
            }
        }
		return r;
    }

	static Leaderboard[] MakeTestLeaderboard(int x)
	{
		Leaderboard[] r = new Leaderboard[x];
		for (int i = 0; i < r.length; i++)
		{
			r[i] = new Leaderboard();
			r[i].name = "EntryName " + i;
			r[i].points = i;
		}
		return r;
	}

	static String TestPHP()
	{
		StringBuilder r = new StringBuilder();
		URL url;
		HttpURLConnection urlConnection = null;
		try
		{
			url = new URL("http://cbrell.de/bwi50207/181/ka997653/testPHP.php?test=12345");

			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = urlConnection.getInputStream();

			InputStreamReader isw = new InputStreamReader(in);

			int data = isw.read();
			while (data != -1) {
				r.append((char) data);
				data = isw.read();
			}
		}
		catch (Exception e)
		{
			r.append("ERROR: ").append(e.getMessage());
		}
		finally
		{
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return "PHP: " + r.toString();
	}
}
