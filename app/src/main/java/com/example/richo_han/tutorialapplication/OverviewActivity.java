package com.example.richo_han.tutorialapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class OverviewActivity extends AppCompatActivity {

    public final static String TAG = OverviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
         ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
         pager.setAdapter(new ViewPagerAdapter(OverviewActivity.this, getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
         TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
         tabs.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Log.i(TAG, "Settings button pushed.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
