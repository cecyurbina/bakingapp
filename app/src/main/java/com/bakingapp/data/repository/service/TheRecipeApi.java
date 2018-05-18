package com.bakingapp.data.repository.service;
import retrofit2.http.GET;
import rx.Observable;

import com.bakingapp.data.model.Recipe;

import java.util.List;

/**
 * Created by cecy on 9/19/17.
 */

public interface TheRecipeApi {

    @GET("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();

}
