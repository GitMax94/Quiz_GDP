package com.uni.gdp.quiz_gdp;

public class TestService
{
    public static Question[] MakeTestQuiz(int numberQuestions, int numberAnswers)
    {
        Question[] r = new Question[numberQuestions];

        for (int i = 0; i < r.length; i++)
        {
			r[i] = new Question();
            r[i].question = "Lorem ipsum dolor sit amet Question " + i + "?";
            r[i].answers = new String[numberAnswers];
            for (int j = 0; j < r[i].answers.length; j++)
            {
                r[i].answers[j] = "TestAnswer " + i + " / " + j;
            }
        }
		return r;
    }

	public static Leaderboard[] MakeTestLeaderboard(int x)
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
}
