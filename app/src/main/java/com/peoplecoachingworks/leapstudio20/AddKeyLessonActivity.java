package com.peoplecoachingworks.leapstudio20;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AddKeyLessonActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker, btnTimePicker;
    int year, month, day, hour, minute;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_key_lesson);

        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnTimePicker = findViewById(R.id.btnTimePicker);


        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);


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
}
