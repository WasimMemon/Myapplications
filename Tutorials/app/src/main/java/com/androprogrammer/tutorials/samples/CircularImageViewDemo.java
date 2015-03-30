package com.androprogrammer.tutorials.samples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;


public class CircularImageViewDemo extends Baseactivity {

    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_circularimageview_demo,container);

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
                //NavUtils.navigateUpFromSameTask(this);
  //              return true;
            break;
        }

        return super.onOptionsItemSelected(item);
    }
}
