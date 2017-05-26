package com.example.android.BakingApp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by MCLAB on 5/5/2017.
 */

public class Recipes implements Parcelable{
    int id;
    String name;
    String[] ingredientList;

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    ArrayList<Steps> steps;


    private Recipes(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientList = in.createStringArray();
        steps=in.createTypedArrayList(Steps.CREATOR);
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };


    public String[] getIngredientList() {
        return ingredientList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientList(String[] ingredientList) {
        this.ingredientList = ingredientList;
    }
    public Recipes(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeArray(ingredientList);
        dest.writeTypedList(steps);
    }

    public void setStepsList(Steps[] stepsList) {
        steps = new ArrayList<>(stepsList.length);
        for (int i=0;i<stepsList.length;i++){
            steps.add(stepsList[i]);
        }

    }
}
