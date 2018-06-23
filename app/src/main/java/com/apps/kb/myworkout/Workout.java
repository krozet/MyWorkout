package com.apps.kb.myworkout;

import java.util.LinkedList;
import java.util.Queue;

public class Workout {
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
}
