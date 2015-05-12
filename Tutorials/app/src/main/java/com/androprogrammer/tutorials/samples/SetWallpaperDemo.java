package com.androprogrammer.tutorials.samples;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;

import java.io.IOException;

public class SetWallpaperDemo extends Baseactivity implements View.OnClickListener {

    protected View view;

    protected static final int SELECT_PICTURE = 1;
    protected String SelectedimagePath;
    protected Button bt1,bt2;
    protected ImageView iv1;
    protected Bitmap bmp;

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

        setToolbarElevation(7);

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_setwallpaper_demo,container);

        bt1 = (Button) view.findViewById(R.id.bt_imagepicker);
        bt2 = (Button) view.findViewById(R.id.bt_setwallpaper);
        iv1 = (ImageView) view.findViewById(R.id.imageView1);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    private String getPath(Uri getImageuri)
    {
        if(getImageuri == null)
        {
            return null;
        }

        String [] Projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(getImageuri, Projection, null, null, null);

        if(cursor != null)
        {
            int c_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            cursor.getString(c_index);
            cursor.close();
        }

        return getImageuri.getPath();
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

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bt_imagepicker:

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"),SELECT_PICTURE);

                break;

            case R.id.bt_setwallpaper:

                try {
                    getApplicationContext().setWallpaper(bmp);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri getImageuri = data.getData();
                SelectedimagePath = getPath(getImageuri);

                if (bmp != null && bmp.isRecycled()) {
                    bmp = null;
                }

                bmp = BitmapFactory.decodeFile(SelectedimagePath);
                iv1.setImageBitmap(bmp);
            }
        }
    }
}
