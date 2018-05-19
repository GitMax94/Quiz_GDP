package com.uni.gdp.quiz_gdp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity
{

	TextView et_name;
	Button b_startquiz;
	Button b_leaderboard;
	Button b_player;
	TextView tv_message_p2;
	String player = "Spieler1";
	MainMenuActivity mma;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		et_name = (TextView)findViewById(R.id.et_name_old);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_leaderboard = (Button) findViewById(R.id.b_leaderboard);
		tv_message_p2 = (TextView)findViewById(R.id.tv_message_p2);
		b_player = (Button) findViewById(R.id.b_player);


		DataRepo.name = "";
		DataRepo.uuid = "";

		DataRepo.localData = getSharedPreferences("data_local", Activity.MODE_PRIVATE);
		DataRepo.name = DataRepo.localData.getString("name", DataRepo.name);
		DataRepo.uuid = DataRepo.localData.getString("uuid", DataRepo.uuid);

		if (DataRepo.name.isEmpty() || DataRepo.uuid.isEmpty())
		{
			startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
			finish();
		}







		PHPService.sendToServer("?func=get_quizzes", "setQuizzes", this, null, null, null);
		//PHPService.sendToServer("?func=add_user&userName=" + player, "addUser", this, null, null, null);
		//PHPService.sendToServer("?func=add_user&userId=1243&name=Max", "addUser", this, null, null, null);
		PHPService.heartbeat.run();

		//http://cbrell.de/bwi50207/181/op995204/QuizService.php?func=add_user&userId=1243&name=Max

		//PHPService.sendToServer("", "test", this, null);


		et_name.setText(DataRepo.name);

		//TODO Load properly, see DataRepo.java for classes
		//DataRepo.quizzes = TestService.MakeTestQuizzes(20);
		//DataRepo.leaderboard = TestService.MakeTestLeaderboard(10);


		mma = this;

		b_startquiz.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PHPService.sendToServer("?func=add_user&userName=" + player + "&userId=" + DataRepo.uuid + "&name" + DataRepo.name, "addUser", mma, null, null, null);
			}
		});


		b_player.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (player.equals("Spieler1"))
				{
					player = "Spieler2";
				}
				else
				{
					player = "Spieler1";
				}
				b_player.setText(player);
			}
		});
    }



	void setQuizzes(String s)
	{
		Log.i("PHP Bullshit", s);




		String[] lines = s.split("<br>");
		DataRepo.quizzes = new Quiz[lines.length-1];

		for (int i = 0; i < lines.length-1; i++)
		{
			String[] line = lines[i].split(";");
			DataRepo.quizzes[i] = new Quiz();

			DataRepo.quizzes[i].name = line[0];
			DataRepo.quizzes[i].questions = new Question[(line.length-1)/6];
			int k = 0;
			for (int j = 1; j < line.length; j+=6)
			{
				DataRepo.quizzes[i].questions[k] = new Question();
				DataRepo.quizzes[i].questions[k].question = line[j];
				DataRepo.quizzes[i].questions[k].correctId = Integer.parseInt(line[j+1]);
				DataRepo.quizzes[i].questions[k].answers = new String[4];
				DataRepo.quizzes[i].questions[k].answers[0] = line[j+2];
				DataRepo.quizzes[i].questions[k].answers[1] = line[j+3];
				DataRepo.quizzes[i].questions[k].answers[2] = line[j+4];
				DataRepo.quizzes[i].questions[k].answers[3] = line[j+5];
				k++;
			}
		}

		Log.i("PHP Bullshit", s);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getBaseContext(), "Loaded Quiz", Toast.LENGTH_LONG).show();
			}
		});
	}

	void addUser(String s)
	{
		DataRepo.name = player;
		DataRepo.currentQuiz = 0;
		DataRepo.currentQuestion = 0;
		DataRepo.currentPoints = 0;
		startActivity(new Intent(MainMenuActivity.this, QuestionActivity.class));
	}

}
