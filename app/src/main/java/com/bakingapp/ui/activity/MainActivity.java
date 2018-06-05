package com.bakingapp.ui.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bakingapp.R;
import com.bakingapp.ui.adapter.RecipeAdapter;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.presenter.MainPresenter;
import com.bakingapp.presenter.MainPresenterImpl;
import com.bakingapp.ui.view.MainView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final String TAG = MainActivity.class.getName();
    private static final String KEY_SAVED_RECIPE_RESULTS = "KEY_SAVED_RECIPE_RESULT";
    private MainPresenter presenter;
    @BindView(R.id.rv_recipe)
    RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Recipe> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenterImpl(this);
        presenter.onCreate();
        recyclerView.setHasFixedSize(true);
        if (getResources().getBoolean(R.bool.isTablet)) {
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        recyclerView.setLayoutManager(layoutManager);
        recipeAdapter = new RecipeAdapter(recipeList, this);
        recyclerView.setAdapter(recipeAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_SAVED_RECIPE_RESULTS)) {
            String json = savedInstanceState.getString(KEY_SAVED_RECIPE_RESULTS);
            Gson gson = new Gson();
            Recipe[] arrayRecipe = gson.fromJson(json, Recipe[].class);
            recipesSuccess(Arrays.asList(arrayRecipe));
        } else {
            getRecipes();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (recipeList != null && recipeList.size() > 0) {
            Gson gson = new Gson();
            String json = gson.toJson(recipeList);
            outState.putString(KEY_SAVED_RECIPE_RESULTS, json);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void getRecipes() {
        presenter.getRecipes();
    }

    @Override
    public void recipesSuccess(List<Recipe> listRecipe) {
        recipeList.clear();
        recipeList.addAll(listRecipe);
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void recipesError(String error) {
        Log.d(TAG, error);
        String text = getString(R.string.error_message);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    @Override
    public Context getView() {
        return this.getApplicationContext();
    }


}
