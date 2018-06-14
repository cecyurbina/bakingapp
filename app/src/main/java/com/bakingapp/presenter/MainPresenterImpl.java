package com.bakingapp.presenter;


import android.support.annotation.Nullable;

import com.bakingapp.IdlingResource.SimpleIdlingResource;
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

public class MainPresenterImpl implements MainPresenter {
    private MainView mView;
    private CompositeSubscription mSubscription;
    private RecipesRepository mRecipesRepository;
    private SimpleIdlingResource mSimpleIdlingResource;

    public MainPresenterImpl(MainView view, @Nullable final SimpleIdlingResource idlingResource) {
        this.mView = view;
        this.mSubscription = new CompositeSubscription();
        this.mRecipesRepository = new RecipesRepositoryImpl();
        this.mSimpleIdlingResource = idlingResource;
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
                if (mSimpleIdlingResource != null) {
                    mSimpleIdlingResource.setIdleState(false);
                }
                if (mView != null) {
                    mView.recipesSuccess(recipes);
                    if (mSimpleIdlingResource != null){
                        mSimpleIdlingResource.setIdleState(true);
                    }
                }
            }
        });
    }

    @Override
    public void addSubscription(Subscription subscription) {
        mSubscription.add(subscription);
    }
}
