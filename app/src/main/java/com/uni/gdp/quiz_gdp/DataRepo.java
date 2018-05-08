package com.uni.gdp.quiz_gdp;

public class DataRepo
{
    static String name;
    static int currentQuestion;
    static int currentPoints;

    static Question[] questions;
    static Leaderboard[] Leaderboard;
}

class Question
{
    String question;
    String[] answers;
}

class Leaderboard
{
    String name;
    int points;
}
