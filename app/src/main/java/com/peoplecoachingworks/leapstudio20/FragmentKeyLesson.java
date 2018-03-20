package com.peoplecoachingworks.leapstudio20;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentKeyLesson extends Fragment {

    Fragment fragment;
    FloatingActionButton fabAddKeyLesson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_key_lesson, container, false);

        fabAddKeyLesson = view.findViewById(R.id.fabAddKeyLesson);

        fabAddKeyLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new FragmentAddKeyLesson();

                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();

                }

            }
        });

        return view;
    }


}
