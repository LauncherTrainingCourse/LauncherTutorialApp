package com.example.richo_han.tutorialapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Intent intent = getIntent();
        Contact contact = intent.getParcelableExtra(ContactAdapter.EXTRA_CONTACT);

        ImageView ivPhoto = (ImageView) findViewById(R.id.contact_info_photo);
        TextView tvName = (TextView) findViewById(R.id.contact_info_name);
        TextView tvPhone = (TextView) findViewById(R.id.contact_info_phone);

        ivPhoto.setImageResource(R.drawable.steve);
        tvName.setText(contact.name);
        tvPhone.setText(contact.phone);
    }
}
