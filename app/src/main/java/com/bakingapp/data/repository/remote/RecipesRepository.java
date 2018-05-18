package com.bakingapp.data.repository.remote;

import com.bakingapp.data.model.Recipe;

import java.util.List;

import rx.Observable;

/**
 * Created by cecy on 11/28/17.
 */

public interface RecipesRepository {
    Observable<List<Recipe>> getRecipes();
}
