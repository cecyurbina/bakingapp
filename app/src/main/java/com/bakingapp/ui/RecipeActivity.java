package com.bakingapp.ui;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bakingapp.R;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnFragmentInteractionListener{

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mTwoPane = getResources().getBoolean(R.bool.isTablet);

        if (mTwoPane) {

            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeStepFragment stepFragment = new RecipeStepFragment();
                fragmentManager.beginTransaction().add(R.id.fl_step_container, stepFragment).commit();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
