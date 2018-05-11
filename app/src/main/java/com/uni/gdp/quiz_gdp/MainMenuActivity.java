package com.uni.gdp.quiz_gdp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
/*

$DateiSchreiben = fopen("Standortdaten.csv", "a");																							//In Anlehnung an Folien-HTML-und-PHP-komplett Von Prof.Dr. Brell S.15
	fwrite($DateiSchreiben, $MeetMeCsvTextZeile);
	fclose($DateiSchreiben); 

 */
		DataRepo.localData = getSharedPreferences("data_local", Activity.MODE_PRIVATE);
		et_name = (TextView)findViewById(R.id.et_name);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_leaderboard = (Button) findViewById(R.id.b_leaderboard);

		//Toast.makeText(this, "Start php", LENGTH_SHORT).show();

		//this.getClass().getMethod()

		PHPService.sendToServer("", "test", this, null);


		DataRepo.name = DataRepo.localData.getString("name", DataRepo.name);
		et_name.setText(DataRepo.name);

		//Toast.makeText(this, TestService.TestPHP(), LENGTH_SHORT).show();

		//TODO Load properly, see DataRepo.java for classes
		DataRepo.quizzes = TestService.MakeTestQuizzes(20);
		DataRepo.leaderboard = TestService.MakeTestLeaderboard(10);

		b_startquiz.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
			{
				if (!et_name.getText().toString().equals(""))
				{
					DataRepo.setName(et_name.getText().toString());
					startActivity(new Intent(MainMenuActivity.this, SelectquizActivity.class));
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

	void test(final String s)
	{
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getBaseContext(), "Test: " + s, Toast.LENGTH_LONG).show();
			}
		});
	}






}
