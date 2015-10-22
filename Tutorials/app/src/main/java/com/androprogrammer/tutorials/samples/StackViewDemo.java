package com.androprogrammer.tutorials.samples;

import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.StackView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.StackViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class StackViewDemo extends Baseactivity {

    protected View view;
    private StackView cartoon_stackview;

    private List<Integer> imageList;
    private StackViewAdapter mAdapter;

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

        setToolbarElevation(0);

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_stackview_demo,container);

        cartoon_stackview = (StackView) view.findViewById(R.id.stackView_images);

        imageList = new ArrayList<>();

        imageList.add(R.mipmap.abc1);
        imageList.add(R.mipmap.abc2);
        imageList.add(R.mipmap.abc3);
        imageList.add(R.mipmap.abc4);
        imageList.add(R.mipmap.abc5);

        mAdapter = new StackViewAdapter(StackViewDemo.this, imageList);

        cartoon_stackview.setAdapter(mAdapter);
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
                // NavUtils.navigateUpFromSameTask(this);
                // return true;
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
