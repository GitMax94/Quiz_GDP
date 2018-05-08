package com.uni.gdp.quiz_gdp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);





        Button b_startquiz = (Button) findViewById(R.id.b_startquiz);
        b_startquiz.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView tv_name = (TextView)findViewById(R.id.tv_name);
                DataRepo.name = tv_name.getText().toString();
                DataRepo.currentQuestion = 0;
                startActivity(new Intent(MainMenuActivity.this, QuestionActivity.class));
            }
        });


    }



}
