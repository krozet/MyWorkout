package com.apps.kb.myworkout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WorkoutActivity extends AppCompatActivity
{
    private Button editWorkout, startWorkout, selectMusic;
    private String workoutName;
    private String navigationOrigin = "USER";

    final int PROGRAMMATIC_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workoutName = getIntent().getStringExtra("WORKOUT_NAME_ID");
        getSupportActionBar().setTitle(workoutName);

        editWorkout = findViewById(R.id.edit_workout);
        editWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openEditWorkout();
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

        selectMusic = findViewById(R.id.music_button);
        selectMusic.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {

            }
        });

        if ((getIntent().getStringExtra("NAVIGATION_ORIGIN_ID")).equals("CREATE_WORKOUT"))
        {
            navigationOrigin = "CREATE_WORKOUT";
            openEditWorkoutProgrammatically();
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
        }
    }

    private void openEditWorkout()
    {
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        navigationOrigin = "USER";
        startActivity(intent);
    }

    private void openEditWorkoutProgrammatically()
    {
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        navigationOrigin = "USER";
        startActivityForResult(intent, PROGRAMMATIC_REQUEST);
    }

}
