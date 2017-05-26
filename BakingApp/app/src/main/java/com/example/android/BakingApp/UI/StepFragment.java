package com.example.android.BakingApp.UI;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.BakingApp.Data.Steps;
import com.example.android.BakingApp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
/**
 * Created by MCLAB on 5/12/2017.
 */
public class StepFragment extends Fragment{
    private int mStepId;
    private ArrayList<Steps> stepList;
    private String stepDetailList ="stepList";
    private String listIndex = "stepId";
    private String mRecipeName;

    private SimpleExoPlayer simpleExoPlayer;

    private String stepVideoUri;

    private static final String TAG = "StepFragment";
    public StepFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            mStepId = savedInstanceState.getInt(listIndex);
            stepList = savedInstanceState.getParcelableArrayList(stepDetailList);

        }


        View stepView = inflater.inflate(R.layout.steps_page, container,false);



        final SimpleExoPlayerView step_videos = (SimpleExoPlayerView)stepView.findViewById(R.id.step_videos);

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            ImageButton prev_button = (ImageButton) stepView.findViewById(R.id.previous_step);
            ImageButton next_button = (ImageButton) stepView.findViewById(R.id.next_step);
            ImageView mImageView = (ImageView) stepView.findViewById(R.id.step_image);
            TextView stepFullDescription = (TextView) stepView.findViewById(R.id.step_description);
            stepFullDescription.setText(stepList.get(mStepId).getStepDescription());
            prev_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mStepId>0) {
                        StepFragment previousFragment = new StepFragment();
                        previousFragment.setmStepId(mStepId - 1);
                        previousFragment.setStepList(stepList);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.steps_container, previousFragment)
                                .commit();
                    }else{
                        Toast.makeText(getContext(),"This is the first step",Toast.LENGTH_SHORT);
                    }


                }
            });
            next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mStepId<stepList.size()-1) {
                        StepFragment nextFragment = new StepFragment();
                        nextFragment.setmStepId(mStepId + 1);
                        nextFragment.setStepList(stepList);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.steps_container, nextFragment)
                                .commit();
                    }else{
                        Toast.makeText(getContext(),"Reached the end",Toast.LENGTH_SHORT);

                    }

                }
            });
        }
        stepVideoUri = stepList.get(mStepId).getStepVideoURL();
        if (stepVideoUri != "") {
            createExoPlayer();
            step_videos.setPlayer(simpleExoPlayer);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                    Util.getUserAgent(getActivity(), "BakingApp"), bandwidthMeter);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videoSource = new ExtractorMediaSource(Uri.parse(stepVideoUri),
                    dataSourceFactory, extractorsFactory, null, null);
            simpleExoPlayer.prepare(videoSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }


        return stepView;
    }


    private void createExoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),trackSelector);
    }


    public void setmStepId(int stepId) {
        mStepId = stepId;

    }

    public void setStepList(ArrayList<Steps> stepsArrayList) {
        stepList = stepsArrayList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(listIndex,mStepId);
        outState.putParcelableArrayList(stepDetailList,(ArrayList<Steps>) stepList);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }


}
