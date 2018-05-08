package com.uni.gdp.quiz_gdp;

public class TestService
{
    public static Question[] MakeTestQuiz(int numberQuestions, int numberAnswers)
    {
        Question[] questions = new Question[numberQuestions];

        for (int i = 0; i < questions.length; i++)
        {
            questions[i].question = "Lorem ipsum dolor sit amet Question " + i + "?";
            questions[i].answers = new String[numberAnswers];
            for (int j = 0; j < questions[i].answers.length; j++)
            {
                questions[i].answers[j] = "TestAnswer " + i + " / " + j;
            }
        }

    }
}
