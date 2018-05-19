package com.uni.gdp.quiz_gdp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static com.uni.gdp.quiz_gdp.PHPService.sendToServer;

public class ResultActivity extends AppCompatActivity {

	TextView tv_result_title2;
	TextView tv_result;
	TextView tv_result_opponent;
	TextView tv_final;

	static ResultActivity ra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		tv_result = (TextView)findViewById(R.id.tv_result);
		tv_result_title2 = (TextView)findViewById(R.id.tv_result_title2);
		tv_result_opponent = (TextView)findViewById(R.id.tv_result_opponent);
		tv_final = (TextView)findViewById(R.id.tv_final);

		tv_result.setText(DataRepo.currentPoints + " / " + DataRepo.quiz.questions.length);
		tv_result_opponent.setText(DataRepo.opponentPoints + " / " + DataRepo.quiz.questions.length);
		tv_result_title2.setText(DataRepo.opponentName + " Ergebnis:");
		ra = this;

		heartbeat.run();
	}

	final static Handler hbHandler = new Handler();
	static Runnable heartbeat = new Runnable()
	{
		@Override
		public void run()
		{
			sendToServer("?func=heartbeat&userName=" + DataRepo.name, "checkOpponent", null, null, ra);
			Log.i("PHP", "heartbeat now");
			hbHandler.postDelayed(heartbeat, 9000);
		}
	};

	//from php
	void checkOpponent(String hb)
	{
		String[] items = hb.split(";");
		tv_result_opponent.setText(items[0] + " / " + items[1]);

		if (DataRepo.quiz.questions.length == Integer.parseInt(items[1].trim()))
		{
			tv_final.setText("Quiz beendet");
		}
	}
}
