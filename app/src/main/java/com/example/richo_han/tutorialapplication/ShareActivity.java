package com.example.richo_han.tutorialapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String link = intent.getStringExtra(Intent.EXTRA_TEXT);

        ((TextView) (findViewById(R.id.news_title))).setText(title);
        ((TextView) (findViewById(R.id.news_link))).setText(link);
    }
}
