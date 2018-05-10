package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

	TextView tv_result;
	TextView tv_perfect;
	TextView tv_highscore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		tv_result = (TextView)findViewById(R.id.tv_result);
		tv_perfect = (TextView)findViewById(R.id.tv_perfect);
		tv_highscore = (TextView)findViewById(R.id.tv_highscore);

		tv_result.setText(DataRepo.currentPoints + " / " + DataRepo.quizzes[DataRepo.currentQuiz].questions.length);

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
		else { tv_perfect.setVisibility(View.GONE); }



		//startActivity(new Intent(ResultActivity.this, MainMenuActivity.class));

	}
}
