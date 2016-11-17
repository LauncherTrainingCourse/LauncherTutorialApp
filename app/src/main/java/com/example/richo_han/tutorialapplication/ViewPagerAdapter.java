package com.example.richo_han.tutorialapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Richo_Han on 2016/11/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = {"Contact", "Message", "Group"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
