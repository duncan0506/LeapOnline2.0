package com.peoplecoachingworks.leapstudio20;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class FragmentHomeMaster extends Fragment {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                getActivity().setTitle("Dashboard");
                fragment = new FragmentDashboard();
            } else if (id == R.id.nav_key_lesson) {
                getActivity().setTitle("Key Lesson");
                fragment = new FragmentKeyLesson();
            } else if (id == R.id.nav_profile) {
                getActivity().setTitle("Profile");
                fragment = new FragmentProfile();
            }

            if (fragment != null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.home_master_frame, fragment);
                ft.commit();

            }
            return true;
        }

    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Home Master"); //Set title for Fragment
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_master, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getActivity().setTitle("Dashboard");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.home_master_frame, new FragmentDashboard());
        ft.commit();

        return view;
    }

}
