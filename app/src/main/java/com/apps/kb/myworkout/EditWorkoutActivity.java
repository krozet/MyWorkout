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

    final int PROGRAMMATIC_REQUEST = 3;

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

        if ((getIntent().getStringExtra("NAVIGATION_ORIGIN_ID")).equals("CREATE_WORKOUT"))
        {
            Toast.makeText(getApplicationContext(),
                    "Got here automatically from create workout", Toast.LENGTH_LONG).show();
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
        }
    }

    private void openAddTimeInterval() {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        navigationOrigin = "USER";
        startActivity(intent);
    }

    private void openAddTimeIntervalProgrammatically()
    {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        navigationOrigin = "USER";
        startActivityForResult(intent, PROGRAMMATIC_REQUEST);
    }
}
