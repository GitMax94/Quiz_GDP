package com.uni.gdp.quiz_gdp;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DataRepo
{
	static int playerId;
	static String name;
    static String uuid;
    static String opponentName;
	static int currentQuiz;
	static int currentQuestion;
    static int currentPoints;
	static int opponentQuestion;
	static int opponentPoints;
	static SharedPreferences localData;

	static Quiz[] quizzes;
	static Player[] players;
    static Leaderboard[] leaderboard;


    static void setPlayers(String text)
	{
		String[] lines = text.split("<br>");
		ArrayList<Player> players_temp = new  ArrayList<Player>();

		int j = 0;
		for (int i = 0; i < lines.length-1; i++)
		{
			String[] line = lines[i].split(";");
			if (!lines[i].isEmpty() && !lines[i].equals(";") && !isInPlayerList(players_temp, line[0].trim()))
			{
				players_temp.add(new Player(line[1].trim(), line[0].trim()));
			}
		}
		DataRepo.players = new Player[players_temp.size()];
		for (int i = 0; i < DataRepo.players.length; i++)
		{
			DataRepo.players[i] = players_temp.get(i);
		}
	}

	static boolean isInPlayerList(ArrayList<Player> players, String uuid)
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).uuid.equals(uuid))
			{
				return true;
			}
		}
		return false;
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

	public Player(String name, String uuid) {
		this.name = name;
		this.uuid = uuid;
	}
}

class Leaderboard
{
    String name;
    int points;
}
