package com.peoplecoachingworks.leapstudio20;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Duncan on 05-Mar-18.
 */

public class TabPager extends FragmentStatePagerAdapter {

    String[] titles = new String[]{"Home", "Dashboard", "Profile"};

    public TabPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) { //get page position
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentHome();
        } else if (position == 1) {
            return new FragmentDashboard();
        } else {
            return new FragmentProfile();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
