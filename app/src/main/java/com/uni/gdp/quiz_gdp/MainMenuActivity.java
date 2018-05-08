package com.uni.gdp.quiz_gdp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		et_name = (TextView)findViewById(R.id.et_name);
		b_startquiz = (Button) findViewById(R.id.b_startquiz);

		if (!DataRepo.name.equals(""))
		{
			et_name.setText(DataRepo.name);
		}
		DataRepo.questions = TestService.MakeTestQuiz(12, 4);

		b_startquiz.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
			{
				if (!et_name.getText().toString().equals(""))
				{
					DataRepo.name = et_name.getText().toString();
					DataRepo.currentQuestion = 0;
					DataRepo.currentPoints = 0;
					startActivity(new Intent(MainMenuActivity.this, QuestionActivity.class));
				}
				else
				{
					Toast.makeText(v.getContext(), "Name kann nicht leer sein", LENGTH_SHORT).show();
				}
            }
        });
    }



}
