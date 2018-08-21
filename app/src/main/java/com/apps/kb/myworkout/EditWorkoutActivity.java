package com.apps.kb.myworkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EditWorkoutActivity extends AppCompatActivity
{
    private Button addTimeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit " +
                getIntent().getStringExtra("WORKOUT_NAME_ID"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addTimeInterval = findViewById(R.id.add_time_interval2);
        addTimeInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });
    }

    private void openAddTimeInterval() {
        Intent intent = new Intent(this, AddTimeInterval.class);
        startActivity(intent);
    }
}
