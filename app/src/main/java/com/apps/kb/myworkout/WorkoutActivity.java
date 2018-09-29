package com.apps.kb.myworkout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    private Button editWorkout, startWorkout, selectMusic;
    private ListView workoutTimeIntervalView;
    private List<String> timeIntervalViewList;
    private TimeInterval timeInterval;
    private Workout workout;
    private String workoutName;
    private String navigationOrigin = "USER";

    final int PROGRAMMATIC_REQUEST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeIntervalViewList = new ArrayList<>();
        workoutName = getIntent().getStringExtra("WORKOUT_NAME_ID");
        workout = new Workout(workoutName);

        getSupportActionBar().setTitle(workoutName);

        workoutTimeIntervalView = findViewById(R.id.workout_time_intervals);
        workoutTimeIntervalView.setVisibility(View.GONE);

        editWorkout = findViewById(R.id.edit_workout);
        editWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditWorkout();
            }
        });

        startWorkout = findViewById(R.id.start_workout);
        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartWorkout();
            }
        });

        selectMusic = findViewById(R.id.music_button);
        selectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if ((getIntent().getStringExtra("NAVIGATION_ORIGIN_ID")).equals("CREATE_WORKOUT")) {
            navigationOrigin = "CREATE_WORKOUT";
            openEditWorkoutProgrammatically();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("REQUEST CODE: " + requestCode + " RESULT CODE: " + resultCode);
        if (requestCode == PROGRAMMATIC_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                onBackPressed();
            }
        }
    }

    private void openEditWorkout() {
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        navigationOrigin = "USER";
        startActivity(intent);
    }

    private void openEditWorkoutProgrammatically() {
        Intent intent = new Intent(this, EditWorkoutActivity.class);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        navigationOrigin = "USER";
        startActivityForResult(intent, PROGRAMMATIC_REQUEST);
    }


    private void openStartWorkout() {
        Intent intent = new Intent(this, StartWorkoutActivity.class);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        intent.putExtra("Workout", workout);
        navigationOrigin = "USER";
        startActivity(intent);
    }

    private void populateView() {
        workout.load(getApplicationContext());
        timeIntervalViewList.clear();

        for (int i = 0; i < workout.getSize(); i++) {
            timeIntervalViewList.add(workout.getTimeIntervalAt(i).getViewDetails());
        }
        workoutTimeIntervalView.setVisibility(View.VISIBLE);

        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeIntervalViewList);
        workoutTimeIntervalView.setAdapter(adapter);
        workoutTimeIntervalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              openWorkout(position);
            }
        });

        workoutTimeIntervalView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String object = (String) adapter.getItem(position);
//              createDeleteAlert(position);
                return true;
            }
        });

    }
}
