package com.example.richo_han.tutorialapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {
    public final static String TAG = ContactInfoActivity.class.getSimpleName();
    public Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_info_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        contact = intent.getParcelableExtra(ContactAdapter.EXTRA_CONTACT);

        ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
        contactInfoFragment.setArguments(intent.getExtras());

        FragmentManager manager =  this.getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.info_container, contactInfoFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_contact_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                if(!contact.getFavorite()) {
                    item.setIcon(R.drawable.ic_favorite_black_48dp);
                    contact.setFavorite(true);
                } else {
                    item.setIcon(R.drawable.ic_favorite_border_black_48px);
                    contact.setFavorite(false);
                }
                return true;
            case R.id.action_settings:
                Log.i(TAG, "Settings button pushed.");
                return true;
            case R.id.action_delete:
                Log.i(TAG, "Delete button pushed.");
                Intent data = new Intent();
                data.putExtra(ContactAdapter.EXTRA_CONTACT, contact);
                setResult(RESULT_OK, data);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onBackPressed();
        }
    }
}
