package com.androprogrammer.tutorials.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.androprogrammer.tutorials.MainController;
import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.util.ExceptionHandler;


public abstract class Baseactivity extends ActionBarActivity {

    public FrameLayout container;
    public android.support.v7.widget.Toolbar toolbar;
    public RelativeLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!MainController.preferenceGetString("AppliedTheme","").equals(""))
        {
            if (MainController.preferenceGetString("AppliedTheme","").equals("Green"))
            {
                setTheme(R.style.ThemeApp_Green);
            }
            else if (MainController.preferenceGetString("AppliedTheme","").equals("Green_Dark"))
            {
                setTheme(R.style.ThemeApp_Green_Dark);
            }
            else if (MainController.preferenceGetString("AppliedTheme","").equals("Purple_Dark"))
            {
                setTheme(R.style.ThemeApp_Purple_Dark);
            }
            else if (MainController.preferenceGetString("AppliedTheme","").equals("Purple"))
            {
                setTheme(R.style.ThemeApp_Purple);
            }
        }
        else
        {
            setTheme(R.style.ThemeApp_Green);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getInstance(this));

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        mainlayout = (RelativeLayout) findViewById(R.id.fulllayout);

        //Set the custom toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(R.string.app_name);
            toolbar.setTitleTextColor(Color.WHITE);
            //toolbar.setLogo(R.mipmap.ic_launcher);
        }
    }

    public void setToolbarSubTittle(String header) {
        toolbar.setSubtitle(header);
    }

    public void setToolbarElevation(float value) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(value);
        }
    }

    // Method to set xml object reference.
    public abstract void setReference();

}
