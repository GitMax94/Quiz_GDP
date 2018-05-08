package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity
{
	ActionBar ab;
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

        ab = getSupportActionBar();
		ab.setTitle("Frage " + (DataRepo.currentQuestion+1));



		tv_question = (TextView)findViewById(R.id.tv_question);
		b_answer1 = (Button)findViewById(R.id.b_answer1);
		b_answer2 = (Button)findViewById(R.id.b_answer2);
		b_answer3 = (Button)findViewById(R.id.b_answer3);
		b_answer4 = (Button)findViewById(R.id.b_answer4);

		SetQuestion();

		b_answer1.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Answer(0);
			}
		});
		b_answer2.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Answer(1);
			}
		});
		b_answer3.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Answer(2);
			}
		});
		b_answer4.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Answer(3);
			}
		});

    }

	private void SetQuestion()
	{
		ab.setTitle("Frage " + (DataRepo.currentQuestion+1));
		tv_question.setText(DataRepo.questions[DataRepo.currentQuestion].question);
		b_answer1.setText(DataRepo.questions[DataRepo.currentQuestion].answers[0]);
		b_answer2.setText(DataRepo.questions[DataRepo.currentQuestion].answers[1]);
		b_answer3.setText(DataRepo.questions[DataRepo.currentQuestion].answers[2]);
		b_answer4.setText(DataRepo.questions[DataRepo.currentQuestion].answers[3]);
	}

	private void Answer(int id)
	{
		if (DataRepo.currentQuestion < DataRepo.questions.length)
		{

			if (DataRepo.questions[DataRepo.currentQuestion].correctId == id)
				DataRepo.currentPoints++;
			DataRepo.currentQuestion++;
			if (DataRepo.currentQuestion < DataRepo.questions.length)
			{
				SetQuestion();
			}
			else
			{
				startActivity(new Intent(QuestionActivity.this, ResultActivity.class));
			}
		}
	}
}
