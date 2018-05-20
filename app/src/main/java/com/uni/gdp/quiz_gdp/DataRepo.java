package com.uni.gdp.quiz_gdp;

//static variables to save data in one place to be used in multiple activities
class DataRepo
{
	static String name;
    static String opponentName;
	static int currentQuestion;
    static int currentPoints;
	static int opponentPoints;
	static Quiz quiz;
}

//represents the quiz itself
class Quiz
{
	String name;
	Question[] questions;
}

//represents single question in the quiz
class Question
{
    String question;
    int correctId;
    String[] answers;
}