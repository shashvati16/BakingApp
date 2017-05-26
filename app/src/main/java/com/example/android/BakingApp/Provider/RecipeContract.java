package com.example.android.BakingApp.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by MCLAB on 5/20/2017.
 */

public class RecipeContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.BakingApp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "plants" directory
    public static final String PATH_INGREDIENTS = "ingredients";

    public static final long INVALID_INGREDIENT_ID = -1;

    public static final class RecipeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();

        public static final String TABLE_NAME = "recipes";
        public static final String COLUMN_INGREDIENT_NAME = "ingredientsName";
        public static final String COLUMN_RECIPE_NAME = "recipeName";

    }
}
