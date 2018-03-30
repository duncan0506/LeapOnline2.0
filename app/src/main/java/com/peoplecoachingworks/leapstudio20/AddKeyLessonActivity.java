package com.peoplecoachingworks.leapstudio20;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.peoplecoachingworks.leapstudio20.Data.KeyLessonContract.KeyLessonEntry;
import com.peoplecoachingworks.leapstudio20.Data.KeyLessonDbHelper;


public class AddKeyLessonActivity extends AppCompatActivity {


    private EditText mKeyLessonEditText;
    private EditText mActionStepEditText;
    private Button mDateButton;
    private Button mTimeButton;
    private Spinner mPrioritySpinner;
    private EditText mAddNoteEditText;
    private FloatingActionButton mSaveKeyLessonFAB;


    private int mPriority = KeyLessonEntry.PRIORITY_LOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key_lesson);

        // Find all relevant views that we will need to read user input from
        mKeyLessonEditText = findViewById(R.id.etKeyLesson);
        mActionStepEditText = findViewById(R.id.etActionStep);
        mDateButton = findViewById(R.id.btnDatePicker);
        mTimeButton = findViewById(R.id.btnTimePicker);
        mPrioritySpinner = findViewById(R.id.spinner_priority);
        mAddNoteEditText = findViewById(R.id.etAddNote);
        mSaveKeyLessonFAB = findViewById(R.id.fabSaveKeyLesson);

        mSaveKeyLessonFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertKeyLesson();
                finish();
            }
        });

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_priority_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mPrioritySpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.priority_mid))) {
                        mPriority = KeyLessonEntry.PRIORITY_MEDIUM;
                    } else if (selection.equals(getString(R.string.priority_high))) {
                        mPriority = KeyLessonEntry.PRIORITY_HIGH;
                    } else if (selection.equals(getString(R.string.priority_critical))) {
                        mPriority = KeyLessonEntry.PRIORITY_CRITICAL;
                    } else {
                        mPriority = KeyLessonEntry.PRIORITY_LOW;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPriority = KeyLessonEntry.PRIORITY_LOW;
            }
        });
    }

    /**
     * Get user input from editor and save new pet into database.
     */
    private void insertKeyLesson() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String keyLessonString = mKeyLessonEditText.getText().toString().trim();
        String actionStepString = mActionStepEditText.getText().toString().trim();
        String dateString = mDateButton.getText().toString().trim();
        String timeString = mTimeButton.getText().toString().trim();
        String addNoteString = mAddNoteEditText.getText().toString().trim();

        // Create database helper
        KeyLessonDbHelper mDbHelper = new KeyLessonDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(KeyLessonEntry.COLUMN_KEY_LESSON, keyLessonString);
        values.put(KeyLessonEntry.COLUMN_ACTION_STEP, actionStepString);
        values.put(KeyLessonEntry.COLUMN_DATE, dateString);
        values.put(KeyLessonEntry.COLUMN_TIME, timeString);
        values.put(KeyLessonEntry.COLUMN_PRIORITY, mPriority);
        values.put(KeyLessonEntry.COLUMN_ADD_NOTE, addNoteString);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(KeyLessonEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.fragment_key_lesson_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_add_dummy_data:
                // Save pet to database
                insertKeyLesson();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete_all:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
