package com.uni.gdp.quiz_gdp;

import android.content.SharedPreferences;

class DataRepo
{
    static String name;
    static String uuid;
	static int currentQuiz;
	static int currentQuestion;
    static int currentPoints;
	static SharedPreferences localData;

	static Quiz[] quizzes;
	static Player[] players;
    static Leaderboard[] leaderboard;


    static void setPlayers(String text)
	{
		String[] lines = text.split("<br>");
		DataRepo.players = new Player[lines.length-1];

		int j = 0;
		for (int i = 0; i < lines.length; i++)
		{
			String[] line = lines[i].split(";");
			if (line[0].trim().equals(uuid)) {
				j = 1;
			}
			else
			{
				DataRepo.players[i - j] = new Player();
				DataRepo.players[i - j].uuid = line[0].trim();
				DataRepo.players[i - j].name = line[1].trim();
			}
		}
	}

    static String[] leaderboardToList()
	{
		String[] r = new String[leaderboard.length+1];
		for (int i = 0; i < r.length-1; i++)
		{
			r[i] = (i+1) + ". " + (leaderboard[i].name.equals(name) ? ">> " : "") + leaderboard[i].name + " (" + leaderboard[i].points + ")";
		}
		r[r.length-1] = "Ihr Highscore: " + DataRepo.getTotalBestScore();
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

	static int getTotalBestScore()
	{
		int r = 0;
		for (Quiz quiz : quizzes) {
			r += quiz.bestScore;
		}
		return r;
	}

	static void setName(String newName, String newUuid)
	{
		DataRepo.name = newName;
		DataRepo.uuid = newUuid;
		SharedPreferences.Editor editor = localData.edit();
		editor.putString("name", name);
		editor.putString("uuid", uuid);
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

class Player
{
	String name;
	String uuid;
}

class Leaderboard
{
    String name;
    int points;
}
