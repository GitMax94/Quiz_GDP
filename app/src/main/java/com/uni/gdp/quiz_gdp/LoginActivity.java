package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity {

	TextView et_name;
	Button b_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);


		et_name = findViewById(R.id.et_name);
		b_login = (Button) findViewById(R.id.b_login);




		b_login.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!et_name.getText().toString().equals(""))
				{
					DataRepo.setName(et_name.getText().toString(), "123456789");
					startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
				}
				else
				{
					Toast.makeText(v.getContext(), "Name kann nicht leer sein", LENGTH_SHORT).show();
				}
			}
		});



	}
}
