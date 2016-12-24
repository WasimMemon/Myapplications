package com.androprogrammer.tutorials.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.adapters.TutorialListAdapter;
import com.androprogrammer.tutorials.listners.RecyclerItemClickListener;
import com.androprogrammer.tutorials.samples.AppsListviewDemo;
import com.androprogrammer.tutorials.samples.AsyncFileReadDemo;
import com.androprogrammer.tutorials.samples.BatteryLevelDemo;
import com.androprogrammer.tutorials.samples.BottomBarDemo;
import com.androprogrammer.tutorials.samples.ChangeThemeDemo;
import com.androprogrammer.tutorials.samples.CircularImageViewDemo;
import com.androprogrammer.tutorials.samples.DataCachingDemo;
import com.androprogrammer.tutorials.samples.EncryptionDemo;
import com.androprogrammer.tutorials.samples.FragmentAnimationDemo;
import com.androprogrammer.tutorials.samples.FrameAnimationDemo;
import com.androprogrammer.tutorials.samples.ImageAnimationDemo;
import com.androprogrammer.tutorials.samples.ImageSwitcherDemo;
import com.androprogrammer.tutorials.samples.ImageTabViewDemo;
import com.androprogrammer.tutorials.samples.PushNotificationDemo;
import com.androprogrammer.tutorials.samples.StackViewDemo;
import com.androprogrammer.tutorials.samples.SystemSettingDemo;
import com.androprogrammer.tutorials.samples.TabViewDemo;
import com.androprogrammer.tutorials.samples.TakePictureDemo;
import com.androprogrammer.tutorials.samples.Test_Mainlist;
import com.androprogrammer.tutorials.samples.TrackUserDemo;
import com.androprogrammer.tutorials.samples.VideoPlayerDemo;
import com.androprogrammer.tutorials.samples.ViewPagerDemo;
import com.androprogrammer.tutorials.samples.VoiceInputDemo;
import com.androprogrammer.tutorials.util.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Listactivity extends Baseactivity implements SearchView.OnQueryTextListener, RecyclerItemClickListener.OnItemClickListener {

    protected View view;

    @Bind(R.id.recycler_mainlist)
    RecyclerView tutorial_list;
    @Bind(R.id.fab_share)
    FloatingActionButton btShare;

    protected RecyclerView.LayoutManager mLayoutManager;

    protected LayoutManagerType mCurrentLayoutManagerType;
    protected TutorialListAdapter mAdapter;

    protected boolean linearDividerAdded, GridDividerAdded;

    protected File image;

    private static final String TAG = "Listactivity";

    private static final int REQUEST_CODE = 1234;
    private static final int SPAN_COUNT = 2;

    //private ComponentName mDeviceAdminSample;
    private SearchView sv;
    private MenuItem searchItem;
    private MenuItem list_type;

    public static int REQUEST_CODE_ENABLE_ADMIN = 1111;

    public static Listactivity listActivity;
    public Map<String, Object> tutorials;
    public List<String> list_keys;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);
        //rootLogger.setLevel(Level.ALL);

        addListItem();

        /*mainlayout.post(new Runnable() {
            @Override
            public void run() {
                captureScreen();
            }
        });*/

        //Log.d("List", "" + tutorials.size());

        // Launch the activity to have the user enable our admin.
        /*Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                getResources().getString(R.string.admin_receiver_status_disable_warning));
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);*/

        initializeView();

        // To access it from service.
        listActivity = this;

    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_mainlist, container);

        ButterKnife.bind(this,view);

        mLayoutManager = new LinearLayoutManager(Listactivity.this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        //mDeviceAdminSample = new ComponentName(this, DeviceAdminReciver.class);

    }

    private void initializeView() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                list_keys = new ArrayList<String>(tutorials.keySet());

                setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

                mAdapter = new TutorialListAdapter(Listactivity.this, list_keys);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tutorial_list.setAdapter(mAdapter);

                        tutorial_list.addOnItemTouchListener(new RecyclerItemClickListener(Listactivity.this,
                                tutorial_list, Listactivity.this));

                    }
                });

            }
        }).start();

    }

    public static Listactivity getInstance() {
        return listActivity;
    }

    @OnClick(R.id.fab_share)
    public void onClick() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/jpg");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, getApplicationInfo().name);
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
        startActivity(Intent.createChooser(sharingIntent, "Share App using"));

    }

    private void hideViews() {
        //Log.d(TAG, "" + rootLogger.isDebugEnabled());
        //rootLogger.debug("hideview");
        /*toolbar.animate().alpha(0).setDuration(300)
                .translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));*/

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) btShare.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;

        btShare.animate().setDuration(300).translationY(btShare.getHeight() + fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        //rootLogger.debug("showview");
        /*toolbar.animate().alpha(1).setDuration(300).translationY(0)
                .setInterpolator(new DecelerateInterpolator(2));*/
        btShare.animate().setDuration(300).translationY(0)
                .setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void onItemClick(View view, int position) {

        String key = (String) mAdapter.getItem(position);

        /*ObjectAnimator animX = ObjectAnimator.ofFloat(view,"x",view.getWidth(),0);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view,"y",-view.getHeight(),0);

        AnimatorSet animXY = new AnimatorSet();
        animXY.playTogether(animX,animY);
        animXY.setDuration(150);
        animXY.start();*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(view, View.TRANSLATION_Z, view.getWidth(), 0);
            animation.setDuration(500);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.start();
        }

        /*TranslateAnimation anim = new TranslateAnimation(0,0,view.getY(),0);
        anim.setFillAfter(true);
        anim.setDuration(0);
        view.startAnimation(anim);*/

        //view.animate().x(50f).y(100f);

        Bundle b = ActivityOptionsCompat.makeScaleUpAnimation(view, 0,
                (int) view.getY(),
                view.getWidth(), view.getHeight()).toBundle();

        //ActivityOptionsCompat obj = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.activity_open_scale, 0);
        /*ActivityOptionsCompat obj = ActivityOptionsCompat.makeScaleUpAnimation(view, 0,
                                                                               (int) view.getY(), view.getWidth(), view.getHeight());*/

        ActivityCompat.startActivity(this, new Intent((Intent) tutorials.get(key)), b);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public void addListItem() {
        tutorials = new TreeMap<String, Object>();

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
        //tutorials.put("Tutorial Recycler View", new Intent(this, Test_Mainlist.class));
        tutorials.put("Fragment Animation", new Intent(this, FragmentAnimationDemo.class));
        tutorials.put("Stack view ", new Intent(this, StackViewDemo.class));
        tutorials.put("Data Caching ", new Intent(this, DataCachingDemo.class));
        tutorials.put("Data Encryption/Decryption", new Intent(this, EncryptionDemo.class));
        tutorials.put("Bottom Bar Demo", new Intent(this, BottomBarDemo.class));
    }

    private void captureScreen() {

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

        list_type = menu.findItem(R.id.menu_list_style);

        searchItem = menu.findItem(R.id.search);
        sv = (SearchView) MenuItemCompat.getActionView(searchItem);
        sv.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed

                list_keys = new ArrayList<String>(tutorials.keySet());
                mAdapter = new TutorialListAdapter(Listactivity.this, list_keys);

                mAdapter.notifyDataSetChanged();

                tutorial_list.setAdapter(mAdapter);
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded

                return true;  // Return true to expand action view
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (list_type != null) {
            if (mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER) {
                list_type.setIcon(R.mipmap.ic_action_listtype);
            } else {
                list_type.setIcon(R.mipmap.ic_action_gridtype);
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

        switch (item.getItemId()) {

            case R.id.menu_list_style:
                if (mCurrentLayoutManagerType == LayoutManagerType.GRID_LAYOUT_MANAGER) {
                    setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                } else {
                    setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
                }
                this.invalidateOptionsMenu();
                break;

            case R.id.action_aboutus:
                Intent about = new Intent(Listactivity.this, AboutusActivity.class);
                startActivity(about);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                mLayoutManager = new GridLayoutManager(Listactivity.this, SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                if (!GridDividerAdded) {
                    //			tutorial_list.addItemDecoration(new GridLayoutDivider(Listactivity.this,R.dimen.griditem_padding));
                    GridDividerAdded = true;
                }
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(Listactivity.this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

                if (!linearDividerAdded) {
                    //			tutorial_list.addItemDecoration(new LinearLayoutDivider(Listactivity.this, null));
                    linearDividerAdded = true;
                }
                tutorial_list.invalidateItemDecorations();
                break;
            default:
                mLayoutManager = new LinearLayoutManager(Listactivity.this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                if (!linearDividerAdded) {
                    //tutorial_list.addItemDecoration(new LinearLayoutDivider(Test_Mainlist.this, null));
                    linearDividerAdded = true;
                }
        }

        tutorial_list.setLayoutManager(mLayoutManager);
        tutorial_list.scrollToPosition(scrollPosition);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        List<String> filteredModelList = filter(list_keys, s);
        mAdapter.animateTo(filteredModelList);
        tutorial_list.scrollToPosition(0);
        if (!TextUtils.isEmpty(s)) {
        }

        return true;
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the word");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private List<String> filter(List<String> models, String query) {
        query = query.toLowerCase();

        final List<String> filteredModelList = new ArrayList<>();

        for (String row : models) {
            final String text = row.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(row);
            }
        }

        return filteredModelList;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If Voice recognition is successful then it returns RESULT_OK
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    String Query = textMatchList.get(0);
                    if (searchItem != null) {
                        searchItem.expandActionView();
                        sv.setQuery(Query, true);
                    }
                    //word.setText(searchQuery);
                }
                //Result code for various error.
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                Common.showToast(this, "Network Error");
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                Common.showToast(this, "No Match");
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                Common.showToast(this, "Server Error");
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
