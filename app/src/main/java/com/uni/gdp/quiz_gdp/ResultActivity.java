package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

	TextView tv_result;
	Button b_backtomenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		tv_result = (TextView)findViewById(R.id.tv_result);
		b_backtomenu = (Button)findViewById(R.id.b_backtomenu);


		tv_result.setText(DataRepo.currentPoints + " / " + DataRepo.questions.length);

		b_backtomenu.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ResultActivity.this, MainMenuActivity.class));
			}
		});

	}
}
