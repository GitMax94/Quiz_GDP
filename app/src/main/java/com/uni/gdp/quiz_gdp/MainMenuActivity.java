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
	{ // test
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		DataRepo.localData = getSharedPreferences("data_local", Activity.MODE_PRIVATE);
		et_name = (TextView)findViewById(R.id.et_name);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_leaderboard = (Button) findViewById(R.id.b_leaderboard);

		//Toast.makeText(this, "Start php", LENGTH_SHORT).show();
		sendToServer(this);

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






	public void sendToServer(final Context context)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					String webURL = "http://cbrell.de/bwi50205/172/op995204/QuizAuswertung.php";
					String TextParameter = URLEncoder.encode(webURL, "UTF-8");

					URL scriptURL = new URL(webURL);
					HttpURLConnection HttpURLVerbindung = (HttpURLConnection) scriptURL.openConnection();
					HttpURLVerbindung.setDoOutput(true);
					HttpURLVerbindung.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					HttpURLVerbindung.setFixedLengthStreamingMode(TextParameter.getBytes().length);

					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(HttpURLVerbindung.getOutputStream());
					outputStreamWriter.write(TextParameter);
					outputStreamWriter.flush();
					outputStreamWriter.close();

					InputStream answerInputStream = HttpURLVerbindung.getInputStream();

					BufferedReader br = new BufferedReader(new InputStreamReader(answerInputStream));

					final StringBuilder phpOutput = new StringBuilder();

					String text = br.readLine();
					while (text != null)
					{
						phpOutput.append(text + "\n");
						text = br.readLine();
					}

					final String phpOutput2 = phpOutput.toString();

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							et_name.setText("PHP: " + phpOutput2);
							Toast.makeText(context, phpOutput2.split("<br>")[phpOutput2.split("<br>").length-1], Toast.LENGTH_LONG).show();
						}
					});

					answerInputStream.close();
					HttpURLVerbindung.disconnect();
				}
				catch (Exception e)
				{
					//Toast.makeText(context, "Fuck PHP", Toast.LENGTH_LONG).show();
				}

			}
		}).start();
	}

}
