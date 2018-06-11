package com.bakingapp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bakingapp.R;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.ui.fragment.RecipeFragment;
import com.bakingapp.ui.fragment.RecipeIngredientsFragment;
import com.bakingapp.ui.fragment.RecipeStepFragment;
import com.bakingapp.utils.Utils;
import com.google.gson.Gson;

import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity
        implements RecipeFragment.OnFragmentInteractionListener,
        RecipeStepFragment.OnFragmentInteractionListener,
        RecipeIngredientsFragment.OnFragmentIngredientsInteractionListener {
    public static final String KEY_RECIPE = "KEY_RECIPE";
    private boolean mTwoPane;
    private Recipe mRecipe;
    private String recipeJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_RECIPE)) {
            recipeJson = savedInstanceState.getString(KEY_RECIPE);
        } else {
            recipeJson = getIntent().getStringExtra(KEY_RECIPE);
        }
        Gson gson = new Gson();
        mRecipe = gson.fromJson(recipeJson, Recipe.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mTwoPane = getResources().getBoolean(R.bool.isTablet);

        /*if (mTwoPane) {

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeStepFragment stepFragment = new RecipeStepFragment();
                fragmentManager.beginTransaction().add(R.id.fl_step_container, stepFragment).commit();
            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_to_widget:
                Utils.saveIngredients(this, mRecipe.getIngredients());
                return true;
            default:
                return (super.onOptionsItemSelected(menuItem));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (recipeJson != null) {
            outState.putString(KEY_RECIPE, recipeJson);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public Recipe getRecipe() {
        return mRecipe;
    }

    @Override
    public void showIngredients() {
        if (mTwoPane) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipeJson);
                fragmentManager.beginTransaction().replace(R.id.fl_step_container, recipeIngredientsFragment).commit();
        } else {
            Intent i = new Intent(this, ContainerDetailRecipeActivity.class);
            i.putExtra(ContainerDetailRecipeActivity.KEY_FRAGMENT, ContainerDetailRecipeActivity.INGREDIENTS_FRAGMENT);
            i.putExtra(ContainerDetailRecipeActivity.KEY_RECIPE, recipeJson);
            startActivity(i);
        }
    }

    @Override
    public void showStepDetail(int position) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(recipeJson, position);
            fragmentManager.beginTransaction().replace(R.id.fl_step_container, recipeStepFragment).commit();
        } else {
            Intent i = new Intent(this, ContainerDetailRecipeActivity.class);
            i.putExtra(ContainerDetailRecipeActivity.KEY_FRAGMENT, ContainerDetailRecipeActivity.STEP_FRAGMENT);
            i.putExtra(ContainerDetailRecipeActivity.KEY_RECIPE_STEP_POSITON, position);
            i.putExtra(ContainerDetailRecipeActivity.KEY_RECIPE, recipeJson);
            startActivity(i);
        }
    }
}
