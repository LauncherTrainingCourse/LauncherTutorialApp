package com.example.richo_han.tutorialapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{
    public final static String EXTRA_NAME = "com.example.tutorialapp.NAME";
    public final static String EXTRA_GENDER = "com.example.tutorialapp.GENDER";
    public final static String EXTRA_EMAIL = "com.example.tutorialapp.EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
