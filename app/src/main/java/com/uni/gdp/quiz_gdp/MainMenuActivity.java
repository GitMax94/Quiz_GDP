package com.uni.gdp.quiz_gdp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity
{

	TextView tv_playerName;
	Button b_startquiz;
	Button b_player1;
	Button b_player2;
	MainMenuActivity mma;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		tv_playerName = (TextView)findViewById(R.id.tv_playerName);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_player1 = (Button) findViewById(R.id.b_player1);
		b_player2 = (Button) findViewById(R.id.b_player2);

		DataRepo.name = "Spieler1";
		tv_playerName.setText("Sie sind " + DataRepo.name);
		mma = this;
		PHPService.sendToServer("?func=get_quiz", "setQuiz", this, null, null);
		//PHPService.heartbeat.run();

		b_startquiz.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PHPService.sendToServer("?func=add_user&userName=" + DataRepo.name, "addUser", mma, null, null);
			}
		});

		b_player1.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DataRepo.name = "Spieler1";
				tv_playerName.setText("Sie sind " + DataRepo.name);
			}
		});

		b_player2.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DataRepo.name = "Spieler2";
				tv_playerName.setText("Sie sind " + DataRepo.name);
			}
		});
    }


	//from PHP
	void setQuiz(String s)
	{
		//String[] line = s.split("<br>")[0].split(";");
		String[] line = s.split(";");
		DataRepo.quiz = new Quiz();

		DataRepo.quiz.name = line[0];
		DataRepo.quiz.questions = new Question[(line.length-1)/6];
		int k = 0;
		for (int j = 1; j < line.length; j+=6)
		{
			DataRepo.quiz.questions[k] = new Question();
			DataRepo.quiz.questions[k].question = line[j];
			DataRepo.quiz.questions[k].correctId = Integer.parseInt(line[j+1]);
			DataRepo.quiz.questions[k].answers = new String[4];
			DataRepo.quiz.questions[k].answers[0] = line[j+2];
			DataRepo.quiz.questions[k].answers[1] = line[j+3];
			DataRepo.quiz.questions[k].answers[2] = line[j+4];
			DataRepo.quiz.questions[k].answers[3] = line[j+5];
			k++;
		}
	}

	//from PHP
	void addUser(String s)
	{
		DataRepo.opponentName = DataRepo.name.equals("Spieler1") ? "Spieler2" : "Spieler1";
		DataRepo.currentQuestion = 0;
		DataRepo.currentPoints = 0;
		startActivity(new Intent(MainMenuActivity.this, QuestionActivity.class));
	}

}
