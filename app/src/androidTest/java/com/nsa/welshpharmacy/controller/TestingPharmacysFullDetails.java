package com.nsa.welshpharmacy.controller;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.nsa.welshpharmacy.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestingPharmacysFullDetails {

    @Rule
    public ActivityTestRule<UserFilterPreferenceActivity> mActivityTestRule = new ActivityTestRule<>(UserFilterPreferenceActivity.class);

    @Test
    public void testingPharmacysFullDetails() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.check_minor_ailments), withText("Minor ailments"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.text_postcode),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.text_postcode),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("C"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.text_postcode), withText("C"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("CF"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.text_postcode), withText("CF"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.text_postcode), withText("CF"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("CF24 3UW"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.text_postcode), withText("CF24 3UW"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.submit_button), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        4),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.text1), withText("Welsh's pharmacy"),
                        childAtPosition(
                                allOf(withId(R.id.listview_pharmacies),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Welsh's pharmacy")));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview_pharmacies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0);
        appCompatTextView.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Welsh's pharmacy"),
                        childAtPosition(
                                allOf(withId(R.id.listview_pharmacys_details),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Welsh's pharmacy")));

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.text1), withText("01516252544"),
                        childAtPosition(
                                allOf(withId(R.id.listview_pharmacys_details),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("01516252544")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("CH48 4EF"),
                        childAtPosition(
                                allOf(withId(R.id.listview_pharmacys_details),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("CH48 4EF")));

        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.text1), withText("xd@xd.com"),
                        childAtPosition(
                                allOf(withId(R.id.listview_pharmacys_details),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                3),
                        isDisplayed()));
        textView5.check(matches(withText("xd@xd.com")));

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.text1), withText("www.thehubpharmacy.com/welshs-pharmacy-wirral/"),
                        childAtPosition(
                                allOf(withId(R.id.listview_pharmacys_details),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                4),
                        isDisplayed()));
        textView6.check(matches(withText("www.thehubpharmacy.com/welshs-pharmacy-wirral/")));

        ViewInteraction button = onView(
                allOf(withId(R.id.button_to_map),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.second_fragment_layout),
                                        2),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

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
