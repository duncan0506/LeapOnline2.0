package com.peoplecoachingworks.leapstudio20;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class DialogAddGoal extends AppCompatDialogFragment {
    private EditText etAddGoal, etAddAuthor;
    private DialogAddGoalListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_goal, null);

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
                        String quote = etAddGoal.getText().toString();
                        String author = etAddAuthor.getText().toString();
                        listener.applyText(quote, author);
                    }
                });

        etAddGoal = view.findViewById(R.id.etAddGoal);
        etAddAuthor = view.findViewById(R.id.etAddAuthor);
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

    public interface DialogAddGoalListener {
        void applyText(String quote, String author);
    }
}
