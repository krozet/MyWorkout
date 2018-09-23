package com.apps.kb.myworkout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class StartWorkoutActivity extends AppCompatActivity {

    private Button startstop;

    private Chronometer timer;
    private WorkoutThread workoutThread;
    private boolean timerRunning;

    private long startTime, remainingTime, lastTimePressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timer = findViewById(R.id.chronotimer);

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                timer = chronometerChanged;
            }
        });

        timer.setCountDown(true);
        //timer.setBase(SystemClock.elapsedRealtime()+30000);

        startstop = findViewById(R.id.startstop);
        startstop.setText("START");
        startstop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startStopClick();
            }
        });

        workoutThread = new WorkoutThread(this);

        remainingTime = 30 * 1000;
        timer.setBase(SystemClock.elapsedRealtime() + remainingTime);
        timerRunning = false;
        workoutThread.setRunning(true);
        workoutThread.start();
    }

    private void startStopClick()
    {
        if(!timerRunning)
        {
            timer.setBase(SystemClock.elapsedRealtime() + remainingTime);
            timer.start();
            lastTimePressed = SystemClock.elapsedRealtime();
            startstop.setText("STOP");
            timerRunning = true;
        }

        else
        {
            timer.stop();
            remainingTime = remainingTime - (SystemClock.elapsedRealtime() - lastTimePressed);
            System.out.println(remainingTime);
            startstop.setText("START");
            timerRunning = false;
        }
    }

    public void setTimerText()
    {
        timer.setText("DONE!");
        workoutThread.setRunning(false);
        timer.stop();
    }

    public String checkTimer()
    {
        return timer.getText().toString();
    }
}
