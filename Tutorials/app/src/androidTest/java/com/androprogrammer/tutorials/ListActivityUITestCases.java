package com.androprogrammer.tutorials;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androprogrammer.tutorials.activities.Listactivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListActivityUITestCases {

    @Rule
    public ActivityTestRule<Listactivity> activityRule = new ActivityTestRule<>(
            Listactivity.class,
            true,
            true);

    @Test
    public void testSampleRecyclerVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_mainlist))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCaseForRecyclerClick() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_mainlist))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testCaseForRecyclerScroll() {

        // Get total item of RecyclerView
        RecyclerView recyclerView = activityRule.getActivity().findViewById(R.id.recycler_mainlist);
        int itemCount = recyclerView.getAdapter().getItemCount();

        // Scroll to end of page with position
        Espresso.onView(ViewMatchers.withId(R.id.recycler_mainlist))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));
    }

    @Test
    public void testCaseForRecyclerItemView() {

        Espresso.onView(ViewMatchers.withId(R.id.recycler_mainlist))
                .inRoot(RootMatchers.withDecorView(
                        Matchers.is(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(withViewAtPosition(1, Matchers.allOf(
                        ViewMatchers.withId(R.id.row_tutorial_title), isDisplayed()))));
    }

    public Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
