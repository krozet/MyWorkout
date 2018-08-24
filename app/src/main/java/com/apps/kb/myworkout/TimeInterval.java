package com.apps.kb.myworkout;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TimeInterval implements Parcelable{
    private String name;
    private String pathToBeginningAudio;
    private String pathToEndingAudio;
    private String pathToBackgroundImage;
    private String backgroundColor;
    private String backgroundText;
    private boolean endingAlert;
    private int minutes;
    private int seconds;

    private String emptyObj = "\"\";\"\";\"\";\"\";\"\";\"\";false;0;0";

    public TimeInterval(String obj) {
        parseString(obj);
    }

    public TimeInterval() {
        parseString(emptyObj);
    }

    protected TimeInterval(Parcel in) {
        name = in.readString();
        pathToBeginningAudio = in.readString();
        pathToEndingAudio = in.readString();
        pathToBackgroundImage = in.readString();
        backgroundColor = in.readString();
        backgroundText = in.readString();
        endingAlert = in.readByte() != 0;
        minutes = in.readInt();
        seconds = in.readInt();
        emptyObj = in.readString();
    }

    public static final Creator<TimeInterval> CREATOR = new Creator<TimeInterval>() {
        @Override
        public TimeInterval createFromParcel(Parcel in) {
            return new TimeInterval(in);
        }

        @Override
        public TimeInterval[] newArray(int size) {
            return new TimeInterval[size];
        }
    };

    //name, pathToBeginningAudio, pathToEndingAudio, pathToBackgroundImage, backgroundColor, backgroundText, endingAlert, minutes, seconds
    private void parseString(String obj) {
        String[] tokens = obj.split(";");

        if(tokens.length == 9) {
            name = tokens[0];
            pathToBeginningAudio = tokens[1];
            pathToEndingAudio = tokens[2];
            pathToBackgroundImage = tokens[3];
            backgroundColor = tokens[4];
            backgroundText = tokens[5];
            endingAlert = Boolean.parseBoolean(tokens[6]);
            minutes = Integer.parseInt(tokens[7]);
            seconds = Integer.parseInt(tokens[8]);
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

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPathToBeginningAudio(String pathToBeginningAudio) {
        this.pathToBeginningAudio = pathToBeginningAudio;
    }

    public void setPathToEndingAudio(String pathToEndingAudio) {
        this.pathToEndingAudio = pathToEndingAudio;
    }

    public void setPathToBackgroundImage(String pathToBackgroundImage) {
        this.pathToBackgroundImage = pathToBackgroundImage;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundText(String backgroundText) {
        this.backgroundText = backgroundText;
    }

    public void setEndingAlert(boolean endingAlert) {
        this.endingAlert = endingAlert;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(pathToBeginningAudio);
        dest.writeString(pathToEndingAudio);
        dest.writeString(pathToBackgroundImage);
        dest.writeString(backgroundColor);
        dest.writeString(backgroundText);
        dest.writeByte((byte) (endingAlert ? 1 : 0));
        dest.writeInt(minutes);
        dest.writeInt(seconds);
        dest.writeString(emptyObj);
    }
}
