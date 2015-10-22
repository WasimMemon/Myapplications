package com.androprogrammer.tutorials.samples;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;

public class ImageAnimationDetail extends Baseactivity
{

    protected View view;
    protected ImageView titleImage;

    private static final String KEY_ID = "ViewTransitionValues:id";

    private int mImageResourceId = R.mipmap.abc1;

    private String mName = "ducky";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setReference();

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_imageanimation_detail,container);
        titleImage = (ImageView) view.findViewById(R.id.titleImage);
        titleImage.setImageDrawable(getDrawable());
    }

    public void clicked(View v) {
        Intent intent = new Intent(this, ImageAnimationDemo.class);
        intent.putExtra(KEY_ID, mName);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this,
                v, "hero");
        startActivity(intent, activityOptions.toBundle());
    }

    private Drawable getDrawable() {
        String name = getIntent().getStringExtra(KEY_ID);
        if (name != null) {
            mName = name;
            mImageResourceId = ImageAnimationDemo.getDrawableIdForKey(name);
            Log.d(KEY_ID, "" + mImageResourceId);
        }

        return getResources().getDrawable(mImageResourceId);
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
