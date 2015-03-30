package com.androprogrammer.tutorials.samples;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.services.GetUserLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrackUserDemo extends Baseactivity
{

    protected View view;

    // Google Map Object
    private GoogleMap googleMap;

    private LatLng userLatLng;

    private boolean IsFirstTimeLoad;

    private LocationManager mLocationManager;

    private static final String TAG = "TrackUserDemo";

    private static String RefreshMenu = "Refresh Map";


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
        } else {
            //listViewCarrier.setIndicatorBoundsRelative(500, 0);
        }

        super.onCreate(savedInstanceState);

        setReference();

        setToolbarTittle(this.getClass().getSimpleName());

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        initilizeMap();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!IsFirstTimeLoad)
        {
            if(!mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
            {
                Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
            else {
                Intent intent = new Intent(this, GetUserLocation.class);
                startService(intent);
                initilizeMap();
            }
        }
        else {
            IsFirstTimeLoad = false;
        }
    }

    private void initilizeMap()
    {
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                // check if map is created successfully or not
                if (googleMap == null)
                {
                    Toast.makeText(TrackUserDemo.this, "Sorry! unable to create maps", Toast.LENGTH_LONG).show();
                }
            }

            // getting GPS status
            boolean isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            GetUserLocation service = new GetUserLocation(this,isGPSEnabled,isNetworkEnabled);

            if (!isGPSEnabled)
            {
                Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                //service.getUserLocation(isGPSEnabled,isNetworkEnabled);
            }

            userLatLng = new LatLng(service.getLatitude(), service.getLongitude());

            MarkerOptions usermarker = new MarkerOptions().position(userLatLng);

            // To remove old marker
            googleMap.clear();

            googleMap.addMarker(usermarker);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(service.getLatitude(), service.getLongitude()),
                    10.0f));

            googleMap.getUiSettings().setZoomControlsEnabled(true);

        } catch (Exception e) {
            //Log.e(TAG , e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setReference()
    {
        view = LayoutInflater.from(this).inflate(R.layout.activity_trackuser_demo,container);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // To display refresh button

        SubMenu btnRefresh = menu.addSubMenu(RefreshMenu);
        btnRefresh.setIcon(android.R.drawable.ic_popup_sync);
        btnRefresh.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        super.onCreateOptionsMenu(menu);
        return true;
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


        if (item.getTitle() == RefreshMenu)
        {
            initilizeMap();
        }

        return super.onOptionsItemSelected(item);
    }
}
