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
public class TestingEmailActivity {

    @Rule
    public ActivityTestRule<UserFilterPreferenceActivity> mActivityTestRule = new ActivityTestRule<>(UserFilterPreferenceActivity.class);

    @Test
    public void testingEmailActivity() {
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
            Thread.sleep(5000);
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
        appCompatEditText3.perform(replaceText("CF24 3UW"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.text_postcode), withText("CF24 3UW"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.v7.widget.LinearLayoutCompat")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

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

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview_pharmacies),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(0);
        appCompatTextView.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview_pharmacys_details),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)))
                .atPosition(3);
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView2), withText("Please enter your message below"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Please enter your message below")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.users_msg_edittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.send_email_btn),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.users_msg_edittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.users_msg_edittext),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("Hi."), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.users_msg_edittext), withText("Hi."),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        editText2.check(matches(withText("Hi.")));

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
