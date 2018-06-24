package com.apps.kb.myworkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

public class RecordedVoices extends AppCompatActivity {
    Button clickMe;
    TimeInterval timeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_voices);

        //get time interval object from create workout
        Gson gson = new Gson();
        String timeIntervalAsString = getIntent().getStringExtra("timeIntervalAsString");
        timeInterval = gson.fromJson(timeIntervalAsString, TimeInterval.class);

        clickMe = findViewById(R.id.click_me);
        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), timeInterval.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
