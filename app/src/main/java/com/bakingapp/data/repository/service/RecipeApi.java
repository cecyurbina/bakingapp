package com.bakingapp.data.repository.service;


import com.bakingapp.utils.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cecy on 11/28/17.
 */

public class RecipeApi {
    private static final String API_URL = Utils.BASE_URL;
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static TheRecipeApi API_SERVICE;

    public static TheRecipeApi getApiService() {
        if (API_SERVICE == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(new OkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.client(httpClient.build()).build();
            API_SERVICE = retrofit.create(TheRecipeApi.class);
        }
        return API_SERVICE;
    }
}
