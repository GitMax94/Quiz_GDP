package com.uni.gdp.quiz_gdp;

import android.app.Activity;
import android.content.Intent;
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




		et_name = (TextView)findViewById(R.id.et_name_old);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);
		b_leaderboard = (Button) findViewById(R.id.b_leaderboard);

		//PHPService.sendToServer("", "test", this, null);


		et_name.setText(DataRepo.name);

		//TODO Load properly, see DataRepo.java for classes
		DataRepo.quizzes = TestService.MakeTestQuizzes(20);
		DataRepo.leaderboard = TestService.MakeTestLeaderboard(10);

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
