package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity
{
	//UI elements
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

		//setup UI
		tv_playerName = (TextView)findViewById(R.id.tv_playerName);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_player1 = (Button) findViewById(R.id.b_player1);
		b_player2 = (Button) findViewById(R.id.b_player2);

		//set Player1 as default
		DataRepo.name = "Spieler1";
		tv_playerName.setText("Sie sind " + DataRepo.name);
		mma = this;

		//gets the questions and answers etc from server, invokes setQuiz
		PHPService.sendToServer("?func=get_quiz", "setQuiz", this, null, null);

		//set up button-press code
		b_startquiz.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//sends chosen player name to php, invokes addUser
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


	//from PHP, gets quiz data
	void setQuiz(String s)
	{
		//splits the input string and creates quiz-data-structures
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

	//from PHP when quiz starts
	void addUser(String s)
	{
		//resets names and points of the repo
		DataRepo.opponentName = DataRepo.name.equals("Spieler1") ? "Spieler2" : "Spieler1";
		DataRepo.currentQuestion = 0;
		DataRepo.currentPoints = 0;

		//starts the quiz activity
		startActivity(new Intent(MainMenuActivity.this, QuestionActivity.class));
	}

}
