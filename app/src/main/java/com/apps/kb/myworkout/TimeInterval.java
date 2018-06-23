package com.apps.kb.myworkout;

import android.util.Log;

public class TimeInterval {
    private String name;
    private String pathToBeginningAudio;
    private String pathToEndingAudio;
    private String pathToBackgroundImage;
    private String backgroundColor;
    private String backgroundText;
    private boolean endingAlert;
    private double lengthOfTime;

    private String emptyObj = "\"\";\"\";\"\";\"\";\"\";\"\";false;0.0";

    public TimeInterval(String obj) {
        parseString(obj);
    }

    public TimeInterval() {
        parseString(emptyObj);
    }

    //name, pathToBeginningAudio, pathToEndingAudio, pathToBackgroundImage, backgroundColor, backgroundText, endingAlert, lengthOfTime
    private void parseString(String obj) {
        String[] tokens = obj.split(";");

        if(tokens.length == 8) {
            name = tokens[0];
            pathToBeginningAudio = tokens[1];
            pathToEndingAudio = tokens[2];
            pathToBackgroundImage = tokens[3];
            backgroundColor = tokens[4];
            backgroundText = tokens[5];
            endingAlert = Boolean.parseBoolean(tokens[6]);
            lengthOfTime = Double.parseDouble(tokens[7]);
        } else {
            Log.i("TimeInterval", "Not correct amount of arguments");
        }
    }

    public void update(String obj) {
        parseString(obj);
    }
    
    public String getName() {
        return name;
    }

    public String getPathToBeginningAudio() {
        return pathToBeginningAudio;
    }

    public String getPathToEndingAudio() {
        return pathToEndingAudio;
    }

    public String getPathToBackgroundImage() {
        return pathToBackgroundImage;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getBackgroundText() {
        return backgroundText;
    }

    public boolean isEndingAlertOn() {
        return endingAlert;
    }

    public double getLengthOfTime() {
        return lengthOfTime;
    }
}
