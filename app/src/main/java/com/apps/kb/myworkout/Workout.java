package com.apps.kb.myworkout;

import java.util.LinkedList;
import java.util.Queue;

public class Workout {
    private Queue<TimeInterval> queue;
    private String name;
    private String fileName;
    private boolean isLoaded;

    public Workout(String name) {
        this.name = name;
        fileName = name.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        queue = new LinkedList<>();
        isLoaded = false;
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
