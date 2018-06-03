package com.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
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
import com.bakingapp.ui.adapter.RecipeAdapter;
import com.bakingapp.ui.adapter.RecipeIngredientAdapter;
import com.bakingapp.ui.adapter.RecipeStepAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Recipe mRecipe;
    private Unbinder unbinder;
    //@BindView(R.id.rv_ingredients)
    //RecyclerView recyclerViewIngredients;
    @BindView(R.id.rv_steps)
    RecyclerView recyclerViewSteps;
    @BindView(R.id.tv_title_ingredients)
    TextView textViewTitle;
    //private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    //private RecyclerView.LayoutManager layoutManagerIngredient;
    private RecyclerView.LayoutManager layoutManagerStep;


    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(String param1, String param2) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecipe = mListener.getRecipe();

        //layoutManagerIngredient = new LinearLayoutManager(getContext());
        layoutManagerStep = new LinearLayoutManager(getContext());
        layoutManagerStep.setAutoMeasureEnabled(true);
        //layoutManagerIngredient.setAutoMeasureEnabled(true);

        //recyclerViewIngredients.setLayoutManager(layoutManagerIngredient);
        recyclerViewSteps.setLayoutManager(layoutManagerStep);

        recyclerViewSteps.setHasFixedSize(true);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerViewSteps.addItemDecoration(itemDecor);
        //recyclerViewIngredients.setHasFixedSize(true);
        //recyclerViewIngredients.setNestedScrollingEnabled(false);
        recyclerViewSteps.setNestedScrollingEnabled(false);


        //recipeIngredientAdapter = new RecipeIngredientAdapter(mRecipe.getIngredients());
        //recyclerViewIngredients.setAdapter(recipeIngredientAdapter);

        recipeStepAdapter = new RecipeStepAdapter(mRecipe.getSteps(), mListener);
        recyclerViewSteps.setAdapter(recipeStepAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        Recipe getRecipe();
        void showIngredients();
        void showStepDetail(int position);
    }

    @OnClick(R.id.tv_title_ingredients)
    public void onClickIngredients(){
        // do your things
        mListener.showIngredients();
    }
}
