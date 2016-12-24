package com.androprogrammer.tutorials.samples;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.SwitchCompat;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.androprogrammer.tutorials.MainController;
import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.activities.Listactivity;
import com.androprogrammer.tutorials.util.UserPreferenceManager;


/**
 * Created by Wasim on 15-08-2015.
 */
public class ChangeThemeDemo extends Baseactivity implements View.OnClickListener{

    protected View view;
    private LinearLayout layout_green, layout_purple;
    private SwitchCompat switch_dark;
    private boolean isDarkTheme;

    private static final String Theme_Current = "AppliedTheme";
    private static final String Theme_Dark = "DarkTheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Explode();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);
        setToolbarElevation(0);

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_changetheme_demo,container);

        switch_dark = (SwitchCompat) view.findViewById(R.id.switch_darkTheme);
        layout_green = (LinearLayout) view.findViewById(R.id.Layout_green);
        layout_purple = (LinearLayout) view.findViewById(R.id.Layout_purple);

        switch_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    isDarkTheme = true;
                    UserPreferenceManager.preferencePutBoolean(Theme_Dark, true);
                }
                else {
                    isDarkTheme = false;
	                UserPreferenceManager.preferencePutBoolean(Theme_Dark, false);
                }
            }
        });

        if (UserPreferenceManager.preferenceGetBoolean(Theme_Dark, false))
        {
            switch_dark.setChecked(true);
        }

        layout_green.setOnClickListener(this);
        layout_purple.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.Layout_green:
                if (isDarkTheme)
	                UserPreferenceManager.preferencePutString(Theme_Current, "Green_Dark");
                else
	                UserPreferenceManager.preferencePutString(Theme_Current, "Green");

                TaskStackBuilder.create(this)
                        .addNextIntent(new Intent(this, Listactivity.class))
                        .addNextIntent(getIntent())
                        .startActivities();
                break;

            case R.id.Layout_purple:

                if (isDarkTheme)
	                UserPreferenceManager.preferencePutString(Theme_Current, "Purple_Dark");
                else
	                UserPreferenceManager.preferencePutString(Theme_Current, "Purple");

                TaskStackBuilder.create(this)
                        .addNextIntent(new Intent(this, Listactivity.class))
                        .addNextIntent(getIntent())
                        .startActivities();
                break;
        }

    }
}
