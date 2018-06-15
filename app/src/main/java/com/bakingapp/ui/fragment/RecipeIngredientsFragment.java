package com.bakingapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakingapp.R;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.ui.adapter.RecipeIngredientAdapter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link RecipeIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeIngredientsFragment extends Fragment {
    private static final String ARG_RECIPE_JSON = "arg_recipe_json";

    private Unbinder unbinder;
    private String mRecipeJson;
    private Recipe mRecipe;
    @BindView(R.id.rv_ingredients)
    RecyclerView recyclerViewIngredients;
    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecyclerView.LayoutManager layoutManagerIngredient;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeJson Parameter 1.
     * @return A new instance of fragment RecipeIngredientsFragment.
     */
    public static RecipeIngredientsFragment newInstance(String recipeJson) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_JSON, recipeJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipeJson = getArguments().getString(ARG_RECIPE_JSON);
            Gson gson = new Gson();
            mRecipe = gson.fromJson(mRecipeJson, Recipe.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);
        layoutManagerIngredient = new LinearLayoutManager(getContext());
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerViewIngredients.addItemDecoration(itemDecor);
        recyclerViewIngredients.setLayoutManager(layoutManagerIngredient);
        recipeIngredientAdapter = new RecipeIngredientAdapter(mRecipe.getIngredients());
        recyclerViewIngredients.setAdapter(recipeIngredientAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
