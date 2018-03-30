package com.peoplecoachingworks.leapstudio20;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.peoplecoachingworks.leapstudio20.Data.KeyLessonContract.KeyLessonEntry;

import java.text.DateFormat;
import java.util.Calendar;

public class AddKeyLessonActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_KEY_LESSON_LOADER = 0;
    Button btnDatePicker, btnTimePicker;
    int year, month, day, hour, minute;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private Uri mCurrentKeyLessonUri;
    private EditText mKeyLessonEditText;
    private EditText mActionStepEditText;
    private Spinner mPrioritySpinner;
    private EditText mAddNoteEditText;
    private int mPriority = KeyLessonEntry.PRIORITY_LOW;
    private boolean mKeyLessonHasChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mKeyLessonHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key_lesson);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mCurrentKeyLessonUri = intent.getData();

        if (mCurrentKeyLessonUri == null) {
            // This is a new pet, so change the app bar to say "Add a Pet"
            setTitle(getString(R.string.addKeyLesson_activity_title_add_key_lesson));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.addKeyLesson_activity_title_edit_key_lesson));

            getLoaderManager().initLoader(EXISTING_KEY_LESSON_LOADER, null, this);
        }

        mKeyLessonEditText = findViewById(R.id.etKeyLesson);
        mActionStepEditText = findViewById(R.id.etActionStep);
        mPrioritySpinner = findViewById(R.id.spinner_priority);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnTimePicker = findViewById(R.id.btnTimePicker);
        mAddNoteEditText = findViewById(R.id.etAddNote);


        mKeyLessonEditText.setOnTouchListener(mTouchListener);
        mActionStepEditText.setOnTouchListener(mTouchListener);
        mPrioritySpinner.setOnTouchListener(mTouchListener);
        btnDatePicker.setOnTouchListener(mTouchListener);
        btnTimePicker.setOnTouchListener(mTouchListener);
        mAddNoteEditText.setOnTouchListener(mTouchListener);


        //btnDatePicker.setOnClickListener(this);
        //btnTimePicker.setOnClickListener(this);

        setupSpinner();

    }

    @Override
    public void onClick(View view) {

        if (view == btnDatePicker) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(AddKeyLessonActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                    btnDatePicker.setText(currentDateString);
                }
            }, year, month, day);
            datePickerDialog.show();
        } else if (view == btnTimePicker) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int minute = c.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(AddKeyLessonActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    btnTimePicker.setText(hourOfDay + " : " + minute);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter prioritySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_priority_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        prioritySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mPrioritySpinner.setAdapter(prioritySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.priority_medium))) {
                        mPriority = 1; // Mid
                    } else if (selection.equals(getString(R.string.priority_high))) {
                        mPriority = 2; // High
                    } else if (selection.equals(getString(R.string.priority_critical))) {
                        mPriority = 3; // critical
                    } else {
                        mPriority = 0; // Low
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPriority = 0; // Low
            }
        });
    }

    private void saveKeyLesson() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String keyLessonString = mKeyLessonEditText.getText().toString().trim();
        String actionStepString = mActionStepEditText.getText().toString().trim();
        String addNoteString = mAddNoteEditText.getText().toString().trim();

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentKeyLessonUri == null &&
                TextUtils.isEmpty(keyLessonString) && TextUtils.isEmpty(actionStepString) &&
                TextUtils.isEmpty(addNoteString) && mPriority == KeyLessonEntry.PRIORITY_LOW) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(KeyLessonEntry.COLUMN_KEY_LESSON, keyLessonString);
        values.put(KeyLessonEntry.COLUMN_ACTION_STEP, actionStepString);
        values.put(KeyLessonEntry.COLUMN_PRIORITY, mPriority);
        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        values.put(KeyLessonEntry.COLUMN_NOTE, addNoteString);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentKeyLessonUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(KeyLessonEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.success),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentKeyLessonUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.error),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.success),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_key_lesson_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentKeyLessonUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                saveKeyLesson();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                //showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mKeyLessonHasChanged) {
                    NavUtils.navigateUpFromSameTask(AddKeyLessonActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(AddKeyLessonActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                //showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mKeyLessonHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        //showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                KeyLessonEntry._ID,
                KeyLessonEntry.COLUMN_KEY_LESSON,
                KeyLessonEntry.COLUMN_ACTION_STEP,
                KeyLessonEntry.COLUMN_DATE,
                KeyLessonEntry.COLUMN_TIME,
                KeyLessonEntry.COLUMN_PRIORITY,
                KeyLessonEntry.COLUMN_NOTE};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentKeyLessonUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
