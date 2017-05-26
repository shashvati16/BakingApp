package com.example.android.BakingApp.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MCLAB on 5/8/2017.
 */

public class Steps implements Parcelable{
    int stepId;
    String stepDescription;
    String stepVideoURL;
    String stepShortDescription;

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getStepVideoURL() {
        return stepVideoURL;
    }

    public void setStepVideoURL(String stepVideoURL) {
        this.stepVideoURL = stepVideoURL;
    }
    public String getStepShortDescription() {
        return stepShortDescription;
    }

    public void setStepShortDescription(String stepShortDescription) {
        this.stepShortDescription = stepShortDescription;
    }
    public Steps() {

    }


    protected Steps(Parcel in) {
        stepId = in.readInt();
        stepDescription = in.readString();
        stepVideoURL = in.readString();
        stepShortDescription = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(stepDescription);
        dest.writeString(stepVideoURL);
        dest.writeString(stepShortDescription);
    }
}
