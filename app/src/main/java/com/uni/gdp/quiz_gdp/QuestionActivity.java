package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

	private void Answer(int id)
	{
		if (DataRepo.currentQuestion < DataRepo.quiz.questions.length)
		{
			if (DataRepo.quiz.questions[DataRepo.currentQuestion].correctId == id+1)
				DataRepo.currentPoints++;

			String phpIsCorrect = DataRepo.quiz.questions[DataRepo.currentQuestion].correctId == (id+1) ? "true" : "false";

			PHPService.sendToServer("?func=answer&userName=" + DataRepo.name + "&answerId=" + (id+1) + "&isCorrect=" + phpIsCorrect + "&totalPoints=" + DataRepo.currentPoints, "GetOpponent", null, this, null);
			DataRepo.currentQuestion++;
			if (DataRepo.currentQuestion < DataRepo.quiz.questions.length)
			{
				SetQuestion();
			}
			else
			{
				Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	public void GetOpponent(String s)
	{
		DataRepo.opponentPoints = Integer.parseInt(s.trim());
		SetQuestion();
	}

	@Override
	public void onBackPressed() {}

}
