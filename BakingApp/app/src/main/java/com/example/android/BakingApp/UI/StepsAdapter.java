package com.example.android.BakingApp.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.BakingApp.Data.Steps;
import com.example.android.BakingApp.R;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by MCLAB on 5/12/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {
    private final ArrayList<Steps> mStepsList;
    public StepsAdapterOnClickHandler mClickHandler;

    public interface StepsAdapterOnClickHandler{
        void onClick(Steps stepClicked);
    }


    public StepsAdapter(StepsAdapterOnClickHandler stepClickHandler, ArrayList<Steps> stepsList) {
        mClickHandler = stepClickHandler;
        mStepsList = stepsList;
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId= R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttacheToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttacheToParentImmediately);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {
        final Steps eachStep = mStepsList.get(position);
        if (eachStep!=null){
            holder.recipe_steps_text.setText("Step " + eachStep.getStepShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        if (null == mStepsList) {
            return 0;
        } else {
            Timber.d("No of Steps" + String.valueOf(mStepsList.size()));
            return mStepsList.size();
        }
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView recipe_steps_text;
        public StepsAdapterViewHolder(View itemView) {
            super(itemView);
            recipe_steps_text = (TextView) itemView.findViewById(R.id.recipe_steps_text);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Steps stepClicked =  mStepsList.get(adapterPosition);
            mClickHandler.onClick(stepClicked);
        }
    }
}
