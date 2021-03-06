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

import com.facebook.stetho.Stetho;

public class OverviewActivity extends AppCompatActivity {

    public final static String TAG = OverviewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Stetho.initializeWithDefaults(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new ViewPagerAdapter(OverviewActivity.this, getSupportFragmentManager()));

        // Give the TabLayout the ViewPager
        TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        tabs.setupWithViewPager(pager);
    }

    /***
     * Used to bind the corresponding menu resource to current activity.
     * @param menu
     * @return true if successfully executed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_overview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /***
     * Execute corresponding actions based on button user pushed.
     * @param item
     * @return true if successfully executed.
     */
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
