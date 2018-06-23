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
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateWorkout extends AppCompatActivity {
    private Button myWorkoutsButton;
    private EditText nameMyWorkout;

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

        myWorkoutsButton = findViewById(R.id.to_my_workouts);
        myWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeWorkout(v);
                openMyWorkouts();
            }
        });

    }

    public void openMyWorkouts() {
        Intent intent = new Intent(this, MyWorkouts.class);
        startActivity(intent);
    }

    public void writeWorkout(View view) {
        String name = nameMyWorkout.getText().toString();
        String file_name = "my_workouts";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
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
}
