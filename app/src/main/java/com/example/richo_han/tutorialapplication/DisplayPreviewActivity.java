package com.example.richo_han.tutorialapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_preview);
        setTitle("Preview Page");

        Intent intent = getIntent();
        setText(intent, MainActivity.EXTRA_NAME, R.id.text_user);
        setText(intent, MainActivity.EXTRA_GENDER, R.id.text_gender);
        setText(intent, MainActivity.EXTRA_EMAIL, R.id.text_email);
    }

    private void setText(Intent intent, String extra, int id) {
        String value = intent.getStringExtra(extra);
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }

    public void confirm(View view) {
        ConfirmDialogFragment newFragment = new ConfirmDialogFragment();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }

    public void sendForm() {
        final ProgressbarDialogFragment newFragment = new ProgressbarDialogFragment();
        newFragment.show(getSupportFragmentManager(), "loading");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                newFragment.dismiss();

                Context context = getApplicationContext();
                CharSequence text = "Form sent!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
            }
        }, 2000);
    }
}
