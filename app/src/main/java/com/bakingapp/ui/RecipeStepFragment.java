package com.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.data.model.Step;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RECIPE_JSON = "arg_recipe_json";
    private static final String ARG_RECIPE_STEP_POSITION = "arg_recipe_position";

    // TODO: Rename and change types of parameters
    private Unbinder unbinder;
    private String mRecipeJson;
    private Recipe mRecipe;
    private int mStepPosition;
    @BindView(R.id.tv_short_description)
    TextView tvShortDescription;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_thumbnail)
    ImageView ivThumbnail;

    private OnFragmentInteractionListener mListener;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecipeStepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepFragment newInstance(String param1, int stepPosition) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_JSON, param1);
        args.putInt(ARG_RECIPE_STEP_POSITION, stepPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepPosition = getArguments().getInt(ARG_RECIPE_STEP_POSITION);
            mRecipeJson = getArguments().getString(ARG_RECIPE_JSON);
            Gson gson = new Gson();
            mRecipe = gson.fromJson(mRecipeJson, Recipe.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        unbinder = ButterKnife.bind(this, view);
        Step step = mRecipe.getSteps().get(mStepPosition);
        tvDescription.setText(step.getDescription());
        tvShortDescription.setText(step.getShortDescription());
        if (step.getThumbnailURL() != null && step.getThumbnailURL().length() > 0){
            Glide.with(this).load(step.getThumbnailURL()).into(ivThumbnail);
        }
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
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
