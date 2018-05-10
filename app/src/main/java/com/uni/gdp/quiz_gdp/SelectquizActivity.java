package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class SelectquizActivity extends AppCompatActivity {

	ListView lv_quizzes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectquiz);

		lv_quizzes = (ListView) findViewById(R.id.lv_quizzes);

		String[] displayList = DataRepo.quizzesToList();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayList);
		lv_quizzes.setAdapter(adapter);

		lv_quizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				DataRepo.currentQuiz = position;
				DataRepo.currentQuestion = 0;
				DataRepo.currentPoints = 0;
				startActivity(new Intent(SelectquizActivity.this, QuestionActivity.class));
			}
		});








	}
}
