package com.apps.kb.myworkout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
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
    public boolean done;

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

        startTime = 5000;
        remainingTime = startTime;
        timer.setBase(SystemClock.elapsedRealtime() + startTime);
        timerRunning = false;
        workoutThread.setRunning(true);
        workoutThread.start();
        done = false;
    }

    private void startStopClick()
    {
        if (!timerRunning) {
            if (done) {
                done = false;
            }
            timer.setBase(SystemClock.elapsedRealtime() + remainingTime);
            timer.start();
            lastTimePressed = SystemClock.elapsedRealtime();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    startstop.setText("STOP");
                }
            });
            timerRunning = true;
        } else {
            timer.stop();
            remainingTime = remainingTime - (SystemClock.elapsedRealtime() - lastTimePressed);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    startstop.setText("START");
                }
            });
            timerRunning = false;
        }
    }

    public void timerDone()
    {
        done = true;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                timer.setText("DONE!");
            }
        });

        timer.stop();

        handler.post(new Runnable() {
            public void run() {
                startstop.setText("RESTART");
            }
        });

        remainingTime = startTime;
        timerRunning = false;
    }

    public String checkTimer()
    {
        return timer.getText().toString();
    }
}
