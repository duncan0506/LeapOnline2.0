package com.peoplecoachingworks.leapstudio20;

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
import android.widget.Toast;


public class FragmentGoalTips extends Fragment implements DialogAddGoal.DialogAddGoalListener {

    private TextView tvQuote, tvAuthor;
    private FloatingActionButton fabAddGoal;

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

        tvQuote = view.findViewById(R.id.tvQuote);
        tvAuthor = view.findViewById(R.id.tvAuthor);
        fabAddGoal = view.findViewById(R.id.fabAddGoal);

        fabAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }

    public void openDialog() {
        DialogAddGoal dialogAddGoal = new DialogAddGoal();
        dialogAddGoal.show(getFragmentManager(), "dialog add goal");
        dialogAddGoal.setTargetFragment(FragmentGoalTips.this, 1);
    }

    @Override
    public void applyText(String quote, String author) {
        tvQuote.setText(quote);
        tvAuthor.setText(author);
    }

    @Override //Inflate the menu
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_goal_tips_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override //Handle clicks event
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                Toast.makeText(getActivity(), "Delete All Toast", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
