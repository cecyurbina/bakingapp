package com.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.bakingapp.ui.IngredientsList;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new IngredientsList(this.getApplicationContext(),
                intent));
    }
}