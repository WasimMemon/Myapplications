package com.androprogrammer.tutorials.samples;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.TutorialListAdapter;
import com.androprogrammer.tutorials.util.GridLayoutDivider;
import com.androprogrammer.tutorials.util.LinearLayoutDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test_Mainlist extends Baseactivity implements SearchView.OnQueryTextListener{

    protected View view;
    protected FloatingActionButton btShare;
    protected RecyclerView tutorial_list;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected TutorialListAdapter mAdapter;
    protected MenuItem list_type;
    protected boolean linearDividerAdded, GridDividerAdded;

    public Map<String,Object> tutorials;
    protected List<String> list_keys;

    private static final int SPAN_COUNT = 2;
    private static final String TAG = "Test_Mainlist";

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setReference();

        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addListItem();

        list_keys = new ArrayList<String>(tutorials.keySet());

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new TutorialListAdapter(Test_Mainlist.this, list_keys);
        tutorial_list.setAdapter(mAdapter);


        /*tutorials.keySet().
        String[] keys = tutorials.keySet().toArray(
                new String[tutorials.keySet().size()]);*/


    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_testmainlist, container);
        tutorial_list = (RecyclerView) view.findViewById(R.id.recycler_mainlist);
        btShare = (FloatingActionButton) view.findViewById(R.id.fab_share);

        tutorials = new TreeMap<String,Object>();

        mLayoutManager = new LinearLayoutManager(Test_Mainlist.this);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_testmainlist_demo, menu);

        list_type = menu.findItem(R.id.menu_list_style);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (list_type != null)
        {
            if (mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER)
            {
                list_type.setIcon(android.R.drawable.ic_menu_sort_by_size);
            }
            else
            {
                list_type.setIcon(android.R.drawable.ic_dialog_dialer);
            }

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        switch (item.getItemId()){

            case R.id.menu_list_style:
                if (mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER)
                {
                    setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                }
                else
                {
                    setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
                }
                this.invalidateOptionsMenu();
                break;

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        tutorials.put("Crop and Resize Image", new Intent(this, TakePictureDemo.class));
        tutorials.put("Image Grid View", new Intent(this, ImageAnimationDemo.class));
        tutorials.put("Image Switcher", new Intent(this, ImageSwitcherDemo.class));
        tutorials.put("Tabs with Toolbar", new Intent(this, TabViewDemo.class));
        tutorials.put("Icon Tabs with Toolbar", new Intent(this, ImageTabViewDemo.class));
        tutorials.put("Push Notification", new Intent(this, PushNotificationDemo.class));
        tutorials.put("All Installed Apps", new Intent(this, AppsListviewDemo.class));
        tutorials.put("View pager with circular indicator", new Intent(this, ViewPagerDemo.class));
        tutorials.put("Voice input using Google", new Intent(this, VoiceInputDemo.class));
        tutorials.put("Change Theme", new Intent(this, ChangeThemeDemo.class));
        tutorials.put("Fragment Animation", new Intent(this, FragmentAnimationDemo.class));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        final List<String> filteredModelList = filter(list_keys, query);
        mAdapter.animateTo(filteredModelList);
        tutorial_list.scrollToPosition(0);
        return true;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (tutorial_list.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) tutorial_list.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(Test_Mainlist.this, SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                if (!GridDividerAdded) {
                    tutorial_list.addItemDecoration(new GridLayoutDivider(Test_Mainlist.this,R.dimen.griditem_padding));
                    GridDividerAdded = true;
                }
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(Test_Mainlist.this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

                if (!linearDividerAdded) {
                    tutorial_list.addItemDecoration(new LinearLayoutDivider(Test_Mainlist.this, null));
                    linearDividerAdded = true;
                }
                tutorial_list.invalidateItemDecorations();
                break;
            default:
                mLayoutManager = new LinearLayoutManager(Test_Mainlist.this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                if (!linearDividerAdded) {
                    tutorial_list.addItemDecoration(new LinearLayoutDivider(Test_Mainlist.this, null));
                    linearDividerAdded = true;
                }
        }

        tutorial_list.setLayoutManager(mLayoutManager);
        tutorial_list.scrollToPosition(scrollPosition);
    }

    private List<String> filter(List<String> models, String query) {
        query = query.toLowerCase();

        final List<String> filteredModelList = new ArrayList<>();

        for (String row : models)
        {
            if (row.contains(query)) {
                filteredModelList.add(row);
            }
        }

        return filteredModelList;

    }
}
