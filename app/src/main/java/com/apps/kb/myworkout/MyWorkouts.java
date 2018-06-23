package com.apps.kb.myworkout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyWorkouts extends AppCompatActivity {
    private Button createWorkoutButton;
    private TextView displayMyWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_workouts);
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
            StringBuffer stringBuffer = new StringBuffer();
            String name;

            while ((name = bufferedReader.readLine()) != null) {
                stringBuffer.append(name +"\n");
            }

            displayMyWorkouts.setText(stringBuffer.toString());
            displayMyWorkouts.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
