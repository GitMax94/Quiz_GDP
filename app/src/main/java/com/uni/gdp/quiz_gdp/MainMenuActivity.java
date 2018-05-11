package com.uni.gdp.quiz_gdp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import static android.widget.Toast.LENGTH_SHORT;

public class MainMenuActivity extends AppCompatActivity
{

	TextView et_name;
	Button b_startquiz;
	Button b_leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

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

		PHPService.sendToServer("?anwendung=F", "setQuizzes", this, null);

		//PHPService.heartbeat.run();



		et_name = (TextView)findViewById(R.id.et_name_old);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_leaderboard = (Button) findViewById(R.id.b_leaderboard);

		//PHPService.sendToServer("", "test", this, null);


		et_name.setText(DataRepo.name);

		//TODO Load properly, see DataRepo.java for classes
		DataRepo.quizzes = TestService.MakeTestQuizzes(20);
		DataRepo.leaderboard = TestService.MakeTestLeaderboard(10);

		b_startquiz.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainMenuActivity.this, SelectquizActivity.class));
			}
		});

		b_leaderboard.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
			{
				//DataRepo.setName(et_name.getText().toString(), DataRepo.uuid);
				startActivity(new Intent(MainMenuActivity.this, LeaderboardActivity.class));
            }
        });
    }



	void setQuizzes(final String s)
	{
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
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getBaseContext(), "Loaded Quiz", Toast.LENGTH_LONG).show();
			}
		});
	}

}
