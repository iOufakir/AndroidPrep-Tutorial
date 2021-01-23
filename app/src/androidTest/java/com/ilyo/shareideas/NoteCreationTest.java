package com.ilyo.shareideas;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by iLyas Dev on 13/01/2021
 */
@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

    private static DataManager dataManagerInstance;

    @BeforeClass
    public static void setUp() {
        dataManagerInstance = DataManager.getInstance();
    }

    // This is a rule that can start activity
    @Rule
    public ActivityScenarioRule<NoteListActivity> mNoteActivityRule = new ActivityScenarioRule<>(NoteListActivity.class);


    @Test
    public void createNewNote() {
        // To click on the button
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click());

        // Click first on the spinner
        Espresso.onView(ViewMatchers.withId(R.id.spinner_courses)).perform(ViewActions.click());
        // select a data from the spinner
        final CourseInfo courseInfo = dataManagerInstance.getCourse("java_lang");
        Espresso.onData(Matchers.allOf(Matchers.instanceOf(CourseInfo.class), Matchers.equalTo(courseInfo)))
                .perform(ViewActions.click());
        // Test the value select in the spinner is the same we want
        Espresso.onView(ViewMatchers.withId(R.id.spinner_courses))
                .check(ViewAssertions.matches(ViewMatchers.withSpinnerText(
                        Matchers.containsString(courseInfo.getTitle())
                )));

        // Type texts
        Espresso.onView(ViewMatchers.withId(R.id.editText_title))
                .perform(ViewActions.typeText("Test note title"))
                // check in the same time
                .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("Test note title"))));
        Espresso.onView(ViewMatchers.withId(R.id.text_note_content))
                .perform(ViewActions.typeText("Test note Body"), ViewActions.closeSoftKeyboard());

        // Test edit text if it contains a specific value
        Espresso.onView(ViewMatchers.withId(R.id.text_note_content))
                .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("Test note Body"))));

        // press back button
        Espresso.pressBack();

        // Check if the data was inserted
        int noteIndex = dataManagerInstance.getNotes().size() - 1;
        NoteInfo noteInfo = dataManagerInstance.getNotes().get(noteIndex);

        // Test using jUnit
        Assert.assertEquals(dataManagerInstance.getCourse("java_lang"), noteInfo.getCourse());
        Assert.assertEquals("Test note title", noteInfo.getTitle());
        Assert.assertEquals("Test note Body", noteInfo.getText());
    }

}