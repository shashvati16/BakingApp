package com.example.android.BakingApp.Utilities;

import com.example.android.BakingApp.Data.Recipes;
import com.example.android.BakingApp.Data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
/**
 * Created by MCLAB on 5/6/2017.
 */
public class NetworkUtil {

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
    public static Recipes[] getRecipesObjectsFromJson(String recipeJson) throws JSONException {
        final String id = "id";
        final String recipeName = "name";
        final String recipeIngredients = "ingredients";
        final String recipeSteps = "steps";
        final String ingredientQuantity = "quantity";
        final String ingredientMeasure = "measure";
        final String ingredientIngredient = "ingredient";
        final String stepDescription = "description";
        final String stepVideoUrl = "videoURL";
        final String stepThumbnailUrl = "thumbnailURL";
        final String stepShortDescription = "shortDescription";
        JSONArray recipeArray= new JSONArray(recipeJson);
        Recipes[] recipe = new Recipes[recipeArray.length()];
        for (int i=0;i<recipeArray.length();i++){
            JSONObject recipeObject= recipeArray.getJSONObject(i);
            recipe[i]= new Recipes();
            recipe[i].setId(recipeObject.getInt(id));
            recipe[i].setName(recipeObject.getString(recipeName));
            JSONArray ingredientsArray=recipeObject.getJSONArray(recipeIngredients);
            String[] ingredients = new String[ingredientsArray.length()];
            for(int j=0;j<ingredientsArray.length();j++){
                JSONObject ingredientObject = ingredientsArray.getJSONObject(j);
                ingredients[j]= String.valueOf(ingredientObject.getDouble(ingredientQuantity)) + " " + ingredientObject.getString(ingredientMeasure)
                        + " " + ingredientObject.getString(ingredientIngredient);

            }
            recipe[i].setIngredientList(ingredients);
            JSONArray stepsArray = recipeObject.getJSONArray("steps");
            Steps[] steps = new Steps[stepsArray.length()];

            for (int k=0;k<stepsArray.length();k++){
                JSONObject stepsObject = stepsArray.getJSONObject(k);
                steps[k] = new Steps();
                steps[k].setStepId(stepsObject.getInt("id"));
                steps[k].setStepDescription(stepsObject.getString("description"));
                steps[k].setStepShortDescription(stepsObject.getString("shortDescription"));
                if(stepsObject.getString("videoURL")!= "") {
                    steps[k].setStepVideoURL(stepsObject.getString("videoURL"));
                }else {
                    steps[k].setStepVideoURL(stepsObject.getString("thumbnailURL"));
                }
                recipe[i].setStepsList(steps);
            }

        }
        return recipe;
    }


}
