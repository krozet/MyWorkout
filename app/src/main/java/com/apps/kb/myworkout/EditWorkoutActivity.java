package com.apps.kb.myworkout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EditWorkoutActivity extends AppCompatActivity
{
    private Button addTimeIntervalButton, cancelButton, saveButton;
    private DynamicListView editTimeIntervalView;
    private String navigationOrigin = "USER";
    private String workoutName;
    final int TIME_INTERVAL_REQUEST = 1;
    final int PROGRAMMATIC_REQUEST = 3;
    private List<String> timeIntervalViewList;
    private TimeInterval timeInterval;
    private Workout workout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        timeIntervalViewList = new ArrayList<>();
        workoutName = getIntent().getStringExtra("WORKOUT_NAME_ID");
        workout  = new Workout(workoutName);
        workout.load(getApplicationContext());
        setContentView(R.layout.activity_edit_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit " + workoutName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTimeIntervalView = (DynamicListView) findViewById(R.id.edit_time_intervals);
        editTimeIntervalView.setVisibility(View.GONE);

        addTimeIntervalButton = findViewById(R.id.add_time_interval2);
        addTimeIntervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });

        cancelButton = findViewById(R.id.edit_workout_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        saveButton = findViewById(R.id.edit_workout_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
                onBackPressed();
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

    private void saveWorkout() {
        workout.save(getApplicationContext());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        System.out.println("REQUEST CODE: " + requestCode + " RESULT CODE: " + resultCode);
        switch(requestCode) {
            case PROGRAMMATIC_REQUEST:
                if (resultCode == RESULT_CANCELED) {
                    onBackPressed();
                }

                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                }
            case TIME_INTERVAL_REQUEST:
                if (resultCode == RESULT_OK) {
                    timeInterval = data.getParcelableExtra("timeInterval");
                    if (timeInterval != null) {
                        workout.addTimeInterval(timeInterval);
                    }
                }
        }
    }

    private void openAddTimeInterval() {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivityForResult(intent, TIME_INTERVAL_REQUEST);
    }

    private void openAddTimeIntervalProgrammatically()
    {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivityForResult(intent, PROGRAMMATIC_REQUEST);
    }

    private void populateView(){
        timeIntervalViewList.clear();

        for (int i=0; i < workout.getSize(); i++) {
             timeIntervalViewList.add(workout.getTimeIntervalAt(i).getViewDetails());
        }
        editTimeIntervalView.setVisibility(View.VISIBLE);

        final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, timeIntervalViewList);
//        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, timeIntervalViewList);
        editTimeIntervalView.setCheeseList(new ArrayList<>(timeIntervalViewList));
        editTimeIntervalView.setAdapter(adapter);
        editTimeIntervalView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        editTimeIntervalView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//             {
////              openWorkout(position);
//            }
//        });

//        editTimeIntervalView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                String object = (String) adapter.getItem(position);
////              createDeleteAlert(position);
//                return true;
//            }
//        });

    }
}
