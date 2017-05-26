package com.example.android.BakingApp.UI;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.BakingApp.R;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
    }

}
