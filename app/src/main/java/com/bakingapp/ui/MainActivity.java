package com.bakingapp.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bakingapp.R;
import com.bakingapp.ui.adapter.RecipeAdapter;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.presenter.MainPresenter;
import com.bakingapp.presenter.MainPresenterImpl;
import com.bakingapp.ui.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

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
        if (getResources().getBoolean(R.bool.isTablet)){
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        recyclerView.setLayoutManager(layoutManager);
        recipeAdapter = new RecipeAdapter(recipeList, this);
        recyclerView.setAdapter(recipeAdapter);
        presenter.getRecipes();
    }

    @Override
    public void getRecipes() {

    }

    @Override
    public void recipesSuccess(List<Recipe> listRecipe) {
        recipeList.clear();
        recipeList.addAll(listRecipe);
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void recipesError(String error) {

    }

    @Override
    public Context getView() {
        return this.getApplicationContext();
    }


}
