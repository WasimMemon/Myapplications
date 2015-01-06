package com.androprogrammer.test.materialapp1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener
{
    private android.support.v7.widget.RecyclerView mRecyclerView;
    private android.support.v7.widget.RecyclerView.Adapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mLayoutManager;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.v4.widget.DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private Button fab;
    private String[] leftSliderData = {"Home", "Android", "Sitemap", "About", "Contact Me"};
    protected String[] myDataset = {"facebook", "Google+", "twitter", "Linkdin", "pintrest"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRef();

        //Set the custom toolbar
        if (toolbar != null)
        {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        )

        {
            public void onDrawerClosed(View view)
            {
                Log.d("MainActivity", "OnDrawerClosed");
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                Log.d("MainActivity", "OnDrawerOpened");
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    private void setRef()
    {
    	// create reference to the xml views
        mRecyclerView = (android.support.v7.widget.RecyclerView) findViewById(R.id.my_recycler_view);
        fab = (Button) findViewById(R.id.addButton);
        drawerLayout = (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.primarydark);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        leftDrawerList = (ListView) findViewById(R.id.list_drawer);

        View list_header = getLayoutInflater().inflate(R.layout.drawerlist_header, null);
        leftDrawerList.addHeaderView(list_header);
        navigationDrawerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast.makeText(getApplicationContext(), "Clicked on " + leftSliderData[position - 1], Toast.LENGTH_LONG).show();
    }
}
