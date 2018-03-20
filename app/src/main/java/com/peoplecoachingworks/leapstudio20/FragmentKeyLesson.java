package com.peoplecoachingworks.leapstudio20;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentKeyLesson extends Fragment {

    Fragment fragment;
    FloatingActionButton fabAddKeyLesson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Key Lesson"); //Set title for Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_key_lesson, container, false);

        fabAddKeyLesson = view.findViewById(R.id.fabAddKeyLesson);

        fabAddKeyLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddKeyLessonActivity.class);
                startActivity(i);
                //addFragment();


            }
        });

        return view;
    }

    private void addFragment() {

        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        FragmentAddKeyLesson fragmentAddKeyLesson = new FragmentAddKeyLesson();
        ft.add(R.id.content_frame, fragmentAddKeyLesson);
        ft.commit(); */

        FragmentAddKeyLesson fragmentAddKeyLesson = new FragmentAddKeyLesson();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragmentAddKeyLesson, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


}
