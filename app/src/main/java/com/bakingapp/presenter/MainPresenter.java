package com.bakingapp.presenter;

import rx.Subscription;

/**
 * Created by cecy on 5/13/18.
 */

public interface MainPresenter {
    void onCreate();
    void onDestroy();
    void getRecipes();
    void addSubscription(Subscription subscription);
}
