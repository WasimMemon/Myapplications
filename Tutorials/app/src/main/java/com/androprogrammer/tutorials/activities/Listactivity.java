package com.androprogrammer.tutorials.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.samples.AsyncFileReadDemo;
import com.androprogrammer.tutorials.samples.BatteryLevelDemo;
import com.androprogrammer.tutorials.samples.CircularImageViewDemo;
import com.androprogrammer.tutorials.samples.FrameAnimationDemo;
import com.androprogrammer.tutorials.samples.SystemSettingDemo;
import com.androprogrammer.tutorials.samples.TakePictureDemo;
import com.androprogrammer.tutorials.samples.TrackUserDemo;
import com.androprogrammer.tutorials.samples.VideoPlayerDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;


public class Listactivity extends Baseactivity implements ListView.OnItemClickListener
{
    protected View view;
    protected ListView lv_tutorials;
    protected HashMap<String,Object> tutorials;
    protected File image;
    protected ImageButton btShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setReference();

        addListItem();

        mainlayout.post(new Runnable() {
            @Override
            public void run() {
                captureScreen();
            }
        });

        //Log.d("List", "" + tutorials.size());

        String[] keys = tutorials.keySet().toArray(
                new String[tutorials.keySet().size()]);


        lv_tutorials.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, keys));

        lv_tutorials.setOnItemClickListener(this);

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/jpg");
                sharingIntent.putExtra(Intent.EXTRA_TITLE, getApplicationInfo().name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile(image));
                startActivity(Intent.createChooser(sharingIntent, "Share App using"));

            }
        });

    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_mainlist,container);
        lv_tutorials = (ListView) view.findViewById(R.id.lv_tutorials);
        btShare = (ImageButton) view.findViewById(R.id.list_bt_share);
        tutorials = new HashMap<String,Object>();
    }

    public void addListItem()
    {
        tutorials.put("Async File Read", new Intent(this, AsyncFileReadDemo.class));
        tutorials.put("Battery Status", new Intent(this, BatteryLevelDemo.class));
        tutorials.put("Volume Setting", new Intent(this, SystemSettingDemo.class));
        tutorials.put("Frame Animation", new Intent(this, FrameAnimationDemo.class));
        tutorials.put("Video Player", new Intent(this, VideoPlayerDemo.class));
        tutorials.put("Circular Image View", new Intent(this, CircularImageViewDemo.class));
        tutorials.put("Track User Location", new Intent(this, TrackUserDemo.class));
        tutorials.put("Take Image", new Intent(this, TakePictureDemo.class));

       /* tutorials.put("Video Player1", new Intent(this, VideoPlayerDemo.class));
        tutorials.put("Circular Image View1", new Intent(this, CircularImageViewDemo.class));
        tutorials.put("Track User Location1", new Intent(this, TrackUserDemo.class));
        tutorials.put("Video Player2", new Intent(this, VideoPlayerDemo.class));
        tutorials.put("Circular Image View2", new Intent(this, CircularImageViewDemo.class));
        tutorials.put("Track User Location2", new Intent(this, TrackUserDemo.class));
        tutorials.put("Video Player3", new Intent(this, VideoPlayerDemo.class));
        tutorials.put("Circular Image View3", new Intent(this, CircularImageViewDemo.class));
        tutorials.put("Track User Location3", new Intent(this, TrackUserDemo.class));
        tutorials.put("Video Player4", new Intent(this, VideoPlayerDemo.class));
        tutorials.put("Circular Image View4", new Intent(this, CircularImageViewDemo.class));
        tutorials.put("Track User Location4", new Intent(this, TrackUserDemo.class));*/

        /*if (tutorials != null) {
            tutorials.put("Circular Image View", new Intent(this, CircularImageViewDemo.class));
        }*/
    }

    private void captureScreen(){

        // create bitmap screen capture
        mainlayout.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(mainlayout.getDrawingCache());
        mainlayout.setDrawingCacheEnabled(false);

        image = new File(Environment.getExternalStorageDirectory().toString(), "share.jpg");

        OutputStream fout = null;
        try {
            fout = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fout);
            fout.flush();
            fout.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        switch (item.getItemId()){

            case R.id.action_settings :
                break;

            /*case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/jpg");
                sharingIntent.putExtra(Intent.EXTRA_TITLE, getApplicationInfo().name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile(image));
                startActivity(Intent.createChooser(sharingIntent, "Share App using"));
                break;*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = (String) parent.getItemAtPosition(position);
        startActivity(new Intent((Intent) tutorials.get(key)));
    }
}
