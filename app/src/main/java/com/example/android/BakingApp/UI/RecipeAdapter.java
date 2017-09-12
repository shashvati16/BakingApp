package com.example.android.BakingApp.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.BakingApp.Data.Recipes;
import com.example.android.BakingApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
/**
 * Created by MCLAB on 5/8/2017.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Recipes[] mRecipes = new Recipes[40];
    private RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipes openRecipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler,Recipes[] recipes) {
        mRecipes = recipes;
        mClickHandler = clickHandler;
    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.recipe_card_text) TextView recipeCard;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }
       @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipes viewRecipe = mRecipes[adapterPosition];
            mClickHandler.onClick(viewRecipe);
        }

    }
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttacheToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttacheToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipes eachRecipe = mRecipes[position];
        if (eachRecipe != null) {
            holder.recipeCard.setText(eachRecipe.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) {
            return 0;
        } else {
            Timber.d("No of recipes" + String.valueOf(mRecipes.length));
            return mRecipes.length;


        }
    }

}
