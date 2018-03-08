package com.peoplecoachingworks.leapstudio20;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDashboard extends Fragment {

    private CardView assessmentCard, ideaCard, goalCard, addCard, wifiCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //Defining cards
        assessmentCard = view.findViewById(R.id.assessmentCardId);
        ideaCard = view.findViewById(R.id.ideaCardId);
        goalCard = view.findViewById(R.id.cardGoalId);
        addCard = view.findViewById(R.id.addCardId);
        wifiCard = view.findViewById(R.id.wifiCardId);

        //Add onClickListener to the cards
        goalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), GoalAchievementPage.class);
                startActivity(i);
            }
        });


        return view;
    }

}
