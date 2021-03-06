package com.apps.kb.myworkout;

/*
 * A class used to hold all the TimeIntervals for the specific Workout.
 * Has methods to load all the TimeIntervals from the file system, or save all the TimeIntervals to the file system.
 * This class also implements Parcelable for passing between Activities.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Workout implements Parcelable{
    private ArrayList<TimeInterval> timeIntervalList;
    private String name;
    private String fileName;
    private boolean isLoaded;

    //creates a new Workout from name
    public Workout(String name) {
        this.name = name;
        fileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        timeIntervalList = new ArrayList<>();
        isLoaded = false;
    }

    //used to load a workout
    public Workout(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    protected Workout(Parcel in) {
        name = in.readString();
        fileName = in.readString();
        isLoaded = in.readByte() != 0;
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public void addTimeInterval(TimeInterval interval) {
        timeIntervalList.add(interval);
    }

    public String getName() {
        return name;
    }

    public void save(Context context) {
        context.deleteFile(fileName);
        for (int i=0; i < timeIntervalList.size(); i++)
            timeIntervalList.get(i).writeToFile(context);
    }

    public void load(Context context){
        timeIntervalList.clear();
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String timeIntervalString;

            while ((timeIntervalString = bufferedReader.readLine()) != null) {
                addTimeIntervalFromString(timeIntervalString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TimeInterval getTimeIntervalAt(int position) {
        if (position <= timeIntervalList.size()-1) {
            return timeIntervalList.get(position);
        }
        return null;
    }

    public void addTimeIntervalFromString(String timeIntervalString) {
        timeIntervalList.add(new TimeInterval(timeIntervalString));
    }

    public int getSize() { return timeIntervalList.size(); }

    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fileName);
        dest.writeByte((byte) (isLoaded ? 1 : 0));
    }

    public void printDetails() {
        System.out.println("Workout time intervals:");
        for (int i=0; i < timeIntervalList.size(); i++) {
            System.out.println(timeIntervalList.get(i));
        }
    }

    //Brad's method
    public long totalTimeInMs()
    {
        long totalTime = 0;
        for(int i=0; i < timeIntervalList.size(); i++)
        {
            totalTime += timeIntervalList.get(i).getTotalTimeInMS();
        }

        return totalTime;
    }

    public long totalTimeFromIndex(int i)
    {
        long totalTime = 0;
        for(int j = i; j < timeIntervalList.size(); j++)
        {
            totalTime += timeIntervalList.get(j).getTotalTimeInMS();
        }

        return totalTime;
    }

    public void remove(int position) {
        timeIntervalList.remove(position);
    }

    public void swap(int dragged, int target) {
        Collections.swap(timeIntervalList, dragged, target);
    }

    public void set(int position, TimeInterval ti) {
        timeIntervalList.set(position, ti);
    }
}
