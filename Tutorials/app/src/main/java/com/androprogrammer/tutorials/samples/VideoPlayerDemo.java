package com.androprogrammer.tutorials.samples;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;


public class VideoPlayerDemo extends Baseactivity {

    protected VideoView myView;
    protected ImageButton play,pause;
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Slide();
            ts.setDuration(3000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);
        setReference();

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(myView);

        Uri uri = null;

        //Setting MediaController and URI, then starting the videoView
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.savehemesursorsandwallpapersinnlite);

        myView.setMediaController(mediaController);
        myView.setVideoURI(uri);
        myView.requestFocus();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.start();
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.pause();
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void setReference()
    {
        view = LayoutInflater.from(this).inflate(R.layout.activity_videoplayer_demo,container);

        myView = (VideoView) view.findViewById(R.id.myVideoView);
        play = (ImageButton) view.findViewById(R.id.video_play);
        pause = (ImageButton) view.findViewById(R.id.video_pause);

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
