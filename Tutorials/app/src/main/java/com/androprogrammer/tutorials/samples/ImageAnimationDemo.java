package com.androprogrammer.tutorials.samples;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.ImageAdapter;

import java.util.List;
import java.util.Map;

public class ImageAnimationDemo extends Baseactivity implements GridView.OnItemClickListener{

    protected View view;
    protected GridView grid_images;

    public static final int[] DRAWABLES = {
            R.mipmap.abc1,
            R.mipmap.abc2,
            R.mipmap.abc3,
            R.mipmap.abc4,
            R.mipmap.abc5,
            R.mipmap.abc6
    };

    public static final String[] NAMES = {
            "first",
            "second",
            "third",
            "fourth",
            "fifth",
            "sixth"
    };

    protected ImageAdapter madapter;

    private ImageView mHero;

    private static final String KEY_ID = "ViewTransitionValues:id";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setReference();

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.second_primarydark));
            toolbar.setBackgroundColor(getResources().getColor(R.color.second_primary));
        }

        setSimpleToolbar(true);

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        madapter = new ImageAdapter(ImageAnimationDemo.this, DRAWABLES, NAMES);

        grid_images.setAdapter(madapter);
    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_imageanimation_demo,container);
        grid_images = (GridView) view.findViewById(R.id.gridImages);
    }

    private void setupHero() {
        String name = getIntent().getStringExtra(KEY_ID);
        //mHero = null;
        if (name != null) {
            //mHero = (ImageView) findViewById(getIdForKey(name));
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names,
                                                Map<String, View> sharedElements) {
                    sharedElements.put("hero", mHero);
                }
            });
        }
    }


    public void clicked(View v)
    {
        mHero = (ImageView) v.findViewById(R.id.ducky);
        Intent intent = new Intent(this, ImageAnimationDetail.class);
        intent.putExtra(KEY_ID, v.getTransitionName());
        ActivityOptions activityOptions
                = ActivityOptions.makeSceneTransitionAnimation(this, mHero, "hero");
        startActivity(intent, activityOptions.toBundle());
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

    public static int getDrawableIdForKey(String id) {
        return DRAWABLES[getIndexForKey(id)];
    }

    public static int getIndexForKey(String id) {
        for (int i = 0; i < NAMES.length; i++) {
            String name = NAMES[i];
            if (name.equals(id)) {
                return i;
            }
        }
        return 2;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clicked(view);
    }
}
