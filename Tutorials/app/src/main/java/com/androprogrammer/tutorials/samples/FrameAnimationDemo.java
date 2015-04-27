package com.androprogrammer.tutorials.samples;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;

public class FrameAnimationDemo extends Baseactivity
{

    protected ImageView iv1;
    protected AnimationDrawable Anim;
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setReference();
        setToolbarSubTittle(this.getClass().getSimpleName());

        setToolbarElevation(7);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(
                    R.mipmap.untitled1);
            BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(
                    R.mipmap.untitled2);
            BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(
                    R.mipmap.untitled3);

            Anim = new AnimationDrawable();
            Anim.addFrame(frame1, 200);
            Anim.addFrame(frame2, 200);
            Anim.addFrame(frame3, 200);
            Anim.setOneShot(false);

            iv1.setBackgroundDrawable(Anim);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                public void run() {

                    Anim.start();

                }
            }, 5000);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void setReference()
    {
        view = LayoutInflater.from(this).inflate(R.layout.activity_frameanimation_demo,container);
        iv1 = (ImageView) view.findViewById(R.id.imageView1);
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
            case R.id.action_settings:
                // return true;
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
