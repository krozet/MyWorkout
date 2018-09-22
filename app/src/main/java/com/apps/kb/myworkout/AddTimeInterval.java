package com.apps.kb.myworkout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class AddTimeInterval extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_START_VOICE = 2;
    private final int PROGRAMMATIC_REQUEST = 3;
    private static final int RESULT_END_VOICE = 4;

    // time interval values
    private String name;
    private String pathToBeginningAudio = "";
    private String pathToEndingAudio = "";
    private String backgroundText = "";
    private int minutes = 0;
    private int seconds = 30;
    private int primaryBackgroundColor = 0xffffffff;
    private int secondaryBackgroundColor = 0xffffffff;
    private int textColor = 0xffffffff;
    private String pathToBackgroundImage = "";
    private boolean endingAlert;

    // widgets
    private ScrollView root;
    Button addStartVoiceButton, addEndVoiceButton, addBackgroundImageButton, addBackgroundColorButton, addTextColorButton, saveButton, cancelButton;
    TimeInterval timeInterval;
    NumberPicker minutesNumberPicker, secondsNumberPicker;
    TextView selectTime, colon, displayMessage;
    EditText displayMessageInput;
    Switch fiveSecondAlert;
    ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_interval);
        root = findViewById(R.id.color_screen);
        timeInterval = new TimeInterval();
        timeInterval.setName("fuck keawa");

        setupName();
        setupSelectTime();
        setupStartVoice();
        setupEndVoice();
        setupBackgroundImage();
        setupBackgroundColorPicker();
        setupTextColorPicker();
        setupDisplayMessage();
        setupFiveSecondAlert();
        setupSave();
        setupCancel();
    }

    private void setupCancel() {
        cancelButton = findViewById(R.id.ti_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupSave() {
        saveButton = findViewById(R.id.ti_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                writeWorkout(v);
                createTimeInterval();
                setResult(RESULT_OK);
                onBackPressed();
            }
        });
    }

    private void setupFiveSecondAlert() {
        // switch
        fiveSecondAlert = findViewById(R.id.five_second_alert);
        fiveSecondAlert.setOnCheckedChangeListener(this);
    }

    private void setupDisplayMessage() {
        // display message
        displayMessage = findViewById(R.id.display_message);
        displayMessageInput = findViewById(R.id.display_message_input);
    }

    private void setupTextColorPicker() {
        // text color picker
        addTextColorButton = findViewById(R.id.text_color);
        addTextColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddTimeInterval.this;
                ColorPickerDialogBuilder
                        .with(context, R.style.ColorPickerDialogTheme)
                        .setTitle("Choose color")
                        .initialColor(textColor)
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
    }

    private void setupBackgroundColorPicker() {
        // background color picker
        addBackgroundColorButton = findViewById(R.id.background_color);
        addBackgroundColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = AddTimeInterval.this;
                ColorPickerDialogBuilder
                        .with(context, R.style.ColorPickerDialogTheme)
                        .setTitle("Choose color")
                        .initialColor(primaryBackgroundColor)
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
    }

    private void setupBackgroundImage() {
        // background image
        backgroundImageView = findViewById(R.id.background_image_view);
        addBackgroundImageButton = findViewById(R.id.background_image);
        addBackgroundImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                }

                Intent media = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(media, RESULT_LOAD_IMAGE);
            }
        });
    }

    private void setupEndVoice() {
        // end voice
        addEndVoiceButton = findViewById(R.id.end_voice);
        addEndVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartRecordedVoice();
            }
        });
    }

    private void setupStartVoice() {
        // start voice
        addStartVoiceButton = findViewById(R.id.start_voice);
        addStartVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEndRecordedVoice();
            }
        });
    }

    private void setupSelectTime() {
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
                minutes = newVal;
            }
        });

        secondsNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                seconds = newVal;
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

    private void openStartRecordedVoice() {
        Intent intent = new Intent(this, RecordedVoices.class);
        Gson gson = new Gson();
        String timeIntervalAsString = gson.toJson(timeInterval);
        intent.putExtra("timeIntervalAsString", timeIntervalAsString);
        startActivityForResult(intent, RESULT_START_VOICE);
    }

    private void openEndRecordedVoice() {
        Intent intent = new Intent(this, RecordedVoices.class);
        Gson gson = new Gson();
        String timeIntervalAsString = gson.toJson(timeInterval);
        intent.putExtra("timeIntervalAsString", timeIntervalAsString);
        startActivityForResult(intent, RESULT_END_VOICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case RESULT_LOAD_IMAGE:
                if(resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    pathToBackgroundImage = cursor.getString(columnIndex);
                    cursor.close();
                    backgroundImageView.setImageBitmap(BitmapFactory.decodeFile(pathToBackgroundImage));
                }
                break;
            case RESULT_START_VOICE:
                if(resultCode == RESULT_OK)
                {
                    Toast.makeText(getApplicationContext(), "Voice Added", Toast.LENGTH_LONG).show();
                    pathToBeginningAudio = data.getStringExtra("outputFile");
                    System.out.println("outputFile inside AddTimeInterval: " + pathToBeginningAudio);
                }
                break;
            case RESULT_END_VOICE:
                if(resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Voice Added", Toast.LENGTH_LONG).show();
                    pathToEndingAudio = data.getStringExtra("outputFile");
                    System.out.println("outputFile inside AddTimeInterval: " + pathToEndingAudio);
                }
            case PROGRAMMATIC_REQUEST:
                {
                    if(resultCode == RESULT_CANCELED)
                    {
                        onBackPressed();
                    }
                }
                break;
        }
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void changeBackgroundColor(int selectedColor) {
        primaryBackgroundColor = selectedColor;

        // convert color the hsv from int
        float[] hsv = new float[3];
        Color.colorToHSV(primaryBackgroundColor, hsv);

        if (hsv[2] > .5)
            // make the color darker
            hsv[2] *= 0.75f;
        else
            // make the color lighter
            hsv[2] = 1.0f - 0.75f * (1.0f - hsv[2]);

        secondaryBackgroundColor = Color.HSVToColor(hsv);

        // gradient effect
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {selectedColor, secondaryBackgroundColor});
        gd.setCornerRadius(0f);

        root.setBackgroundDrawable(gd);
    }

    private void changeTextColor(int selectedColor) {
        textColor = selectedColor;
        displayMessage.setTextColor(selectedColor);
        displayMessageInput.setTextColor(selectedColor);
        selectTime.setTextColor(selectedColor);
        colon.setTextColor(selectedColor);
        fiveSecondAlert.setTextColor(selectedColor);
        setNumberPickerTextColor(minutesNumberPicker, selectedColor);
        setNumberPickerTextColor(secondsNumberPicker, selectedColor);
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
            endingAlert = isChecked;
    }

    // Do not use - will attempt to write name of workout twice
//    public void writeWorkout(View view) {
//        String nameToWrite = name + "\n";
//        String file_name = "my_workouts";
//        try {
//            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_APPEND);
//            fileOutputStream.write(nameToWrite.getBytes());
//            fileOutputStream.close();
//            Toast.makeText(getApplicationContext(), "My workout saved", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setupName() {
        name = getIntent().getStringExtra("name");
        System.out.println("name: " + name);
    }

    public void createTimeInterval() {
        TimeInterval ti = new TimeInterval();
        backgroundText = displayMessageInput.getText().toString();

        ti.setName(name);
        ti.setPathToBeginningAudio(pathToBeginningAudio);
        ti.setPathToEndingAudio(pathToEndingAudio);
        ti.setPathToBackgroundImage(pathToBackgroundImage);
        ti.setPrimaryBackgroundColor(primaryBackgroundColor);
        ti.setSecondaryBackgroundColor(secondaryBackgroundColor);
        ti.setTextColor(textColor);
        ti.setBackgroundText(backgroundText);
        ti.setEndingAlert(endingAlert);
        ti.setMinutes(minutes);
        ti.setSeconds(seconds);
        System.out.println(ti.toString());
//        ti.writeToFile(getApplicationContext());
    }
}
