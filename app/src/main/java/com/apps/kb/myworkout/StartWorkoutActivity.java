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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class StartWorkoutActivity extends AppCompatActivity {

    private Button startstop, debugButton;

    private Chronometer timer;
    private WorkoutThread workoutThread;
    private boolean timerRunning;
    public Workout workout;
    private long startTime, remainingTime, lastTimePressed;
    public boolean done;
    private String workoutName;

    private TextView currentIntervalText;
    public TimeInterval currentTimeInterval;
    public long nextIntervalTime;
    public int lastIntervalIndex;

    private List<String> timeIntervalViewList;
    private ListView workoutTimeIntervalView;

    private boolean incrementing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        incrementing = false;

        timeIntervalViewList = new ArrayList<>();

        workoutName = getIntent().getStringExtra("WORKOUT_NAME_ID");
        currentIntervalText = findViewById(R.id.currentInterval);

        workout = new Workout(workoutName);
        workout.load(getApplicationContext());



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

        debugButton = findViewById(R.id.debugButton);
        debugButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setCurrentIntervalText();
            }
        });

        workoutThread = new WorkoutThread(this);

        startTime = workout.totalTimeInMs();
        remainingTime = startTime;
        nextIntervalTime = startTime;
        setCurrentInterval(0);
        lastIntervalIndex = 0;
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

    public void incrementTimeInterval()
    {
        incrementing = true;
        lastIntervalIndex ++;
        if(lastIntervalIndex < workout.getSize())
        {
            setCurrentInterval(lastIntervalIndex);
        }
        incrementing = false;
    }

    public void setCurrentInterval(final int i)
    {
        nextIntervalTime -= workout.getTimeIntervalAt(i).getTotalTimeInMS();
        currentTimeInterval = workout.getTimeIntervalAt(i);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                currentIntervalText.setText(currentTimeInterval.getBackgroundText());
            }
        });
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

    public long getTimeRemaining() {return timer.getBase() - SystemClock.elapsedRealtime();}

    public boolean isTimerRunning() {return timerRunning;}

    public long getNextIntervalTime() {return nextIntervalTime;}

    public void loadWorkout() {workout.load(getApplicationContext());};

    public boolean isIncrementing() {return incrementing;}

    public void setCurrentIntervalText()
    {
        final long base2 = timer.getBase() - SystemClock.elapsedRealtime();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                //currentIntervalText.setText(Long.toString(base2));
                currentIntervalText.setText(Long.toString(nextIntervalTime));
            }
        });
    }
}
