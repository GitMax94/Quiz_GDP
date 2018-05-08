package com.uni.gdp.quiz_gdp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LeaderboardActivity extends AppCompatActivity {

	ListView lv_leaderboard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);

		lv_leaderboard = (ListView)findViewById(R.id.lv_leaderboard);
		String[] displayList = DataRepo.leaderboardToList();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayList);
		lv_leaderboard.setAdapter(adapter);
	}
}
