package com.apps.kb.myworkout;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.Toast;
=======
import android.widget.NumberPicker;
>>>>>>> master

import com.google.gson.Gson;

public class AddTimeInterval extends AppCompatActivity {
    Button addStartVoiceButton, addEndVoiceButton;
    TimeInterval timeInterval;
    NumberPicker minutesNumberPicker, secondsNumberPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_interval);
        timeInterval = new TimeInterval();
        timeInterval.setName("fuck brad");

        minutesNumberPicker = findViewById(R.id.minutes_number_picker);
        secondsNumberPicker = findViewById(R.id.seconds_number_picker);
        setNumberPickers();

        minutesNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                timeInterval.setMinutes(newVal);
            }
        });

        secondsNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                timeInterval.setSeconds(newVal);
            }
        });

        addStartVoiceButton = findViewById(R.id.start_voice);
        addStartVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });

        addEndVoiceButton = findViewById(R.id.end_voice);
        addEndVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });
    }

    private void setNumberPickers() {
        minutesNumberPicker.setMaxValue(59);
        minutesNumberPicker.setMinValue(0);
        minutesNumberPicker.setValue(0);
        secondsNumberPicker.setMaxValue(59);
        secondsNumberPicker.setMinValue(0);
        secondsNumberPicker.setValue(30);
    }

    private void openAddTimeInterval() {
        Intent intent = new Intent(this, RecordedVoices.class);
        Gson gson = new Gson();
        String timeIntervalAsString = gson.toJson(timeInterval);
        intent.putExtra("timeIntervalAsString", timeIntervalAsString);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String toastString = "Request Code: " + requestCode + " Result Code: " + resultCode;
        //Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
        if(resultCode == RESULT_OK)
        {
            Toast.makeText(getApplicationContext(), "Voice Added", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

}
