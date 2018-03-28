package com.peoplecoachingworks.leapstudio20;


import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.peoplecoachingworks.leapstudio20.Data.GoalTipsContract.GoalTipsEntry;


public class DialogAddGoal extends AppCompatDialogFragment {
    String quoteString, authorString;
    private EditText etAddQuote, etAddAuthor;
    private DialogAddGoalListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_goal, null);

        etAddQuote = view.findViewById(R.id.etAddQuote);
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
    //TODO: Entered data doesn't update unless Fragment is reloaded

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
        // Read from input fields
        quoteString = etAddQuote.getText().toString().trim();
        authorString = etAddAuthor.getText().toString().trim();

        // Create a ContentValues object where column names are the keys, and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(GoalTipsEntry.COLUMN_QUOTE, quoteString);
        values.put(GoalTipsEntry.COLUMN_AUTHOR, authorString);

        /*Dialog dialogAddGoal = getDialog();
        Uri currentGoalUri = dialogAddGoal.getData();*/



        // Insert a new pet into the provider, returning the content URI for the new pet.
        Uri newUri = getActivity().getContentResolver().insert(GoalTipsEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.dialog_insert_quote_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.dialog_insert_quote_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public interface DialogAddGoalListener {
        void applyText(String quote, String author);
    }
}
