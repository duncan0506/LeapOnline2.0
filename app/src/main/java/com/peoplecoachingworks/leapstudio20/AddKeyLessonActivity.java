package com.peoplecoachingworks.leapstudio20;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AddKeyLessonActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    Button btnDatePicker, btnTimePicker;
    int year, month, day, hour, minute;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private Spinner mPrioritySpinner;
    private int mPriority = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key_lesson);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnTimePicker = findViewById(R.id.btnTimePicker);

        mPrioritySpinner = findViewById(R.id.spinner_priority);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_key_lesson_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            return true;
        }

        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
