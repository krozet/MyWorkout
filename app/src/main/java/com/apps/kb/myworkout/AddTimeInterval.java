package com.apps.kb.myworkout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

public class AddTimeInterval extends AppCompatActivity {
    Button addStartVoiceButton, addEndVoiceButton;
    TimeInterval timeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_interval);
        timeInterval = new TimeInterval();
        timeInterval.setName("fuck brad");

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
