package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PreferenceActivity extends AppCompatActivity {
    //km
    public static final int MAX_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
    }
}