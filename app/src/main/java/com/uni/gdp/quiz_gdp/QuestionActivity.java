package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity
{
	ActionBar ab;
	TextView tv_question;
    Button b_answer1;
    Button b_answer2;
    Button b_answer3;
    Button b_answer4;

	boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ab = getSupportActionBar();
		ab.setTitle("Frage " + (DataRepo.currentQuestion+1) + " (" + DataRepo.currentPoints + " vs " + DataRepo.opponentPoints + ")");

		//gets the Buttons from layout
		tv_question = (TextView)findViewById(R.id.tv_question);
		b_answer1 = (Button)findViewById(R.id.b_answer1);
		b_answer2 = (Button)findViewById(R.id.b_answer2);
		b_answer3 = (Button)findViewById(R.id.b_answer3);
		b_answer4 = (Button)findViewById(R.id.b_answer4);

		SetQuestion();

		//calls answer(int) method with answer id
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

    //writes question and answers to layout
	private void SetQuestion()
	{
		if (DataRepo.currentQuestion < DataRepo.quiz.questions.length)
		{
			ab.setTitle("Frage " + (DataRepo.currentQuestion+1) + " (" + DataRepo.currentPoints + " vs " + DataRepo.opponentPoints + ")");
			tv_question.setText(DataRepo.quiz.questions[DataRepo.currentQuestion].question);
			b_answer1.setText(DataRepo.quiz.questions[DataRepo.currentQuestion].answers[0]);
			b_answer2.setText(DataRepo.quiz.questions[DataRepo.currentQuestion].answers[1]);
			b_answer3.setText(DataRepo.quiz.questions[DataRepo.currentQuestion].answers[2]);
			b_answer4.setText(DataRepo.quiz.questions[DataRepo.currentQuestion].answers[3]);
		}
	}

	//gets invoked when any answer clicked
	private void Answer(int id)
	{
		if (DataRepo.currentQuestion < DataRepo.quiz.questions.length)
		{
			//toast result of an answer and adds points
			if (DataRepo.quiz.questions[DataRepo.currentQuestion].correctId == id+1)
			{
				DataRepo.currentPoints++;
				Toast.makeText(this, "Richtig!", Toast.LENGTH_LONG).show();
			}
			else
			{
				String correctAnswer = DataRepo.quiz.questions[DataRepo.currentQuestion].answers[DataRepo.quiz.questions[DataRepo.currentQuestion].correctId-1];
				Toast.makeText(this, "Falsch! (" + correctAnswer + ")", Toast.LENGTH_LONG).show();
			}

			//sends to php various data about the answer
			String phpIsCorrect = DataRepo.quiz.questions[DataRepo.currentQuestion].correctId == (id+1) ? "true" : "false";
			PHPService.sendToServer("?func=answer&userName=" + DataRepo.name + "&answerId=" + (id+1) + "&isCorrect=" + phpIsCorrect + "&totalPoints=" + DataRepo.currentPoints, "GetOpponent", null, this, null);
			DataRepo.currentQuestion++;
			if (DataRepo.currentQuestion < DataRepo.quiz.questions.length)
			{
				//go to next question
				SetQuestion();
			}
			else
			{
				//was last question: go to results
				Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	//sets the opponent points from php
	public void GetOpponent(String s)
	{
		DataRepo.opponentPoints = Integer.parseInt(s.trim());
		SetQuestion();
	}

	//disables return button from android
	@Override
	public void onBackPressed() {}

}
