package com.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.bakingapp.R;
import com.bakingapp.ui.fragment.RecipeIngredientsFragment;
import com.bakingapp.ui.fragment.RecipeStepFragment;

public class ContainerDetailRecipeActivity extends AppCompatActivity {
    private static final String TAG = ContainerDetailRecipeActivity.class.getName();
    public static final String KEY_FRAGMENT = "KEY_FRAGMENT";
    public static final String KEY_RECIPE = "KEY_RECIPE";
    public static final String KEY_RECIPE_STEP_POSITON = "KEY_RECIPE_STEP_POSITION";
    public static final int STEP_FRAGMENT = 101;
    public static final int INGREDIENTS_FRAGMENT = 100;

    private String recipeJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_detail_recipe);
        Bundle b = getIntent().getExtras();
        if (b!=null) {
            loadFragment(b);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return (super.onOptionsItemSelected(menuItem));
        }
    }

    private void loadFragment(Bundle b) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = b.getInt(KEY_FRAGMENT);
        recipeJson = b.getString(KEY_RECIPE);
        switch (id) {
            case INGREDIENTS_FRAGMENT:
                RecipeIngredientsFragment recipeIngredientsFragment = RecipeIngredientsFragment.newInstance(recipeJson);
                fragmentManager.beginTransaction().replace(R.id.fl_container, recipeIngredientsFragment).commit();
                break;
            case STEP_FRAGMENT:
                int position = b.getInt(KEY_RECIPE_STEP_POSITON);
                RecipeStepFragment recipeStepFragment = RecipeStepFragment.newInstance(recipeJson, position);
                fragmentManager.beginTransaction().replace(R.id.fl_container, recipeStepFragment).commit();
                break;
            default:
                Log.d(TAG, getString(R.string.error_message));
                break;
        }
    }

}
