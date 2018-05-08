package com.uni.gdp.quiz_gdp;

import android.content.SharedPreferences;

class DataRepo
{
    static String name;
	static int bestScore;
	static int currentQuestion;
    static int currentPoints;
	static SharedPreferences localData;

	static Question[] questions;
    static Leaderboard[] leaderboard;
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
