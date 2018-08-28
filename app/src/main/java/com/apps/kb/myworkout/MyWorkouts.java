package com.apps.kb.myworkout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import android.os.Build;
=======
import android.graphics.Typeface;
>>>>>>> master
=======
import android.graphics.Typeface;
import android.os.Build;
>>>>>>> development
=======
>>>>>>> parent of c92c961... Big Navigation Update
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MyWorkouts extends AppCompatActivity {
    private Button createWorkoutButton;
    private ListView displayMyWorkouts;
    private List<String> myWorkoutNamesList;
    private ArrayList<Workout> myWorkouts;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> development
    final int CREATE_WORKOUT = 1;
    final int OPEN_WORKOUT = 2;

    public FragmentManager fragmentManager = getSupportFragmentManager();
=======
>>>>>>> parent of c92c961... Big Navigation Update

<<<<<<< HEAD
=======
>>>>>>> master
=======
>>>>>>> development
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

    private void openCreateWorkout() {
        Intent intent = new Intent(this, CreateWorkout.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("myWorkouts", myWorkouts);
        intent.putExtras(bundle);
<<<<<<< HEAD
        startActivityForResult(intent, CREATE_WORKOUT);
    }

    private void openWorkout(int position)
    {
        Intent intent = new Intent(this, WorkoutActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        String workoutName = displayMyWorkouts.getAdapter().getItem(position).toString();
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        startActivityForResult(intent, OPEN_WORKOUT);
    }


    private void createDeleteAlert(int pos)
    {
        final int index = pos;
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MyWorkouts.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MyWorkouts.this);
        }
        builder.setTitle("Delete Workout")
                .setMessage("Are you sure you want to delete this workout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteWorkout(index);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        builder.show();
    }

/*    @Override
    public void onBackPressed()
    {
        System.out.println("BACK PRESSED");
        super.onBackPressed();
    }*/


    public void printMyWorkouts()
    {
        try {
            FileInputStream fileInputStream = openFileInput("my_workouts");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            String[] tokens;

            //myWorkoutNamesList.clear();

            int i = 1;

            while ((line = bufferedReader.readLine()) != null) {
                tokens = line.split(";");

                System.out.println("Line " + i + ": " + line);
                i++;
            }

            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkout(int pos)
    {
        String file_name = "my_workouts";
        String clr = "";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE);
            fileOutputStream.write(clr.getBytes());
            fileOutputStream.close();

            // delete Time Intervals as well by deleting the myWorkoutNamesList.get(pos) file
            if(new File(System.getProperty("user.dir"), myWorkoutNamesList.get(pos)).exists())
                if(!getApplicationContext().deleteFile( myWorkoutNamesList.get(pos)))
                    throw new IOException("Unable to delete file: " + myWorkoutNamesList.get(pos));

            fileOutputStream = openFileOutput(file_name, MODE_APPEND);
            myWorkoutNamesList.remove(pos);

            for (String s: myWorkoutNamesList)
            {
                s += "\n";
                fileOutputStream.write(s.getBytes());
            }


            fileOutputStream.close();

            readMyWorkouts();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
=======
        startActivity(intent);
>>>>>>> parent of c92c961... Big Navigation Update
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
