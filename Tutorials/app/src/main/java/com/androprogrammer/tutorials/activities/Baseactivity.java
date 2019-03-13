package com.androprogrammer.tutorials.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.util.UserPreferenceManager;
import com.anupcowkur.reservoir.Reservoir;


public abstract class Baseactivity extends AppCompatActivity {

	private View view_simpleToolbar;
	public FrameLayout container;
	public android.support.v7.widget.Toolbar toolbar;
	public CoordinatorLayout mainlayout;
	public AppBarLayout base_toolbarContainer;

	private static final String Theme_Current = "AppliedTheme";
	//public ExceptionHandler exceptionHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			setAppTheme();
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_base);

			//exceptionHandler = new ExceptionHandler(this);

			//Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getInstance(this));
			base_toolbarContainer = (AppBarLayout) findViewById(R.id.base_appbar);
			container = (FrameLayout) findViewById(R.id.container);
			mainlayout = (CoordinatorLayout) findViewById(R.id.fulllayout);

			Reservoir.init(this, 8192); //in bytes
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSimpleToolbar(boolean isSimpleToolbarRequire) {
		if (isSimpleToolbarRequire) {
			view_simpleToolbar = LayoutInflater.from(this).inflate(R.layout.simple_toolbar, base_toolbarContainer);
			toolbar = (android.support.v7.widget.Toolbar) view_simpleToolbar.findViewById(R.id.toolbar);

			//Set the custom toolbar
			if (toolbar != null) {
				setSupportActionBar(toolbar);
				toolbar.setTitle(R.string.app_name);
				toolbar.setTitleTextColor(Color.WHITE);
				//toolbar.setLogo(R.mipmap.ic_launcher);
			}
		}
	}

	public void setToolbarSubTittle(String header) {
		if (toolbar != null)
			toolbar.setSubtitle(header);
	}

	public void setToolbarElevation(float value) {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if (toolbar != null)
				toolbar.setElevation(value);
		}
	}

	// Method to set xml object reference.
	public abstract void setReference();

	private void setAppTheme() {
		if (!UserPreferenceManager.preferenceGetString(Theme_Current, "").equals("")) {
			if (UserPreferenceManager.preferenceGetString(Theme_Current, "").equals("Green")) {
				setTheme(R.style.ThemeApp_Green);
			} else if (UserPreferenceManager.preferenceGetString(Theme_Current, "").equals("Green_Dark")) {
				setTheme(R.style.ThemeApp_Green_Dark);
			} else if (UserPreferenceManager.preferenceGetString(Theme_Current, "").equals("Purple_Dark")) {
				setTheme(R.style.ThemeApp_Purple_Dark);
			} else if (UserPreferenceManager.preferenceGetString(Theme_Current, "").equals("Purple")) {
				setTheme(R.style.ThemeApp_Purple);
			}
		} else {
			setTheme(R.style.ThemeApp_Green);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		try {
			Reservoir.clear();
		} catch (Exception e) {
			//failure
		}
	}
}
