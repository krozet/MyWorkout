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
        //workoutActivity.loadWorkout();
    }


    @Override
    public void run()
    {
        while (running)
        {
            if (workoutActivity.checkTimer() != null && workoutActivity.getTimeRemaining() <= 0
                    && !workoutActivity.done && workoutActivity.isTimerRunning())
            {
                workoutActivity.timerDone();
                //workoutActivity.incrementTimeInterval();
            }

            else if(workoutActivity.checkTimer() != null && workoutActivity.getTimeRemaining() <=
                    workoutActivity.getNextIntervalTime() && !workoutActivity.done
                    && workoutActivity.isTimerRunning() && !workoutActivity.isIncrementing()
                    && !workoutActivity.isResetting())
            {
                workoutActivity.incrementTimeInterval();
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
