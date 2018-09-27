package com.apps.kb.myworkout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditWorkoutActivity extends AppCompatActivity
{
    private Button addTimeIntervalButton, cancelButton, saveButton;
    private RecyclerView editTimeIntervalView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private String navigationOrigin = "USER";
    private String workoutName;
    final int TIME_INTERVAL_REQUEST = 1;
    final int EDIT_TIME_INTERVAL_REQUEST = 2;
    final int PROGRAMMATIC_REQUEST = 3;
    private List<String> timeIntervalViewList;
    private TimeInterval timeInterval;
    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
        setupToolbar();
        setupRecyclerview();
        setupAddTimeIntervalButton();
        setupCancelButton();
        setupSaveButton();
    }

    private void setupSaveButton() {
        saveButton = findViewById(R.id.edit_workout_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkout();
                onBackPressed();
            }
        });
    }

    private void setupCancelButton() {
        cancelButton = findViewById(R.id.edit_workout_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    private void setupAddTimeIntervalButton() {
        addTimeIntervalButton = findViewById(R.id.add_time_interval2);
        addTimeIntervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddTimeInterval();
            }
        });
    }

    private void setupRecyclerview() {
        // recyclerview
        editTimeIntervalView = findViewById(R.id.edit_time_intervals);
        editTimeIntervalView.setVisibility(View.GONE);
        editTimeIntervalView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        editTimeIntervalView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(timeIntervalViewList);
        editTimeIntervalView.setAdapter(adapter);
        editTimeIntervalView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, editTimeIntervalView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), timeIntervalViewList.get(position), Toast.LENGTH_LONG).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // handles drag and drop, and left swipe to delete
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int positionDragged = viewHolder.getAdapterPosition();
                int positionTarget = target.getAdapterPosition();

                // swap positions for workout obj and recyclerview
                Collections.swap(timeIntervalViewList, positionDragged, positionTarget);
                workout.swap(positionDragged, positionTarget);
                adapter.notifyItemMoved(positionDragged, positionTarget);
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    removeAt(position);
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    int itemHeight = itemView.getBottom() - itemView.getTop();
                    boolean isCanceled = dX == 0f && !isCurrentlyActive;

                    if (isCanceled) {
                        clearCanvas(c, (float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        return;
                    }

                    // Draw the red delete background
                    ColorDrawable background = new ColorDrawable();
                    background.setColor(Color.parseColor("#f44336"));
                    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    background.draw(c);

                    // Calculate position of delete icon
                    Drawable deleteIcon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_delete_white_24);
                    int intrinsicWidth = deleteIcon.getIntrinsicWidth();
                    int intrinsicHeight = deleteIcon.getIntrinsicHeight();
                    int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                    int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
                    int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                    int deleteIconRight = itemView.getRight() - deleteIconMargin;
                    int deleteIconBottom = deleteIconTop + intrinsicHeight;

                    // Draw the delete icon
                    deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                    deleteIcon.draw(c);

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            private void removeAt(int position) {
                timeIntervalViewList.remove(position);
                workout.remove(position);
                adapter.notifyItemRemoved(position);
            }

            private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
                Paint clearPaint = new Paint();
                clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                c.drawRect(left, top, right, bottom, clearPaint);
            }
        });
        helper.attachToRecyclerView(editTimeIntervalView);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit " + workoutName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        timeIntervalViewList = new ArrayList<>();
        workoutName = getIntent().getStringExtra("WORKOUT_NAME_ID");
        workout  = new Workout(workoutName);
        workout.load(getApplicationContext());
        setContentView(R.layout.activity_edit_workout);

        if ((getIntent().getStringExtra("NAVIGATION_ORIGIN_ID")).equals("CREATE_WORKOUT"))
        {
            navigationOrigin = "CREATE_WORKOUT";
            openAddTimeIntervalProgrammatically();
        }
    }

    private void saveWorkout() {
        workout.save(getApplicationContext());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
//        System.out.println("REQUEST CODE: " + requestCode + " RESULT CODE: " + resultCode);
        switch(requestCode) {
            case PROGRAMMATIC_REQUEST:
                if (resultCode == RESULT_CANCELED) {
                    onBackPressed();
                }

                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                }
            case TIME_INTERVAL_REQUEST:
                if (resultCode == RESULT_OK) {
                    timeInterval = data.getParcelableExtra("timeInterval");
                    if (timeInterval != null) {
                        workout.addTimeInterval(timeInterval);
                    }
                }
        }
    }

    private void openAddTimeInterval() {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivityForResult(intent, TIME_INTERVAL_REQUEST);
    }

    private void openAddTimeIntervalToEdit() {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivityForResult(intent, EDIT_TIME_INTERVAL_REQUEST);
    }

    private void openAddTimeIntervalProgrammatically()
    {
        Intent intent = new Intent(this, AddTimeInterval.class);
        intent.putExtra("NAVIGATION_ORIGIN_ID", navigationOrigin);
        intent.putExtra("WORKOUT_NAME_ID", workoutName);
        navigationOrigin = "USER";
        startActivityForResult(intent, PROGRAMMATIC_REQUEST);
    }

    private void populateView(){
        timeIntervalViewList.clear();

        for (int i=0; i < workout.getSize(); i++) {
             timeIntervalViewList.add(workout.getTimeIntervalAt(i).getViewDetails());
        }

        // specify an adapter (see also next example)
        adapter = new MyAdapter(timeIntervalViewList);
        editTimeIntervalView.setAdapter(adapter);

        editTimeIntervalView.setVisibility(View.VISIBLE);

//        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeIntervalViewList);
//        editTimeIntervalView.setAdapter(adapter);
//        editTimeIntervalView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//             {
////              openWorkout(position);
//            }
//        });
//
//        editTimeIntervalView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                String object = (String) adapter.getItem(position);
////              createDeleteAlert(position);
//                return true;
//            }
//        });

    }
}
