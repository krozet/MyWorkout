package com.apps.kb.myworkout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import com.google.gson.Gson;

import java.lang.reflect.Field;

public class AddTimeInterval extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private int currentBackgroundColor = 0xffffffff;
    private int currentTextColor = 0xffffffff;
    private boolean fsAlert;
    private ScrollView root;

    Button addStartVoiceButton, addEndVoiceButton, addBackgroundImage, addBackgroundColorButton, addTextColorButton;
    TimeInterval timeInterval;
    NumberPicker minutesNumberPicker, secondsNumberPicker;
    TextView selectTime, colon, displayMessage;
    EditText displayMessageInput;
    Switch fiveSecondAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_interval);
        root = findViewById(R.id.color_screen);
        timeInterval = new TimeInterval();
        timeInterval.setName("fuck brad");

        // select time
        selectTime = findViewById(R.id.select_time);
        colon = findViewById(R.id.colon);

        // minutes and seconds
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

        // start voice
        addStartVoiceButton = findViewById(R.id.start_voice);
        addStartVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });

        // end voice
        addEndVoiceButton = findViewById(R.id.end_voice);
        addEndVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });

        // background color picker
        addBackgroundColorButton = findViewById(R.id.background_color);
        addBackgroundColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddTimeInterval.this;
                ColorPickerDialogBuilder
                        .with(context, R.style.ColorPickerDialogTheme)
                        .setTitle("Choose color")
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                // remove this later
                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                changeBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        // text color picker
        addTextColorButton = findViewById(R.id.text_color);
        addTextColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddTimeInterval.this;
                ColorPickerDialogBuilder
                        .with(context, R.style.ColorPickerDialogTheme)
                        .setTitle("Choose color")
                        .initialColor(currentTextColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                // remove this later
                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                changeTextColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        // display message
        displayMessage = findViewById(R.id.display_message);
        displayMessageInput = findViewById(R.id.display_message_input);
        // save the value of the message
        //displayMessageInput.getText().toString();

        // switch
        fiveSecondAlert = findViewById(R.id.five_second_alert);
        fiveSecondAlert.setOnCheckedChangeListener(this);

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

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void changeBackgroundColor(int selectedColor) {
        currentBackgroundColor = selectedColor;

        // convert color the hsv from int
        float[] hsv = new float[3];
        Color.colorToHSV(currentBackgroundColor, hsv);

        if (hsv[2] > .5)
            // make the color darker
            hsv[2] *= 0.75f;
        else
            // make the color lighter
            hsv[2] = 1.0f - 0.75f * (1.0f - hsv[2]);

        int secondaryBackgroundColor = Color.HSVToColor(hsv);

        // gradient effect
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {selectedColor, secondaryBackgroundColor});
        gd.setCornerRadius(0f);

        root.setBackgroundDrawable(gd);
    }

    private void changeTextColor(int selectedColor) {
        currentTextColor = selectedColor;
        displayMessage.setTextColor(selectedColor);
        displayMessageInput.setTextColor(selectedColor);
        selectTime.setTextColor(selectedColor);
        colon.setTextColor(selectedColor);
        fiveSecondAlert.setTextColor(selectedColor);
        setNumberPickerTextColor(minutesNumberPicker, selectedColor);
        setNumberPickerTextColor(secondsNumberPicker, selectedColor);
//        root.setBackgroundColor(selectedColor);
    }

    private static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    e.printStackTrace();
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerTextClr", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerTextClr", e);
                }
            }
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            fsAlert = isChecked;
    }
}
