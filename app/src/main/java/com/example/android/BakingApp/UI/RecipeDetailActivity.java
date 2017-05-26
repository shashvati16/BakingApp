package com.example.android.BakingApp.UI;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.android.BakingApp.Data.Steps;
import com.example.android.BakingApp.R;
import java.util.ArrayList;
import timber.log.Timber;
public class RecipeDetailActivity extends AppCompatActivity implements RecipeFragment.onTextClickListener,
        StepsAdapter.StepsAdapterOnClickHandler{
    RecyclerView ingredients_list;
    RecyclerView steps_list;
    StepsAdapter mStepsAdapter;
    IngredientsAdapter mIngredientsAdapter;
    private boolean mTwoPane;
    private int stepId;
    private String[] ingredients;
    private ArrayList<Steps> stepsArrayList;
    String recipeTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ActionBar actionBar = getSupportActionBar();
        ingredients_list = (RecyclerView) findViewById(R.id.ingredients_list);
        steps_list =(RecyclerView) findViewById(R.id.steps_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(RecipeDetailActivity.this);
        ingredients_list.setLayoutManager(layoutManager);
        ingredients_list.hasFixedSize();
        LinearLayoutManager stepLayoutManager = new LinearLayoutManager(RecipeDetailActivity.this);
        steps_list.setLayoutManager(stepLayoutManager);
        steps_list.hasFixedSize();
        recipeTitle = getIntent().getStringExtra("recipeTitle");
        actionBar.setTitle(recipeTitle);
        ingredients = getIntent().getStringArrayExtra("recipeIngredients");
        mIngredientsAdapter =  new IngredientsAdapter(this, ingredients);
        ingredients_list.setAdapter(mIngredientsAdapter);
        stepsArrayList = getIntent().getParcelableArrayListExtra("stepsList");
        mStepsAdapter=new StepsAdapter(RecipeDetailActivity.this,stepsArrayList);
        steps_list.setAdapter(mStepsAdapter);
        if(findViewById(R.id.baking_layout)!=null){
            mTwoPane=true;
            final ImageButton prev_button = (ImageButton) findViewById(R.id.previous_step);
            prev_button.setVisibility(View.GONE);
            final ImageButton next_button = (ImageButton) findViewById(R.id.next_step);
            next_button.setVisibility(View.GONE);
            if (savedInstanceState==null) {
                actionBar.setTitle(getIntent().getStringExtra("recipe_title"));
                stepsArrayList = getIntent().getParcelableArrayListExtra("stepList");
                stepId = stepsArrayList.get(0).getStepId();
                StepFragment stepFragment = new StepFragment();
                stepFragment.setmStepId(stepId);
                stepFragment.setStepList(stepsArrayList);
                FragmentManager fragmentManager = getSupportFragmentManager();
                Timber.i("starting StepFragment :",stepFragment);
                fragmentManager.beginTransaction()
                        .add(R.id.steps_container, stepFragment)
                        .commit();
            }
        }else {
            mTwoPane=false;
        }
    }

    @Override
    public void onTextSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
        stepId = stepsArrayList.get(position).getStepId();
        if(mTwoPane) {
            StepFragment stepFragment = new StepFragment();
            stepFragment.setmStepId(stepId);
            stepFragment.setStepList(stepsArrayList);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_container, stepFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(Steps stepClicked) {
        Timber.d("Starting StepsActivity");
        Intent intent = new Intent(RecipeDetailActivity.this,StepsActivity.class);
        intent.putExtra("recipe_title",recipeTitle);
        intent.putExtra("stepId",String.valueOf(stepClicked.getStepId()));
        intent.putParcelableArrayListExtra("stepList",stepsArrayList);
        startActivity(intent);
    }
}
