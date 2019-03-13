package com.androprogrammer.tutorials.samples;

import android.app.ActionBar;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.app.Fragment;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.TabFragmentAdapter;
import com.androprogrammer.tutorials.customviews.SlidingTabLayout;
import com.androprogrammer.tutorials.fragments.FragmentOne;
import com.androprogrammer.tutorials.fragments.FragmentThree;
import com.androprogrammer.tutorials.fragments.FragmentTwo;
import com.androprogrammer.tutorials.fragments.RecyclerViewFragment;
import com.androprogrammer.tutorials.util.Common;

import java.util.ArrayList;

public class TabViewDemo extends Baseactivity implements ActionBar.TabListener {

    protected View view;
    protected SlidingTabLayout tabs_header;
    protected android.support.v4.view.ViewPager mPager;
    protected ArrayList<Fragment> fragments;
    protected TabFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //opening transition animations
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        setReference();

        setSimpleToolbar(true);

        setToolbarSubTittle(this.getClass().getSimpleName());

        setToolbarElevation(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragments.add(new RecyclerViewFragment());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());

        String[] titles = {"One", "Two", "Three"};

        mAdapter = new TabFragmentAdapter(getFragmentManager(), fragments, titles);

        mPager.setAdapter(mAdapter);

        int color = Common.getThemeColor(this, R.attr.colorPrimary);

        tabs_header.setBackgroundColor(color);
        tabs_header.setTitleColor(Color.WHITE);
        tabs_header.setFittingChildren(true);
        tabs_header.setTabType(SlidingTabLayout.TabType.TEXT);
        tabs_header.setViewPager(mPager);
    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_tabview_demo,container);

        tabs_header = (SlidingTabLayout) view.findViewById(R.id.tab_layout_header);
        mPager = (android.support.v4.view.ViewPager) view.findViewById(R.id.tab_layout_container);
        fragments = new ArrayList<Fragment>();

        SetTabSelector();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //closing transition animations
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void SetTabSelector()
    {
        tabs_header.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position)
            {
                int ColorId = 0;

                switch (position)
                {
                    case 0:
                        ColorId = Color.WHITE;
                        break;

                    case 1:
                        ColorId = Color.MAGENTA;
                        break;
                    case 2:
                        ColorId = Color.RED;
                        break;
                }

                return ColorId;

            }

            @Override
            public int getDividerColor(int position) {
                return 0;
            }
        });

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
