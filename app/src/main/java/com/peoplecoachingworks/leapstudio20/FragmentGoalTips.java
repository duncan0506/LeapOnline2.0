package com.peoplecoachingworks.leapstudio20;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentGoalTips.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentGoalTips#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGoalTips extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Goal Achievement Tips"); //Set title for Fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goal_tips, container, false);
    }

}
