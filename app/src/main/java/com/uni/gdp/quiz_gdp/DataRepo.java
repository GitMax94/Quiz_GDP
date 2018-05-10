package com.uni.gdp.quiz_gdp;

import android.content.SharedPreferences;

class DataRepo
{
    static String name;
	static int currentQuiz;
	static int currentQuestion;
    static int currentPoints;
	static SharedPreferences localData;

	static Quiz[] quizzes;
    static Leaderboard[] leaderboard;

    static String[] leaderboardToList()
	{
		String[] r = new String[leaderboard.length+1];
		for (int i = 0; i < r.length-1; i++)
		{
			r[i] = (i+1) + ". " + (leaderboard[i].name.equals(name) ? ">> " : "") + leaderboard[i].name + " (" + leaderboard[i].points + ")";
		}
		r[r.length-1] = "Ihr Highscore: " + 12;
		return r;
	}

	static String[] quizzesToList()
	{
		String[] r = new String[quizzes.length];
		for (int i = 0; i < r.length; i++)
		{
			r[i] = quizzes[i].name + " (" + quizzes[i].bestScore + " / " + quizzes[i].questions.length + " Fragen)";
		}
		return r;
	}

	static void setName(String newName)
	{
		DataRepo.name = newName;
		SharedPreferences.Editor editor = localData.edit();
		editor.putString("name", name);
		editor.apply();
	}
}

class Quiz
{
	String name;
	int bestScore;
	Question[] questions;
}

class Question
{
    String question;
    int correctId;
    String[] answers;
}

class Leaderboard
{
    String name;
    int points;
}
