package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.uni.gdp.quiz_gdp.PHPService.resultAct;
import static com.uni.gdp.quiz_gdp.PHPService.sendToServer;

public class ResultActivity extends AppCompatActivity {

	TextView tv_result;
	TextView tv_perfect;
	TextView tv_highscore;

	static ResultActivity ra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		tv_result = (TextView)findViewById(R.id.tv_result);
		tv_perfect = (TextView)findViewById(R.id.tv_perfect);
		tv_highscore = (TextView)findViewById(R.id.tv_highscore);

		tv_result.setText(DataRepo.currentPoints + " / " + DataRepo.quizzes[DataRepo.currentQuiz].questions.length);
		tv_perfect.setText(DataRepo.opponentPoints + " / " + DataRepo.quizzes[DataRepo.currentQuiz].questions.length);

		ra = this;

		heartbeat.run();


		/*
		if (DataRepo.quizzes[DataRepo.currentQuiz].bestScore < DataRepo.currentPoints)
		{
			SharedPreferences.Editor editor = DataRepo.localData.edit();
			editor.putInt("best", DataRepo.currentPoints);
			editor.apply();
			DataRepo.quizzes[DataRepo.currentQuiz].bestScore = DataRepo.currentPoints;
			tv_highscore.setVisibility(View.VISIBLE);
		}
		else { tv_highscore.setVisibility(View.GONE); }

		if (DataRepo.currentPoints == DataRepo.quizzes[DataRepo.currentQuiz].questions.length)
			 { tv_perfect.setVisibility(View.VISIBLE); }
		else { tv_perfect.setVisibility(View.GONE); }*/



		//startActivity(new Intent(ResultActivity.this, MainMenuActivity.class));

	}

	final static Handler hbHandler = new Handler();
	static Runnable heartbeat = new Runnable()
	{
		@Override
		public void run()
		{
			sendToServer("?func=heartbeat&userId=" + DataRepo.uuid + "&name=" + DataRepo.name + "&userName=" + DataRepo.playerId, "checkOpponent", null, null, null, ra);
			Log.i("PHP", "heartbeat now");
			hbHandler.postDelayed(heartbeat, 9000);
		}
	};

	void checkOpponent(String hb)
	{
		String[] items = hb.split(";");

		tv_perfect.setText(items[1] + ": " + items[2] + " / " + items[3]);




		/*String[] texts = hb.split(";");

		//texts[0]
		DataRepo.opponentName = texts[1];
		DataRepo.opponentPoints = Integer.parseInt(texts[2]);
		DataRepo.opponentQuestion = Integer.parseInt(texts[3]);*/
		//returns isOnline (true/false), opponentName, points (int), questions (int)





	}
}
