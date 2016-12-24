package com.androprogrammer.tutorials.samples;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.fragments.FragmentOne;
import com.androprogrammer.tutorials.fragments.FragmentThree;
import com.androprogrammer.tutorials.fragments.FragmentTwo;
import com.androprogrammer.tutorials.util.Common;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BottomBarDemo extends Baseactivity {

	@Bind(R.id.bottom_navigation)
	BottomNavigationView bottomNavigation;

	private View viewLayout;

	private static final String TAG = "BottomBarDemo";

	private int[] bottomBarColors;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

			//set the transition
			Transition ts = new Explode();
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

		int themeColor = Common.getThemeColor(this, R.attr.colorPrimary);

		bottomBarColors = new int[]{
				themeColor,
				R.color.color_skype1,
				R.color.color_blogger,
				R.color.color_gPlus,
				R.color.color_whatsApp
		};

		// For the first time setup
		changeBottomBarColor(bottomNavigation, 0);
		changeFragment(0);

		bottomNavigation.setOnNavigationItemSelectedListener(
				new BottomNavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(@NonNull MenuItem item) {
						switch (item.getItemId()) {
							case R.id.action_favorites:
								changeBottomBarColor(bottomNavigation, 0);
								changeFragment(0);
								break;
							case R.id.action_schedules:
								changeBottomBarColor(bottomNavigation, 1);
								changeFragment(1);
								break;
							case R.id.action_music:
								changeBottomBarColor(bottomNavigation, 2);
								changeFragment(2);
								break;
							case R.id.action_music1:
								changeBottomBarColor(bottomNavigation, 3);
								changeFragment(3);
								break;
							case R.id.action_favorites1:
								changeBottomBarColor(bottomNavigation, 4);
								changeFragment(4);
								break;
						}
						return true;
					}
				});
	}

	@Override
	public void setReference() {
		viewLayout = LayoutInflater.from(this).inflate(R.layout.activity_bottombar_demo, container);

		ButterKnife.bind(this, viewLayout);
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
				break;
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * To change bottom bar color on the basis of index
	 * @param bottomNavigationView bottom bar object
	 * @param index menu index
	 */
	private void changeBottomBarColor(BottomNavigationView bottomNavigationView, int index) {
		if (bottomBarColors != null) {
			int colorCode = 0;

			if (index == 0) {
				colorCode = bottomBarColors[index];
			} else {
				colorCode = ContextCompat.getColor(BottomBarDemo.this, bottomBarColors[index]);
			}

			DrawableCompat.setTint(ContextCompat.getDrawable(BottomBarDemo.this,
					R.drawable.drawable_bottombar_color),
					colorCode);

			bottomNavigationView.setItemBackgroundResource(R.drawable.drawable_bottombar_color);

			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				// If you want to change status bar color
				//getWindow().setStatusBarColor(ContextCompat.getColor(BottomBarDemo.this, colorCode));

				// If you want to change bottom device navigation key background color
				getWindow().setNavigationBarColor(colorCode);
			}
		}
	}

	/**
	 * To load fragments for sample
	 * @param position menu index
	 */
	private void changeFragment(int position) {

		Fragment newFragment = null;

		if (position == 0) {
			newFragment = new FragmentOne();
		} else if (position % 2 != 0) {
			newFragment = new FragmentTwo();
		} else {
			newFragment = new FragmentThree();
		}

		getFragmentManager().beginTransaction().replace(
				R.id.fragmentContainer, newFragment)
				.commit();
	}
}
