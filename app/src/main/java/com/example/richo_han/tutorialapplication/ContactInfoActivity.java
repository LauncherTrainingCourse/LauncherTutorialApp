package com.example.richo_han.tutorialapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_info_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Contact contact = intent.getParcelableExtra(ContactAdapter.EXTRA_CONTACT);

        ImageView ivPhoto = (ImageView) findViewById(R.id.contact_info_photo);
        TextView tvName = (TextView) findViewById(R.id.contact_info_name);
        TextView tvPhone = (TextView) findViewById(R.id.contact_info_phone);
        TextView tvCompany= (TextView) findViewById(R.id.contact_info_company);
        TextView tvEmail = (TextView) findViewById(R.id.contact_info_email);

        if("male".equals(contact.gender)) {
            ivPhoto.setImageResource(R.drawable.steve);
        } else {
            ivPhoto.setImageResource(R.drawable.kristy);
        }
        tvName.setText(contact.name);
        tvPhone.setText(contact.phone);
        tvCompany.setText(contact.company);
        tvEmail.setText(contact.email);
    }
}
