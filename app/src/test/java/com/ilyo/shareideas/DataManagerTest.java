package com.ilyo.shareideas;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.Assert.*;

/**
 * Created by iLyas Dev on 10/01/2021
 */
public class DataManagerTest {

    private static DataManager dataManagerInstance;

    @BeforeClass
    public static void classSetUp(){
        dataManagerInstance =  DataManager.getInstance();
    }


    // Execute this method before each test
    @Before
    public void setUp(){
        dataManagerInstance.getNotes().clear();
        dataManagerInstance.initializeExampleNotes();
    }

    @Test
    public void createNewNote() {
        // Create new note
        int noteIndex = dataManagerInstance.createNewNote();

        // Give it values
        NoteInfo noteInfo = dataManagerInstance.getNotes().get(noteIndex);
        noteInfo.setCourse(new CourseInfo("course_id_1", "Course Title", null));
        noteInfo.setTitle("Test new Title Note");
        noteInfo.setText("Test new Text Note");

        // Test if values if equals
        NoteInfo compareNoteInfo = DataManager.getInstance().getNotes().get(noteIndex);

        assertEquals(noteInfo.getCourse(), compareNoteInfo.getCourse());
        assertEquals(noteInfo.getTitle(), compareNoteInfo.getTitle());
        assertEquals(noteInfo.getText(), compareNoteInfo.getText());
    }


    // Test if we will find two inserted noted on the DataManager notes list
    @Test
    public void findSimilarNotes(){
        int noteIndex1 = dataManagerInstance.createNewNote();
        NoteInfo newNote1 = dataManagerInstance.getNotes().get(noteIndex1);
        newNote1.setCourse(dataManagerInstance.getCourse("android_async"));
        newNote1.setTitle("Test new Title Note");
        newNote1.setText("Test new Text Note");

        int noteIndex2 = dataManagerInstance.createNewNote();
        NoteInfo newNote2 = dataManagerInstance.getNotes().get(noteIndex2);
        newNote2.setCourse(new CourseInfo("course_id_1", "Course Title", null));
        newNote2.setTitle("Test new Title Note");
        newNote2.setText("Test new Text Note");

        int indexFound1 = dataManagerInstance.findNote(newNote1);
        assertEquals(noteIndex1, indexFound1);

        int indexFound2 = dataManagerInstance.findNote(newNote2);
        assertEquals(noteIndex2, indexFound2);
    }

}