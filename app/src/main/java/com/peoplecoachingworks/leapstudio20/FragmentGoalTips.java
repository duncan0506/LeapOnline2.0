package com.peoplecoachingworks.leapstudio20;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ListView;

import com.peoplecoachingworks.leapstudio20.Data.GoalTipsContract.GoalTipsEntry;
import com.peoplecoachingworks.leapstudio20.Data.GoalTipsCursorAdapter;
import com.peoplecoachingworks.leapstudio20.Data.GoalTipsDbHelper;


public class FragmentGoalTips extends Fragment implements DialogAddGoal.DialogAddGoalListener {

    private FloatingActionButton fabAddGoal;
    private GoalTipsDbHelper mDbHelper;
    private ListView goalListView;


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

        fabAddGoal = view.findViewById(R.id.fabAddGoal);
        goalListView = view.findViewById(R.id.goal_list);


        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //access database - instantiating subclass of SQLiteOpenHelper
        mDbHelper = new GoalTipsDbHelper(getActivity().getApplicationContext());

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {


        String[] projection = {
                GoalTipsEntry._ID,
                GoalTipsEntry.COLUMN_QUOTE,
                GoalTipsEntry.COLUMN_AUTHOR
        };

        // Query the database from ContentProvider (use getActivity().getContentResolver() for fragment)
        Cursor cursor = getActivity().getContentResolver().query(
                GoalTipsEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        GoalTipsCursorAdapter adapter = new GoalTipsCursorAdapter(getActivity(), cursor);
        goalListView.setAdapter(adapter);
    }


    public void openDialog() {
        DialogAddGoal dialogAddGoal = new DialogAddGoal();
        dialogAddGoal.show(getFragmentManager(), "dialog add goal");
        dialogAddGoal.setTargetFragment(FragmentGoalTips.this, 1);
    }

    @Override
    public void applyText(String quote, String author) {

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
                insertGoal();
                displayDatabaseInfo();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertGoal() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(GoalTipsEntry.COLUMN_QUOTE, "Toto");
        values.put(GoalTipsEntry.COLUMN_AUTHOR, "Terrier");

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getActivity().getContentResolver().insert(GoalTipsEntry.CONTENT_URI, values);

    }

}
