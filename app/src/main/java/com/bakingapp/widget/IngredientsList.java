package com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bakingapp.R;
import com.bakingapp.utils.Utils;

import java.util.ArrayList;

public class IngredientsList implements RemoteViewsService.RemoteViewsFactory {
    ArrayList<String> items;
    private Context ctxt;

    public IngredientsList(Context ctxt, Intent intent) {
        this.ctxt = ctxt;
        items = Utils.getIngredients(ctxt);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return (items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                R.layout.item_ingredient);
        row.setTextViewText(R.id.tv_ingredient, items.get(position));
        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {
        items = Utils.getIngredients(ctxt);
    }
}