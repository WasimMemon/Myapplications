package com.androprogrammer.tutorials.samples;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.ViewPagerAdapter;

public class ViewPagerDemo extends Baseactivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    protected View view;
    private ImageButton btnNext, btnFinish;
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;

    private int[] mImageResources = {
            R.mipmap.abc1,
            R.mipmap.abc2,
            R.mipmap.abc3,
            R.mipmap.abc4,
            R.mipmap.abc5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setReference();

        toolbar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_viewpager_demo,container);

        intro_images = (ViewPager) view.findViewById(R.id.pager_introduction);
        btnNext = (ImageButton) view.findViewById(R.id.btn_next);
        //btnSkip = (Button) view.findViewById(R.id.btn_skip);
        btnFinish = (ImageButton) view.findViewById(R.id.btn_finish);

        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        btnNext.setOnClickListener(this);
        //btnSkip.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        mAdapter = new ViewPagerAdapter(ViewPagerDemo.this, mImageResources);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
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
        switch (v.getId()) {
            case R.id.btn_next:
                intro_images.setCurrentItem((intro_images.getCurrentItem() < dotsCount)
                        ? intro_images.getCurrentItem() + 1 : 0);
                break;

            case R.id.btn_finish:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
