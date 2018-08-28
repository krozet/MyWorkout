package com.apps.kb.myworkout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateWorkout extends AppCompatActivity {
    private Button myWorkoutsButton, addWorkout, clearWorkouts, addTimeInterval;
    private EditText nameMyWorkout;
<<<<<<< HEAD
    private int resultcode;
    private String name = "";
=======
>>>>>>> parent of c92c961... Big Navigation Update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        nameMyWorkout = findViewById(R.id.enter_workout_name);
        nameMyWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameMyWorkout.setText("");
            }
        });

<<<<<<< HEAD
=======
        myWorkoutsButton = findViewById(R.id.to_my_workouts);
        myWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyWorkouts();
            }
        });

>>>>>>> parent of c92c961... Big Navigation Update
        addWorkout = findViewById(R.id.add_workout);
        addWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
<<<<<<< HEAD
//                writeWorkout(v);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nameMyWorkout.getWindowToken(), 0);
                resultcode = RESULT_OK;
=======
                writeWorkout(v);
            }
        });

        clearWorkouts = findViewById(R.id.clear_my_workouts);
        clearWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWorkouts(v);
            }
        });

        addTimeInterval = findViewById(R.id.add_time_interval);
        addTimeInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
>>>>>>> parent of c92c961... Big Navigation Update
                openAddTimeInterval();
            }
        });

    }

    private void openAddTimeInterval() {
        name = nameMyWorkout.getText().toString();
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("name", name);
        intent.putExtra("creatingWorkout", true);
        setResult(Activity.RESULT_OK, intent);
        startActivity(intent);
    }

    public void openMyWorkouts() {
        Intent intent = new Intent(this, MyWorkouts.class);
        startActivity(intent);
    }
<<<<<<< HEAD
=======

    public void writeWorkout(View view) {
        //Stable
        String name = nameMyWorkout.getText().toString() + "\n";
        String file_name = "my_workouts";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_APPEND);
            fileOutputStream.write(name.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "My workout saved", Toast.LENGTH_LONG).show();
            nameMyWorkout.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearWorkouts(View view) {
        String file_name = "my_workouts";
        String clr = "";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
            fileOutputStream.write(clr.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "My workout saved", Toast.LENGTH_LONG).show();
            nameMyWorkout.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
>>>>>>> parent of c92c961... Big Navigation Update
}
