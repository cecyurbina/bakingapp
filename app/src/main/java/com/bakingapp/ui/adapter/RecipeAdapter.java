package com.bakingapp.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.ui.activity.RecipeActivity;
import com.bakingapp.ui.view.MainView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bakingapp.ui.activity.RecipeActivity.KEY_RECIPE;

/**
 * Created by s.urbina.coronado on 5/3/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private static List<Recipe> mDataset;
    private MainView view;

    public RecipeAdapter(List<Recipe> myDataSet, MainView mainView){
        mDataset = myDataSet;
        view = mainView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTest.setText(mDataset.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_ingredient)
        TextView tvTest;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = mDataset.get(getAdapterPosition());
            Gson gson = new Gson();
            String recipeJson = gson.toJson(recipe);
            Intent intent = new Intent(view.getContext(), RecipeActivity.class);
            intent.putExtra(KEY_RECIPE, recipeJson);
            view.getContext().startActivity(intent);
        }
    }
}
