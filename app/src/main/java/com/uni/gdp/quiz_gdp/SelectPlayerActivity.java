package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectPlayerActivity extends AppCompatActivity {

	ListView lv_players;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_player);
		Log.i("PLAYERS", "1");
		PHPService.sendToServer("?anwendung=S", "setPlayerNames", null, null, this);
		Log.i("PLAYERS", "2");
	}

	void setList(final String[] displayList)
	{
		final SelectPlayerActivity spa = this;
		Log.i("PLAYERS", "6");
		runOnUiThread(new Runnable() {
			@Override
			public void run()
			{
				lv_players = (ListView) findViewById(R.id.lv_players);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(spa, android.R.layout.simple_list_item_1, displayList);
				lv_players.setAdapter(adapter);

				lv_players.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						//Send invite here, I think
						//PHPService.sendToServer("?anwendung=F", "setQuizzes", this, null);
					}
				});
				Toast.makeText(getBaseContext(), "Loaded Players", Toast.LENGTH_LONG).show();
			}
		});

	}

	void setPlayerNames(String input)
	{
		Log.i("PLAYERS", "3");
		DataRepo.setPlayers(input);
		Log.i("PLAYERS", "4");
		String[] r = new String[DataRepo.players.length];
		for (int i = 0; i < DataRepo.players.length; i++)
		{
			r[i] = DataRepo.players[i].name;
		}
		Log.i("PLAYERS", "5");
		setList(r);
	}



}