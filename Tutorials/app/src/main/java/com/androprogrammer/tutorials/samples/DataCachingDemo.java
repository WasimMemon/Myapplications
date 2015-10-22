package com.androprogrammer.tutorials.samples;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.DataCachingListAdapter;
import com.androprogrammer.tutorials.listners.ApiResponse;
import com.androprogrammer.tutorials.models.UserDataResponse;
import com.androprogrammer.tutorials.network.HTTPWebRequest;
import com.androprogrammer.tutorials.util.Common;
import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataCachingDemo extends Baseactivity implements ApiResponse{

	protected View view;
	private RecyclerView recycler_data;
	private SwipeRefreshLayout layout_refresh;
	private RecyclerView.LayoutManager mLayoutManager;
	private List<UserDataResponse> mdata;
	private DataCachingListAdapter mAdapter;

	private static final String Cache_Key="userData";

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

		setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

		setToolbarSubTittle(this.getClass().getSimpleName());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void setReference() {
		view = LayoutInflater.from(this).inflate(R.layout.activity_datacaching_demo, container);

		layout_refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);

		mLayoutManager = new LinearLayoutManager(DataCachingDemo.this);

		recycler_data = (RecyclerView) view.findViewById(R.id.recycler_userData);
		recycler_data.setLayoutManager(mLayoutManager);

		mdata = new ArrayList<>();

		layout_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (!Common.isConnectivityAvailable(DataCachingDemo.this)) {
					if (layout_refresh.isRefreshing()) {
						layout_refresh.setRefreshing(false);
					}
					Common.showToast(DataCachingDemo.this, getResources().getString(R.string.msg_noInternet));

				} else
					getDataFromTheServer();
			}
		});

		fillAdapterData();
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
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void getDataFromTheServer()
	{
		HTTPWebRequest.createJsonRequest(DataCachingDemo.this,
		                                 Common.App_Network_values.Api_call_getData,
		                                 Common.App_Network_values.URL_APP_BASE,
		                                 Common.App_Network_values.METHOD_GET,
		                                 DataCachingDemo.this);
	}


	@Override
	public void NetworkRequestCompleted(int apiCode, JsonArray response) {

		try {
			switch (apiCode)
			{
				case Common.App_Network_values.Api_call_getData:

					if (response != null)
					{
						Type listType = new TypeToken<ArrayList<UserDataResponse>>(){}.getType();
						mdata = new GsonBuilder().create().fromJson(response, listType);

						//Put a simple object
						Reservoir.putAsync(Cache_Key, mdata, new ReservoirPutCallback() {
							@Override
							public void onSuccess() {
								//success
								Common.showToast(DataCachingDemo.this,"Data caching done...");

								fillAdapterData();
							}

							@Override
							public void onFailure(Exception e) {
								//error
							}
						});

						if (layout_refresh.isRefreshing())
						{
							layout_refresh.setRefreshing(false);
						}
					}

					break;
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void fillAdapterData()
	{
		try {
			boolean objectExists = Reservoir.contains(Cache_Key);

			if (objectExists)
			{
				Type resultType = new TypeToken<List<UserDataResponse>>() {}.getType();

				mdata = Reservoir.get(Cache_Key, resultType);

				mAdapter = new DataCachingListAdapter(DataCachingDemo.this,mdata);

				recycler_data.setAdapter(mAdapter);
			}
			else
			{
				getDataFromTheServer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void networkError(int apiCode, String message) {
		Common.showToast(DataCachingDemo.this,message);
	}

	@Override
	public void responseError(int apiCode, String message) {
		Common.showToast(DataCachingDemo.this,message);
	}
}
