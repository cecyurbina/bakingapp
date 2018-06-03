package com.bakingapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.data.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by s.urbina.coronado on 5/3/2018.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder> {
    private static List<Ingredient> mDataset;

    public RecipeIngredientAdapter(List<Ingredient> myDataSet){
        mDataset = myDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mDataset.get(position);
        holder.tvIngredient.setText(ingredient.getIngredient());
        holder.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
        holder.tvMeasure.setText(String.valueOf(ingredient.getMeasure()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_ingredient)
        TextView tvIngredient;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_measure)
        TextView tvMeasure;
        Ingredient ingredient;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            ingredient = mDataset.get(getAdapterPosition());

            /*Gson gson = new Gson();
            String recipeJson = gson.toJson(recipe);
            Intent intent = new Intent(view.getView(), RecipeActivity.class);
            intent.putExtra(KEY_RECIPE, recipeJson);
            view.getView().startActivity(intent);*/
        }
    }
}
