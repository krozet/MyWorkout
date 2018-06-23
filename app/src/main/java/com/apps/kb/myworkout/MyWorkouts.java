package com.apps.kb.myworkout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyWorkouts extends AppCompatActivity {
    private Button createWorkoutButton;
    private ListView displayMyWorkouts;
    private List<String> myWorkoutNamesList;
    private List<Workout> myWorkouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workouts);
        myWorkouts = new ArrayList<>();
        myWorkoutNamesList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    3);
        }
        else
        {
            //Permission already granted
        }

        displayMyWorkouts = findViewById(R.id.display_my_workouts);
        displayMyWorkouts.setVisibility(View.GONE);
        createWorkoutButton = findViewById(R.id.to_create_workout);
        readMyWorkouts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myWorkoutNamesList);
        displayMyWorkouts.setAdapter(adapter);
        createWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCreateWorkout();
            }
        });
    }

    private String[] arrayOf(String str)
    {
        String[] returnArr = {str};
        return returnArr;
    }

    public void openCreateWorkout() {
        Intent intent = new Intent(this, CreateWorkout.class);
        startActivity(intent);
    }

    public void readMyWorkouts() {

        try {
            FileInputStream fileInputStream = openFileInput("my_workouts");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            String[] tokens;

            while ((line = bufferedReader.readLine()) != null) {
                tokens = line.split(";");

                //loads workout and stores it in myWorkouts vector
//                myWorkouts.add(new Workout(tokens[0], tokens[1]));
                //find better way to do this

                myWorkoutNamesList.add(tokens[0]);
            }

            displayMyWorkouts.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
