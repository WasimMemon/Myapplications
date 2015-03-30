package com.androprogrammer.tutorials.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androprogrammer.tutorials.R;


public abstract class Baseactivity extends ActionBarActivity {

    public LinearLayout container;
    public android.support.v7.widget.Toolbar toolbar;
    public RelativeLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        container = (LinearLayout) findViewById(R.id.container);
        mainlayout = (RelativeLayout) findViewById(R.id.fulllayout);

        //Set the custom toolbar
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleTextColor(Color.WHITE);
            //toolbar.setLogo(R.mipmap.ic_launcher);
        }
    }

    public void setToolbarTittle(String header)
    {
        toolbar.setSubtitle(header);
    }

    // Method to set xml object reference.
    public abstract void setReference();
}
