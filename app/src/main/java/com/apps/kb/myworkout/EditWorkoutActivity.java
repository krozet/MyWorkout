package com.apps.kb.myworkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditWorkoutActivity extends AppCompatActivity
{
    private Button addTimeInterval;
    private String navigationOrigin = "USER";
    private String workoutName;

    final int PROGRAMMATIC_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        workoutName = getIntent().getStringExtra("WORKOUT_NAME_ID");
        getSupportActionBar().setTitle("Edit " + workoutName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addTimeInterval = findViewById(R.id.add_time_interval2);
        addTimeInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if ((getIntent().getStringExtra("NAVIGATION_ORIGIN_ID")).equals("CREATE_WORKOUT"))
        {
            navigationOrigin = "CREATE_WORKOUT";
            openAddTimeIntervalProgrammatically();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        System.out.println("REQUEST CODE: " + requestCode + " RESULT CODE: " + resultCode);
        if(requestCode == PROGRAMMATIC_REQUEST)
        {
            if(resultCode == RESULT_CANCELED)
            {
                onBackPressed();
            }

            if(resultCode == RESULT_OK)
            {
                setResult(RESULT_OK);
            }
        }
    }

    private void openAddTimeInterval() {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivity(intent);
    }

    private void openAddTimeIntervalProgrammatically()
    {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivityForResult(intent, PROGRAMMATIC_REQUEST);
    }
}
