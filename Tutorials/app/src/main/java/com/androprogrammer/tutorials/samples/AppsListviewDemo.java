package com.androprogrammer.tutorials.samples;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.adapters.AppsListAdapter;
import com.androprogrammer.tutorials.customviews.CustomListView;
import com.androprogrammer.tutorials.util.Common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppsListviewDemo extends Baseactivity implements AdapterView.OnItemClickListener{

    protected View view;
    protected com.androprogrammer.tutorials.customviews.CustomListView lv_installedapps;

    protected PackageManager packageManager;
    protected ProgressDialog pDialog;

    private AppsListAdapter mAdapter;
    private ArrayList<PackageInfo> results_user_app;
    private static String TAG = "AppsListviewDemo";

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

        setSimpleToolbar(true);
        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));
        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new LoadAppList().execute(packageManager);
    }

    @Override
    public void setReference() {

        view = LayoutInflater.from(this).inflate(R.layout.activity_appslistview_demo,container);
        lv_installedapps = (com.androprogrammer.tutorials.customviews.CustomListView)
                view.findViewById(R.id.lv_allapps);

        lv_installedapps.setOnItemClickListener(this);

        packageManager = getPackageManager();

        results_user_app = new ArrayList<>();

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

   
    public void showDialog()
    {
        if (pDialog == null)
        {
            pDialog = new ProgressDialog(this);
        }

        pDialog.setMessage("Please wait");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void hideDialog()
    {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.row_cb_visiblity);
        TextView tv = (TextView) view.findViewById(R.id.row_tv_appname);
        TextView tv_package = (TextView) view.findViewById(R.id.row_tv_packagename);
        cb.performClick();

    }


    private class LoadAppList extends AsyncTask<PackageManager, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            showDialog();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            lv_installedapps.setAdapter(mAdapter);
            hideDialog();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(PackageManager... params)
        {

            PackageManager pm1 = params[0];
            List<PackageInfo> list_user_app = pm1
                    .getInstalledPackages(PackageManager.GET_META_DATA);
            for (int n = 0; n < list_user_app.size(); n++) {
                if (((list_user_app.get(n).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)!= true) {
                    // icon =
                    // PackageInfo.applicationInfo.loadIcon(getPackageManager());
                    //results_user_app.add(icon);

                    results_user_app.add(list_user_app.get(n));

                    Collections.sort(results_user_app, new Comparator<PackageInfo>() {
                        @Override
                        public int compare(PackageInfo o1,PackageInfo o2)
                        {
                            // compare and return sorted packagelist.
                            return o1.applicationInfo.loadLabel(getPackageManager()).toString()
                                    .compareToIgnoreCase(o2.applicationInfo.loadLabel(getPackageManager())
                                            .toString());
                        }
                    });

                }
            }

            mAdapter = new AppsListAdapter(AppsListviewDemo.this, results_user_app, packageManager);

            return null;
        }
    }
}
