package com.uni.gdp.quiz_gdp;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DataRepo
{
	static String name;
    static String opponentName;
	static int currentQuestion;
    static int currentPoints;
	static int opponentPoints;

	static Quiz quiz;
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