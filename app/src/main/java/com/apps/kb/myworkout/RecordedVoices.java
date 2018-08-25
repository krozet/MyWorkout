package com.apps.kb.myworkout;

<<<<<<< HEAD
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
=======
>>>>>>> parent of c92c961... Big Navigation Update
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
<<<<<<< HEAD

        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);
        record = (Button) findViewById(R.id.record);
        confirm = (Button) findViewById(R.id.confirm);

        stop.setEnabled(false);
        play.setEnabled(false);

        outputFile = (getFilesDir() + "/recording.3gp");

        record.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(outputFile);
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    startRecord = System.currentTimeMillis();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    System.out.println("PROBLEM HERE");
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
            }
        }));

        stop.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                stop.setEnabled(false);
                play.setEnabled(true);
                record.setEnabled(true);
                stopRecord = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), "Audio Recorder Successful", Toast.LENGTH_LONG).show();
            }
        }));

        play.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    recordingLength = stopRecord - startRecord;
                    final MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(outputFile);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    play.setEnabled(false);
                    record.setEnabled(false);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishPlay(mediaPlayer);
                        }
                    }, recordingLength);
                }

                catch (IOException e)
                {
                    System.out.println("Problem in play OnClick");
                }
            }
        }));

        confirm.setOnClickListener((new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmPressed = true;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("outputFile", outputFile);
                System.out.println("outputFile inside RecordedVoices: " + outputFile);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }));

        //thread.setRunning(true);
        //thread.start();
    }

    public static void finishPlay(MediaPlayer mediaPlayer)
    {
        record.setEnabled(true);
        play.setEnabled(true);
    }

    @Override
    public void onBackPressed()
    {
        if(confirmPressed)
        {
            System.out.println("CONFIRMED");
            setResult(RESULT_OK);
            super.onBackPressed();
        }
        else
        {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
=======
>>>>>>> parent of c92c961... Big Navigation Update
    }
}
