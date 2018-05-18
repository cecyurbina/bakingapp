package com.bakingapp.presenter;


import com.bakingapp.data.model.Recipe;
import com.bakingapp.data.repository.remote.RecipesRepository;
import com.bakingapp.data.repository.remote.RecipesRepositoryImpl;
import com.bakingapp.ui.view.MainView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cecy on 5/13/18.
 */

public class MainPresenterImpl implements MainPresenter{
    private MainView mView;
    private CompositeSubscription mSubscription;
    private RecipesRepository mRecipesRepository;
    public MainPresenterImpl(MainView view){
        this.mView = view;
        this.mSubscription = new CompositeSubscription();
        this.mRecipesRepository = new RecipesRepositoryImpl();
    }

    @Override
    public void onCreate() {
        
    }

    @Override
    public void onDestroy() {
        this.mView = null;
        mSubscription.clear();

    }

    @Override
    public void getRecipes() {
        Subscription subscription = mRecipesRepository.getRecipes().subscribe(new Observer<List<Recipe>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.recipesError(e.getLocalizedMessage());
                }
            }

            @Override
            public void onNext(List<Recipe> recipes) {
                if (mView != null) {
                    mView.recipesSuccess(recipes);
                }
            }
        });
    }

    @Override
    public void addSubscription(Subscription subscription) {
        mSubscription.add(subscription);
    }
}
