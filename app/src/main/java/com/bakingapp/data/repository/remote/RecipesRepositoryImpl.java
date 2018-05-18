package com.bakingapp.data.repository.remote;

import com.bakingapp.data.model.Recipe;
import com.bakingapp.data.repository.service.RecipeApi;
import com.bakingapp.data.repository.service.TheRecipeApi;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cecy on 11/28/17.
 */

public class RecipesRepositoryImpl implements RecipesRepository{
    private final TheRecipeApi api;

    public RecipesRepositoryImpl(){
        this.api = RecipeApi.getApiService();
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return api.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }
}
