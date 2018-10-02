package com.apps.kb.myworkout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<String> {
    public MyListViewAdapter(Context context, List<String> workoutName) {
        super(context, 0, workoutName);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String workoutName = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_workout_listview_item, parent, false);
        }
        // Lookup view for data population
        TextView nameOfWorkout = convertView.findViewById(R.id.text_view_head);
        Typeface type = Typeface.createFromAsset(getContext().getAssets(),"fonts/neutra_textlight_alt_demi_32113.ttf");
        nameOfWorkout.setTypeface(type);
        // Populate the data into the template view using the data object
        nameOfWorkout.setText(workoutName);

        // Return the completed view to render on screen
        return convertView;
    }
}