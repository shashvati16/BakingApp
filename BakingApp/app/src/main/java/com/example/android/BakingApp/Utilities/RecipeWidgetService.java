package com.example.android.BakingApp.Utilities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.BakingApp.Provider.RecipeContract;
import com.example.android.BakingApp.R;

import static com.example.android.BakingApp.Provider.RecipeContract.BASE_CONTENT_URI;
import static com.example.android.BakingApp.Provider.RecipeContract.PATH_INGREDIENTS;

/**
 * Created by MCLAB on 5/18/2017.
 */

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetRemoteViewsFactory(this.getApplicationContext(),intent);
    }


}
class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    Cursor mCursor;

    public RecipeWidgetRemoteViewsFactory(Context applicationContext,Intent intent) {
        this.mContext = applicationContext;
    }


    @Override
    public void onCreate() {


    }


    @Override
    public void onDataSetChanged() {
        Uri RECIPE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();
        if(mCursor!=null) mCursor.close();
        final long identityToken = Binder.clearCallingIdentity();

        mCursor = mContext.getContentResolver().query(
                RECIPE_URI,
                null,
                null,
                null,
                RecipeContract.RecipeEntry._ID
        );


        Binder.restoreCallingIdentity(identityToken);


    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor==null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.widgetIngredientNameLabel, mCursor.getString(1));

        return rv;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
