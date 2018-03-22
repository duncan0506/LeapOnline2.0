package com.peoplecoachingworks.leapstudio20;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peoplecoachingworks.leapstudio20.Data.GoalTipsContract.GoalTipsEntry;
import com.peoplecoachingworks.leapstudio20.Data.GoalTipsDBHelper;


public class FragmentGoalTips extends Fragment implements DialogAddGoal.DialogAddGoalListener {

    private TextView tvQuote, tvAuthor, displayView;
    private FloatingActionButton fabAddGoal;
    private GoalTipsDBHelper mDbHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //Set menu options for FragmentGoalTips

        getActivity().setTitle("Goal Achievement Tips"); //Set title for Fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal_tips, container, false);

        tvQuote = view.findViewById(R.id.tvQuote);
        tvAuthor = view.findViewById(R.id.tvAuthor);
        fabAddGoal = view.findViewById(R.id.fabAddGoal);
        displayView = view.findViewById(R.id.text_view_pet);

        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //access database - instantiating subclass of SQLiteOpenHelper
        mDbHelper = new GoalTipsDBHelper(getActivity().getApplicationContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets" to get a Cursor that contains all rows from the pets table.
        Cursor cursor = db.rawQuery("SELECT * FROM " + GoalTipsEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor

            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {

            cursor.close();
        }
    }


    public void openDialog() {
        DialogAddGoal dialogAddGoal = new DialogAddGoal();
        dialogAddGoal.show(getFragmentManager(), "dialog add goal");
        dialogAddGoal.setTargetFragment(FragmentGoalTips.this, 1);
    }

    @Override
    public void applyText(String quote, String author) {
        tvQuote.setText(quote);
        tvAuthor.setText(author);
    }


    @Override //Inflate the menu
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_goal_tips_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //TODO: Click events in toolbar overflow menu not working
    @Override //Handle clicks event
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                //insertGoal();
                displayDatabaseInfo();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
