package com.uni.gdp.quiz_gdp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

		DataRepo.localData = getSharedPreferences("data_local", Activity.MODE_PRIVATE);
		et_name = (TextView)findViewById(R.id.et_name);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_leaderboard = (Button) findViewById(R.id.b_leaderboard);

		DataRepo.bestScore = DataRepo.localData.getInt("best", 0);
		DataRepo.name = DataRepo.localData.getString("name", DataRepo.name);
		et_name.setText(DataRepo.name);

		//TODO Load properly, see DataRepo.java for classes
		DataRepo.questions = TestService.MakeTestQuiz(12, 4);
		DataRepo.leaderboard = TestService.MakeTestLeaderboard(10);

		b_startquiz.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
			{
				if (!et_name.getText().toString().equals(""))
				{
					DataRepo.setName(et_name.getText().toString());

					DataRepo.currentQuestion = 0;
					DataRepo.currentPoints = 0;
					startActivity(new Intent(MainMenuActivity.this, QuestionActivity.class));
				}
				else
				{
					Toast.makeText(v.getContext(), "Name kann nicht leer sein", LENGTH_SHORT).show();
				}
            }
        });

		b_leaderboard.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
			{
				DataRepo.setName(et_name.getText().toString());
				startActivity(new Intent(MainMenuActivity.this, LeaderboardActivity.class));
            }
        });
    }



}
