package com.androprogrammer.tutorials.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.customviews.CustomListView;
import com.androprogrammer.tutorials.samples.AsyncFileReadDemo;
import com.androprogrammer.tutorials.samples.BatteryLevelDemo;
import com.androprogrammer.tutorials.samples.CircularImageViewDemo;
import com.androprogrammer.tutorials.samples.FrameAnimationDemo;
import com.androprogrammer.tutorials.samples.ImageAnimationDemo;
import com.androprogrammer.tutorials.samples.ImageSwitcherDemo;
import com.androprogrammer.tutorials.samples.ImageTabViewDemo;
import com.androprogrammer.tutorials.samples.SystemSettingDemo;
import com.androprogrammer.tutorials.samples.TabViewDemo;
import com.androprogrammer.tutorials.samples.TakePictureDemo;
import com.androprogrammer.tutorials.samples.TrackUserDemo;
import com.androprogrammer.tutorials.samples.VideoPlayerDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;


public class Listactivity extends Baseactivity implements ListView.OnItemClickListener, SearchView.OnQueryTextListener, CustomListView.ScrollObserver
{
    protected View view;
    protected com.androprogrammer.tutorials.customviews.CustomListView lv_tutorials;
    protected Map<String,Object> tutorials;
    protected File image;
    protected ImageButton btShare;
    protected ArrayAdapter<String> adapter = null;

    private static String TAG = "Listactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setReference();

        setToolbarElevation(7);

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

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, keys);

        lv_tutorials.setAdapter(adapter);

        lv_tutorials.setOnItemClickListener(this);
        lv_tutorials.setTextFilterEnabled(false);


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
        view = LayoutInflater.from(this).inflate(R.layout.activity_mainlist, container);
        lv_tutorials = (com.androprogrammer.tutorials.customviews.CustomListView) view.findViewById(R.id.lv_tutorials);
        lv_tutorials.setListener(this);

        lv_tutorials.addHeaderView(getLayoutInflater().inflate(R.layout.mainlist_header, null));

        btShare = (ImageButton) view.findViewById(R.id.list_bt_share);
        tutorials = new TreeMap<String,Object>();
    }

    private void hideViews()
    {
        Log.d(TAG,"hideview");
        toolbar.animate().alpha(0).setDuration(300)
                .translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) btShare.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;

        btShare.animate().setDuration(300).translationY(btShare.getHeight()+fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        Log.d(TAG,"showview");
        toolbar.animate().alpha(1).setDuration(300).translationY(0).setInterpolator(new DecelerateInterpolator(2));
        btShare.animate().setDuration(300).translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
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
        tutorials.put("Image Grid View", new Intent(this, ImageAnimationDemo.class));
        tutorials.put("Image Switcher", new Intent(this, ImageSwitcherDemo.class));
        tutorials.put("Tabs with Toolbar", new Intent(this, TabViewDemo.class));
        tutorials.put("Icon Tabs with Toolbar", new Intent(this, ImageTabViewDemo.class));
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

        MenuItem item = menu.findItem(R.id.search);
        SearchView sv = (SearchView) MenuItemCompat.getActionView(item);
        sv.setOnQueryTextListener(this);

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


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String key = (String) parent.getItemAtPosition(position);
        startActivity(new Intent((Intent) tutorials.get(key)));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if (TextUtils.isEmpty(s))
        {
            adapter.getFilter().filter("");
        }
        else
        {
            adapter.getFilter().filter(s);
        }

        return true;
    }

    @Override
    public void onHide() {
        hideViews();
    }

    @Override
    public void onShow() {
        showViews();
    }
}
