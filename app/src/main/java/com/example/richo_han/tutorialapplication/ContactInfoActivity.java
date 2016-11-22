package com.example.richo_han.tutorialapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Intent intent = getIntent();
        String name = intent.getStringExtra(ContactAdapter.EXTRA_CONTACT);
        TextView tvName = (TextView) findViewById(R.id.contact_info_name);
        tvName.setText(name);
    }
}
