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
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.ui.adapter.RecipeStepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecipeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private Recipe mRecipe;
    private Unbinder unbinder;
    @BindView(R.id.rv_steps)
    RecyclerView recyclerViewSteps;
    @BindView(R.id.tv_title_ingredients)
    TextView textViewTitle;
    private RecipeStepAdapter recipeStepAdapter;
    private RecyclerView.LayoutManager layoutManagerStep;


    public RecipeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecipe = mListener.getRecipe();

        layoutManagerStep = new LinearLayoutManager(getContext());
        layoutManagerStep.setAutoMeasureEnabled(true);

        recyclerViewSteps.setLayoutManager(layoutManagerStep);

        recyclerViewSteps.setHasFixedSize(true);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerViewSteps.addItemDecoration(itemDecor);
        recyclerViewSteps.setNestedScrollingEnabled(false);
        recipeStepAdapter = new RecipeStepAdapter(mRecipe.getSteps(), mListener);
        recyclerViewSteps.setAdapter(recipeStepAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentIngredientsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        Recipe getRecipe();
        void showIngredients();
        void showStepDetail(int position);
    }

    @OnClick(R.id.tv_title_ingredients)
    public void onClickIngredients(){
        mListener.showIngredients();
    }
}
