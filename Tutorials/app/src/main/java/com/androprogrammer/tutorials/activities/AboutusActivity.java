package com.androprogrammer.tutorials.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androprogrammer.tutorials.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutusActivity extends Baseactivity {

    @Bind(R.id.layout_header)
    LinearLayout layoutHeader;
    @Bind(R.id.icon_web)
    ImageView iconWeb;
    @Bind(R.id.icon_source)
    ImageView iconSource;

    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);
        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(getResources().getString(R.string.title_activity_aboutus));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_aboutus, container);

        ButterKnife.bind(this,view);

        iconSource.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.MULTIPLY);
        iconWeb.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.MULTIPLY);

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
                overridePendingTransition(0, R.anim.activity_close_scale);
                //        ActivityCompat.finishAfterTransition(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.icon_web, R.id.icon_source})
    public void onClick(View view) {

        Intent openSite = null;

        switch (view.getId()) {
            case R.id.icon_web:

                openSite = new Intent("android.intent.action.VIEW",
                        Uri.parse("http://www.androprogrammer.com/"));
                startActivity(openSite);
                break;
            case R.id.icon_source:

                openSite = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://github.com/WasimMemon/Myapplications"));
                startActivity(openSite);
                break;
        }
    }
}
