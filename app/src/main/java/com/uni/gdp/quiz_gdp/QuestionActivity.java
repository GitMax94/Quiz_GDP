package com.uni.gdp.quiz_gdp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity
{
    TextView tv_question;
    Button b_answer1;
    Button b_answer2;
    Button b_answer3;
    Button b_answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        tv_question = (TextView)findViewById(R.id.tv_question);
        b_answer1 = (Button)findViewById(R.id.b_answer1);
        b_answer2 = (Button)findViewById(R.id.b_answer2);
        b_answer3 = (Button)findViewById(R.id.b_answer3);
        b_answer4 = (Button)findViewById(R.id.b_answer4);

        tv_question.setText(DataRepo.questions[DataRepo.currentQuestion].question);
        b_answer1.setText(DataRepo.questions[DataRepo.currentQuestion].answers[0]);
        b_answer2.setText(DataRepo.questions[DataRepo.currentQuestion].answers[1]);
        b_answer3.setText(DataRepo.questions[DataRepo.currentQuestion].answers[2]);
        b_answer4.setText(DataRepo.questions[DataRepo.currentQuestion].answers[3]);



    }
}
