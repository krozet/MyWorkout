package com.apps.kb.myworkout;

import android.content.Context;

public class WorkoutThread extends Thread
{
    private com.apps.kb.myworkout.StartWorkoutActivity workoutActivity;
    private boolean running;

    public WorkoutThread()
    {

    }

    public WorkoutThread(StartWorkoutActivity workoutActivity)
    {
        super();
        this.workoutActivity = workoutActivity;
    }


    @Override
    public void run()
    {
        while (running)
        {
            if (workoutActivity.checkTimer() != null && workoutActivity.checkTimer().equals("00:00")
                    && !workoutActivity.done)
            {
                workoutActivity.timerDone();
            }
        }
    }

    public boolean checkRunning()
    {
        return running;
    }
    public void setRunning(boolean isRunning)
    {
        running = isRunning;
    }
}
