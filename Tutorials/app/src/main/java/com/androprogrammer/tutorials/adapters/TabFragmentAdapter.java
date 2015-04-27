package com.androprogrammer.tutorials.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Wasim on 18-04-2015.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private String[] titles;

    public TabFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String [] title) {
        super(fm);
        this.fragments = fragments;
        this.titles = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
