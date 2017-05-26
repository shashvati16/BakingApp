package com.example.android.BakingApp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.android.BakingApp.Data.Steps;
import com.example.android.BakingApp.R;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {
    ArrayList<Steps> stepsArrayList = new ArrayList<Steps>();
    int stepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ActionBar actionBar = getSupportActionBar();

        if (savedInstanceState==null) {
            Intent intent = getIntent();
            actionBar.setTitle(intent.getStringExtra("recipe_title"));
            stepId = Integer.parseInt(intent.getStringExtra("stepId"));
            stepsArrayList = intent.getParcelableArrayListExtra("stepList");
            StepFragment stepFragment = new StepFragment();
            stepFragment.setmStepId(stepId);
            stepFragment.setStepList(stepsArrayList);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepFragment)
                    .commit();

        }
    }
}
