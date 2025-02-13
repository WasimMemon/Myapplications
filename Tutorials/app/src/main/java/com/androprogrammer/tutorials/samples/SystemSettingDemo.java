package com.androprogrammer.tutorials.samples;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.transition.Slide;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;

public class SystemSettingDemo extends Baseactivity implements View.OnClickListener {

    private View view;

    protected Button Bbright,Bvolume;
    protected SeekBar brightness,volume;
    //protected TextView tv_bright,tv_volume;
    protected int set_brightness,set_volume;
    protected float value_brightness,value_volume;

    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Slide();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bbright.setOnClickListener(this);
        Bvolume.setOnClickListener(this);

        try {
            set_brightness = android.provider.Settings.System.getInt(getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);

            //tv_bright.setText(set_brightness + "");
            brightness.setProgress(set_brightness);
        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                set_brightness = progress;
                //tv_bright.setText(set_brightness + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });

        volume.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_RING));
        volume.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_RING));

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                set_volume = progress;
                //tv_volume.setText(set_volume + "");
            }
        });


    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_systemsetting_demo, container);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        brightness = (SeekBar) view.findViewById(R.id.Sbbrihgtness);
        volume= (SeekBar) view.findViewById(R.id.Sbvolume);
        /*tv_bright = (TextView) view.findViewById(R.id.tv_brightness);
        tv_volume = (TextView) view.findViewById(R.id.tv_volume);*/
        Bbright = (Button) view.findViewById(R.id.button1);
        Bvolume = (Button) view.findViewById(R.id.button2);


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button1:
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS, set_brightness);
                break;
            case R.id.button2:

                audioManager.setStreamVolume(AudioManager.STREAM_RING, set_volume, 0);

                break;
            default:
                break;
        }
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
