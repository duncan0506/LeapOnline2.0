package com.peoplecoachingworks.leapstudio20;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.peoplecoachingworks.leapstudio20.Data.GoalTipsContract.GoalTipsEntry;
import com.peoplecoachingworks.leapstudio20.Data.GoalTipsDBHelper;


public class DialogAddGoal extends AppCompatDialogFragment {
    String quoteString, authorString;
    private EditText etAddGoal, etAddAuthor;
    private DialogAddGoalListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_goal, null);

        etAddGoal = view.findViewById(R.id.etAddGoal);
        etAddAuthor = view.findViewById(R.id.etAddAuthor);

        builder.setView(view)
                .setTitle("Add Goal Achievement Tips")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //quoteString = etAddGoal.getText().toString().trim();
                        //authorString = etAddAuthor.getText().toString().trim();
                        //listener.applyText(quoteString, authorString);
                        insertGoal();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogAddGoalListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogAddGoalListener");
        }
    }

    private void insertGoal() {
        //Read from input
        quoteString = etAddGoal.getText().toString().trim();
        authorString = etAddAuthor.getText().toString().trim();

        //Create instance of GoalTipsDbHelper class
        GoalTipsDBHelper mDbHelper = new GoalTipsDBHelper(getActivity().getApplicationContext());
        //Gets data repository into write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Get text from TextView

        //Create map of values, column name are keys
        ContentValues values = new ContentValues();
        //Enter text into db
        values.put(GoalTipsEntry.COLUMN_QUOTE, quoteString);
        values.put(GoalTipsEntry.COLUMN_AUTHOR, authorString);

        long newRowId = db.insert(GoalTipsEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(getActivity().getApplicationContext(), "Error saving", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
        }

        Log.v("Fragment Goal Tips", "new row ID" + newRowId);
    }

    public interface DialogAddGoalListener {
        void applyText(String quote, String author);
    }
}
