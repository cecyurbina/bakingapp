package com.bakingapp.ui;

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

               /*String[] test = new String[10];
        test[0] = "cero";
        test[1] = "uno";
        test[2] = "dos";
        test[3] = "tres";
        test[4] = "cuatro";
        test[5] = "cinco";
        test[6] = "seis";
        test[7] = "siete";
        test[8] = "ocho";
        test[9] = "nueve";*/
        recipeAdapter = new RecipeAdapter(recipeList);
        recyclerView.setAdapter(recipeAdapter);
        presenter.getRecipes();
        /*RecipeListFragment recipeListFragment = new RecipeListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container, recipeListFragment);
        fragmentTransaction.commit();*/
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
}
