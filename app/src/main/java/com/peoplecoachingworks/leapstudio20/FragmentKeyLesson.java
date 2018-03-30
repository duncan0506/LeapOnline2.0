package com.peoplecoachingworks.leapstudio20;


import android.content.ContentValues;
import android.content.Intent;
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

import com.peoplecoachingworks.leapstudio20.Data.KeyLessonContract.KeyLessonEntry;
import com.peoplecoachingworks.leapstudio20.Data.KeyLessonDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class FragmentKeyLesson extends Fragment {

    TextView displayView;
    private KeyLessonDbHelper mDbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Key Lesson"); //Set title for Fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_key_lesson, container, false);


        displayView = view.findViewById(R.id.text_view_key_lesson);

        FloatingActionButton fab = view.findViewById(R.id.fabAddKeyLesson);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddKeyLessonActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new KeyLessonDbHelper(getActivity());

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

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                KeyLessonEntry._ID,
                KeyLessonEntry.COLUMN_KEY_LESSON,
                KeyLessonEntry.COLUMN_ACTION_STEP,
                KeyLessonEntry.COLUMN_DATE,
                KeyLessonEntry.COLUMN_TIME,
                KeyLessonEntry.COLUMN_PRIORITY,
                KeyLessonEntry.COLUMN_ADD_NOTE};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                KeyLessonEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order


        try {
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(KeyLessonEntry._ID + " - " +
                    KeyLessonEntry.COLUMN_KEY_LESSON + " - " +
                    KeyLessonEntry.COLUMN_ACTION_STEP + " - " +
                    KeyLessonEntry.COLUMN_DATE + " - " +
                    KeyLessonEntry.COLUMN_TIME + " - " +
                    KeyLessonEntry.COLUMN_PRIORITY + " - " +
                    KeyLessonEntry.COLUMN_ADD_NOTE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(KeyLessonEntry._ID);
            int keyLessonColumnIndex = cursor.getColumnIndex(KeyLessonEntry.COLUMN_KEY_LESSON);
            int actionStepColumnIndex = cursor.getColumnIndex(KeyLessonEntry.COLUMN_ACTION_STEP);
            int dateColumnIndex = cursor.getColumnIndex(KeyLessonEntry.COLUMN_DATE);
            int timeColumnIndex = cursor.getColumnIndex(KeyLessonEntry.COLUMN_TIME);
            int priorityColumnIndex = cursor.getColumnIndex(KeyLessonEntry.COLUMN_PRIORITY);
            int addNoteColumnIndex = cursor.getColumnIndex(KeyLessonEntry.COLUMN_ADD_NOTE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentKeyLesson = cursor.getString(keyLessonColumnIndex);
                String currentActionStep = cursor.getString(actionStepColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                int currentPriority = cursor.getInt(priorityColumnIndex);
                String currentAddNote = cursor.getString(addNoteColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentKeyLesson + " - " +
                        currentActionStep + " - " +
                        currentDate + " - " +
                        currentTime + " - " +
                        currentPriority + " - " +
                        currentAddNote));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertKeyLesson() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(KeyLessonEntry.COLUMN_KEY_LESSON, "Learn to be happy");
        values.put(KeyLessonEntry.COLUMN_ACTION_STEP, "Play Golf");
        values.put(KeyLessonEntry.COLUMN_DATE, "12pm");
        values.put(KeyLessonEntry.COLUMN_TIME, "12p");
        values.put(KeyLessonEntry.COLUMN_PRIORITY, KeyLessonEntry.PRIORITY_LOW);
        values.put(KeyLessonEntry.COLUMN_ADD_NOTE, "woohoo");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(KeyLessonEntry.TABLE_NAME, null, values);
    }

    @Override //Inflate the menu
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_key_lesson_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //TODO: Click events in toolbar overflow menu not working
    @Override //Handle clicks event
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                insertKeyLesson();
            case R.id.action_delete_all:
                // Do nothing for now
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
