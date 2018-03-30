package com.peoplecoachingworks.leapstudio20;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.peoplecoachingworks.leapstudio20.Data.TaskContract.TaskEntry;

public class AddKeyLesson2Activity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key_lesson2);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */

    public void onClickAddTask(View view) {
        // Read from input fields
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString().trim();
        if (input.length() == 0) {
            return;
        }

        // Create a ContentValues object where column names are the keys, and pet attributes from the editor are the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskEntry.COLUMN_PRIORITY, mPriority);

        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getContentResolver().insert(TaskEntry.CONTENT_URI, contentValues);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(getApplicationContext(), getString(R.string.dialog_insert_quote_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(getApplicationContext(), getString(R.string.dialog_insert_quote_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }
}
