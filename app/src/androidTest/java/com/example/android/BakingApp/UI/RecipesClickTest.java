package com.example.android.BakingApp.UI;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.android.BakingApp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipesClickTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recipesClickTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.recipe_card_text), withText("Nutella Pie"),
                        childAtPosition(
                                allOf(withId(R.id.card_view),
                                        childAtPosition(
                                                withId(R.id.recipe_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Nutella Pie")));

        ViewInteraction recyclerView = onView(
                allOf(withClassName(is("android.support.v7.widget.RecyclerView")),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(withId(R.id.decor_content_parent)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.ingredients), withText("2.0 CUP Graham Cracker crumbs"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredients_list),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("2.0 CUP Graham Cracker crumbs")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.ingredients), withText("6.0 TBLSP unsalted butter, melted"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredients_list),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("6.0 TBLSP unsalted butter, melted")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.ingredients), withText("0.5 CUP granulated sugar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredients_list),
                                        2),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("0.5 CUP granulated sugar")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.recipe_steps_text), withText("Step Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Step Recipe Introduction")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.recipe_steps_text), withText("Step Starting prep"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Step Starting prep")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.recipe_steps_text), withText("Step Prep the cookie crust."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("Step Prep the cookie crust.")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.recipe_steps_text), withText("Step Prep the cookie crust."),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("Step Prep the cookie crust.")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
