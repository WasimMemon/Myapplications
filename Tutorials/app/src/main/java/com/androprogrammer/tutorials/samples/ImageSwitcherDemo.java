package com.androprogrammer.tutorials.samples;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;

public class ImageSwitcherDemo extends Baseactivity {

    protected View view;
    protected ImageSwitcher Switch;
    protected ImageView images;
    protected float initialX;
    protected Cursor cursor;
    protected int columnIndex, position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setReference();

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] projection = {MediaStore.Images.Thumbnails._ID};

        cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null,
                MediaStore.Images.Thumbnails._ID + "");

        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_imageswitcher_demo,container);
        Switch = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        images = (ImageView) view.findViewById(R.id.imageView1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                if (initialX > finalX)
                {
                    cursor.moveToPosition(position);
                    int imageID = cursor.getInt(columnIndex);
                    images.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "" + imageID));
                    //images.setBackgroundResource(R.drawable.mb__messagebar_divider);
                    Switch.showNext();

                    ShowToast("Next Image");

                    position++;
                }
                else
                {
                    if(position > 0)
                    {
                        cursor.moveToPosition(position);
                        int imageID = cursor.getInt(columnIndex);
                        images.setImageURI(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "" + imageID));
                        //images.setBackgroundResource(R.drawable.ic_launcher);

                        ShowToast("previous Image");
                        Switch.showPrevious();
                        position = position-1;
                    }
                    else
                    {
                        ShowToast("No More Images To Swipe");
                    }
                }
                break;
        }
        return false;
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

    protected void ShowToast(String message)
    {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
