package com.apps.kb.myworkout;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;
import java.util.Queue;

public class Workout implements Parcelable{
    private Queue<TimeInterval> queue;
    private String name;
    private String fileName;
    private boolean isLoaded;

    //creates a new Workout from name
    public Workout(String name) {
        this.name = name;
        fileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        queue = new LinkedList<>();
        isLoaded = false;
    }

    //used to load a workout
    public Workout(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
        load();
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
       queue.add(interval);
    }

    public String getName() {
        return name;
    }

    public void save() {

    }

    public void load() {
        isLoaded = true;
    }

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
}
