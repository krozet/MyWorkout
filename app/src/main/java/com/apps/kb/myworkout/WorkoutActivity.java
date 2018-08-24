package com.apps.kb.myworkout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WorkoutActivity extends AppCompatActivity
{
    private Button editWorkout, startWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("WORKOUT_NAME_ID"));

        editWorkout = findViewById(R.id.edit_workout);
        editWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),
                        "This does nothing yet", Toast.LENGTH_LONG).show();
            }
        });

        startWorkout = findViewById(R.id.start_workout);
        startWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(),
                        "Lol you thought", Toast.LENGTH_LONG).show();
            }
        });
    }

}
