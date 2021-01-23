package com.ilyo.shareideas;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by iLyas Dev on 17/01/2021
 */
@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {

    // This is a rule that can start our test activity (MainActivity)
    @Rule
    public ActivityScenarioRule<MainActivity> startedActivityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void nextThroughNotes(){

        // Open the drawer layout
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open());
        // Access NavigationView and access notes item on it
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes));
        // Get the recyclerView and click on the first item
        Espresso.onView(ViewMatchers.withId(R.id.list_items))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        List<NoteInfo> listNotes = DataManager.getInstance().getNotes();
        // Check now the content of the recyclerView with our dataManager
        IntStream.range(0, listNotes.size()).forEach( i -> {
            NoteInfo note = listNotes.get(i);
            // Test our UI
            Espresso.onView(ViewMatchers.withId(R.id.spinner_courses))
                    .check(ViewAssertions.matches(ViewMatchers.withSpinnerText(note.getCourse().getTitle())));
            Espresso.onView(ViewMatchers.withId(R.id.editText_title))
                    .check(ViewAssertions.matches(ViewMatchers.withText(note.getTitle())));
            Espresso.onView(ViewMatchers.withId(R.id.text_note_content))
                    .check(ViewAssertions.matches(ViewMatchers.withText(note.getText())));

            if(i < listNotes.size() - 1){
                // Navigate to the other note and also verify if the next button is enabled
                Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.action_next), ViewMatchers.isEnabled()))
                        .perform(ViewActions.click());
            }
        });
        // check if the last note activity have next button disabled
        Espresso.onView(ViewMatchers.withId(R.id.action_next))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())));

        Espresso.pressBack();
    }

}