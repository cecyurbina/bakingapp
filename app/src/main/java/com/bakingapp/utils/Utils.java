package com.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bakingapp.BuildConfig;
import com.bakingapp.data.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cecy on 9/19/17.
 */

public class Utils {
    public static final String BASE_URL = "https://google.com";
    private static final String KEY_INGREDIENTS = "key_ingredients";



    public static ArrayList<String> getIngredients(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_INGREDIENTS, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveIngredients(Context context, List<Ingredient> ingredients){
        ArrayList<String> listIngredients = new ArrayList<>();
        for (Ingredient ingredient: ingredients){
            listIngredients.add(ingredient.getIngredient());
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listIngredients);
        editor.putString(KEY_INGREDIENTS, json);
        editor.apply();
    }

}
