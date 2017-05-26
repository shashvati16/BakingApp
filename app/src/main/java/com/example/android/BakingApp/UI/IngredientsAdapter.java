package com.example.android.BakingApp.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.BakingApp.R;

import timber.log.Timber;

/**
 * Created by MCLAB on 5/16/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {
    private String[] mIngredients;
    private Context mContext;


    public IngredientsAdapter(Context mContext,String[] mIngredients) {
        this.mContext = mContext;
        this.mIngredients = mIngredients;
    }

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId= R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttacheToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttacheToParentImmediately);
        return new IngredientsAdapter.IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {
        String eachIngredients = mIngredients[position];
        if (eachIngredients!=null){
            holder.ingredients.setText(eachIngredients);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients) {
            return 0;
        } else {
            Timber.d("No of Ingredients" + String.valueOf(mIngredients.length));
            return mIngredients.length;
        }

    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView ingredients;

        public IngredientsAdapterViewHolder(View itemView) {
            super(itemView);
            ingredients = (TextView) itemView.findViewById(R.id.ingredients);
        }
    }
}
