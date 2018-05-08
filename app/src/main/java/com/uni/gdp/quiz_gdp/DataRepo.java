package com.uni.gdp.quiz_gdp;

public class DataRepo
{
    static String name;
    static int currentQuestion;
    static int currentPoints;

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
