package com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;

import com.bakingapp.ui.activity.ContainerDetailRecipeActivity;
import com.bakingapp.ui.activity.MainActivity;
import com.bakingapp.ui.activity.RecipeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.bakingapp.ui.activity.RecipeActivity.KEY_RECIPE;
/*
Test phone view opens an activity to see steps details and tablet view shows details on same view
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeActivityTest {

    private static final String EXTRA_RECIPE = "{ \"id\": 1, \"name\": \"Nutella Pie\", \"ingredients\": [ { \"quantity\": 2, \"measure\": \"CUP\", \"ingredient\": \"Graham Cracker crumbs\" }, { \"quantity\": 6, \"measure\": \"TBLSP\", \"ingredient\": \"unsalted butter, melted\" }, { \"quantity\": 0.5, \"measure\": \"CUP\", \"ingredient\": \"granulated sugar\" }, { \"quantity\": 1.5, \"measure\": \"TSP\", \"ingredient\": \"salt\" }, { \"quantity\": 5, \"measure\": \"TBLSP\", \"ingredient\": \"vanilla\" }, { \"quantity\": 1, \"measure\": \"K\", \"ingredient\": \"Nutella or other chocolate-hazelnut spread\" }, { \"quantity\": 500, \"measure\": \"G\", \"ingredient\": \"Mascapone Cheese(room temperature)\" }, { \"quantity\": 1, \"measure\": \"CUP\", \"ingredient\": \"heavy cream(cold)\" }, { \"quantity\": 4, \"measure\": \"OZ\", \"ingredient\": \"cream cheese(softened)\" } ], \"steps\": [ { \"id\": 0, \"shortDescription\": \"Recipe Introduction\", \"description\": \"Recipe Introduction\", \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\", \"thumbnailURL\": \"\" }, { \"id\": 1, \"shortDescription\": \"Starting prep\", \"description\": \"1. Preheat the oven to 350°F. Butter a 9\\\" deep dish pie pan.\", \"videoURL\": \"\", \"thumbnailURL\": \"\" }, { \"id\": 2, \"shortDescription\": \"Prep the cookie crust.\", \"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\", \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\", \"thumbnailURL\": \"\" }, { \"id\": 3, \"shortDescription\": \"Press the crust into baking form.\", \"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\", \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\", \"thumbnailURL\": \"\" }, { \"id\": 4, \"shortDescription\": \"Start filling prep\", \"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\", \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\", \"thumbnailURL\": \"\" }, { \"id\": 5, \"shortDescription\": \"Finish filling prep\", \"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\", \"videoURL\": \"\", \"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\" }, { \"id\": 6, \"shortDescription\": \"Finishing Steps\", \"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\", \"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\", \"thumbnailURL\": \"\" } ], \"servings\": 8, \"image\": \"\" }";

    @Rule
    public IntentsTestRule<RecipeActivity> intentsTestRule
            = new IntentsTestRule<RecipeActivity>(RecipeActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, RecipeActivity.class);
            result.putExtra(KEY_RECIPE, EXTRA_RECIPE);
            return result;
        }
    };

    @Test
    public void clickOnRecyclerViewItem_runsRecipeStepActivityIntent() {

        onView(withId(R.id.rv_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        if (isTablet()) {
            onView(withId(R.id.tv_short_description)).check(matches(isDisplayed()));
        } else {
            intended(hasComponent(ContainerDetailRecipeActivity.class.getName()));

        }


    }

    private boolean isTablet() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext().getResources().getBoolean(R.bool.isTablet);

    }


}