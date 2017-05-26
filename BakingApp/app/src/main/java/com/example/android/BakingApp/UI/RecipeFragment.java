package com.example.android.BakingApp.UI;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.BakingApp.Data.Recipes;
import com.example.android.BakingApp.Data.Steps;
import com.example.android.BakingApp.Provider.RecipeContract;
import com.example.android.BakingApp.R;
import com.example.android.BakingApp.Utilities.NetworkUtil;
import com.example.android.BakingApp.Utilities.RecipesWidgetProvider;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Recipes[]>, RecipeAdapter.RecipeAdapterOnClickHandler{
    private static final String TAG = "RecipeFragment";
    private Recipes[] recipes;
    private Parcelable[] recipeList;
    private RecipeAdapter rAdapter;

    private RecyclerView mRecyclerView;
    private static final int RECIPE_LOADER_ID = 0;
    public onTextClickListener mCallback;
    public interface onTextClickListener{
        void onTextSelected(int position);
    }
    public RecipeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.recipe_list,container,false);
        if(savedInstanceState == null || !savedInstanceState.containsKey("recipes")) {
            recipeList = (Parcelable[]) recipes;
        }
        else {
            recipeList = savedInstanceState.getParcelableArray("recipes");
        }
        mRecyclerView = (RecyclerView) rv.findViewById(R.id.recipes);
        if(getResources().getBoolean(R.bool.is_tab)==false) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
        }else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
        mRecyclerView.setHasFixedSize(true);
        LoaderManager.LoaderCallbacks<Recipes[]> callback = this;
        Bundle bundleForLoader=null;
        int loaderId=RECIPE_LOADER_ID;
        this.getLoaderManager().initLoader(loaderId,bundleForLoader,callback);
        return rv;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("recipes",recipeList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Recipes[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Recipes[]>(getActivity()) {
            Recipes[] mRecipesData;
            @Override
            protected void onStartLoading() {
                if (mRecipesData != null) {
                    deliverResult(mRecipesData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Recipes[] loadInBackground() {
                String urlString = "https://go.udacity.com/android-baking-app-json";
                mRecipesData = new Recipes[50];
                String recipeResults=null;
                try {
                    URL recipeUrl = new URL(urlString);
                    if(isOnline()==true) {
                        recipeResults= NetworkUtil.getResponseFromHttpUrl(recipeUrl);
                    }
                    mRecipesData = NetworkUtil.getRecipesObjectsFromJson(recipeResults);


                    return  mRecipesData;
                } catch (Exception e) {
                    e.printStackTrace();
                    Timber.d("No data found",e);
                    return null;
                }

            }
            public boolean isOnline() {
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                    int     exitValue = ipProcess.waitFor();
                    return (exitValue == 0);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) { e.printStackTrace(); }

                return false;
            }
            public void deliverResult(Recipes[] mRecipesData){
                mRecipesData = mRecipesData;
                super.deliverResult(mRecipesData);
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Recipes[]> loader, Recipes[] recipeData) {
        recipes = recipeData;
        rAdapter = new RecipeAdapter(this,recipeData);
        mRecyclerView.setAdapter(rAdapter);

    }

    private void insertIngredients(Recipes r) {
        String[] ingredients = r.getIngredientList();

        for (int i = 0; i < ingredients.length; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME, r.getName());
            contentValues.put(RecipeContract.RecipeEntry.COLUMN_INGREDIENT_NAME, ingredients[i]);
            Uri uri=getActivity().getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, contentValues);

        }
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(getActivity(), RecipesWidgetProvider.class));
        getActivity().sendBroadcast(intent);

    }


    @Override
    public void onLoaderReset(Loader<Recipes[]> loader) {

    }


    @Override
    public void onClick(Recipes openRecipe) {
        final Intent intent = new Intent(getActivity(),RecipeDetailActivity.class);
        deleteIngredients();
        insertIngredients(openRecipe);
        intent.putExtra("recipeTitle",openRecipe.getName());
        intent.putExtra("recipeIngredients",openRecipe.getIngredientList());
        ArrayList<Steps> stepsArrayList = openRecipe.getSteps();
        intent.putExtra("stepsList", stepsArrayList);
        startActivity(intent);
    }

    private void deleteIngredients() {
        ContentValues contentValues = new ContentValues();
        int delete = getActivity().getContentResolver().delete(RecipeContract.RecipeEntry.CONTENT_URI, null, null);
    }


}
