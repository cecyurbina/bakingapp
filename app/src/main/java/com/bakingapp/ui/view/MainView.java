package com.bakingapp.ui.view;

import android.content.Context;

import com.bakingapp.data.model.Recipe;

import java.util.List;

public interface MainView {
    void getRecipes();

    void recipesSuccess(List<Recipe> listRecipe);

    void recipesError(String error);

    Context getView();
}
