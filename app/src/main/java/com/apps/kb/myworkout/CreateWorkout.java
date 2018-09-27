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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateWorkout extends AppCompatActivity {
    private Button myWorkoutsButton, addWorkout, clearWorkouts, addTimeInterval;
    private EditText nameMyWorkout;
    private int resultcode;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        resultcode = RESULT_CANCELED;

        getSupportActionBar().setTitle("Create Workout");

        nameMyWorkout = findViewById(R.id.enter_workout_name);
        nameMyWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameMyWorkout.setText("");
            }
        });

        addWorkout = findViewById(R.id.add_workout);
        addWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeWorkout(v);
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nameMyWorkout.getWindowToken(), 0);
                resultcode = RESULT_OK;
                onBackPressed();
            }
        });

        nameMyWorkout.performClick();

        InputMethodManager imm = (InputMethodManager)   getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    @Override
    public void onBackPressed()
    {
        setResult(resultcode);
        super.onBackPressed();
}

    public void writeWorkout(View view) {
        String name = nameMyWorkout.getText().toString();
        String file_name = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        String my_workouts_file_name = "my_workouts";
        try {
            //edit my workouts to include this workout
            FileOutputStream fileOutputStream = openFileOutput(my_workouts_file_name, MODE_APPEND);
            fileOutputStream.write((file_name + "\n").getBytes());
            fileOutputStream.close();

            //create file to save time intervals
            FileOutputStream fileOutputStream2 = openFileOutput(file_name, MODE_APPEND);
            fileOutputStream2.write(("").getBytes());
            fileOutputStream2.close();

            System.out.println("Wrote: " + file_name);
            nameMyWorkout.setText("");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Don't use this
    /*
    private void openAddTimeInterval() {
        name = nameMyWorkout.getText().toString();
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("name", name);
        intent.putExtra("creatingWorkout", true);
        setResult(Activity.RESULT_OK, intent);
        startActivity(intent);
    }
    */

    public void openMyWorkouts() {
        Intent intent = new Intent(this, MyWorkouts.class);
        startActivity(intent);
    }
}
