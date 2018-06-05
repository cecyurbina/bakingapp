package com.bakingapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.data.model.Step;
import com.bakingapp.ui.fragment.RecipeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by s.urbina.coronado on 5/3/2018.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {
    private static List<Step> mDataset;
    private RecipeFragment.OnFragmentInteractionListener mListener;

    public RecipeStepAdapter(List<Step> myDataSet, RecipeFragment.OnFragmentInteractionListener listener){
        mDataset = myDataSet;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String shortDescription = mDataset.get(position).getShortDescription();
        String numberStep = String.valueOf(position+1);
        String text = holder.itemView.getContext().getString(R.string.step_number, numberStep, shortDescription);
        holder.tvTitleStep.setText(text);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_title_step)
        TextView tvTitleStep;
        Step step;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            step = mDataset.get(getAdapterPosition());
            mListener.showStepDetail(getAdapterPosition());
            /*Gson gson = new Gson();
            String recipeJson = gson.toJson(recipe);
            Intent intent = new Intent(view.getView(), RecipeActivity.class);
            intent.putExtra(KEY_RECIPE, recipeJson);
            view.getView().startActivity(intent);*/
        }
    }
}
