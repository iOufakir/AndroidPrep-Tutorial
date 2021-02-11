package com.ilyo.shareideas;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

/**
 * Created by iLyas Dev on 10/02/2021
 */
public class NoteActivityViewModel extends ViewModel{

    public static final String ORIGINAL_NOTE_COURSE_ID = "com.ilyo.shareidea.ORIGINAL_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.ilyo.shareidea.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.ilyo.shareidea.ORIGINAL_NOTE_TEXT";

    private String originalNoteCourseId;
    private String originalNoteTitle;
    private String originalNoteText;
    private boolean newCreated = true;

    public String getOriginalNoteCourseId() {
        return originalNoteCourseId;
    }

    public void setOriginalNoteCourseId(String originalNoteCourseId) {
        this.originalNoteCourseId = originalNoteCourseId;
    }

    public String getOriginalNoteTitle() {
        return originalNoteTitle;
    }

    public void setOriginalNoteTitle(String originalNoteTitle) {
        this.originalNoteTitle = originalNoteTitle;
    }

    public String getOriginalNoteText() {
        return originalNoteText;
    }

    public void setOriginalNoteText(String originalNoteText) {
        this.originalNoteText = originalNoteText;
    }

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_TITLE, originalNoteTitle);
        outState.putString(ORIGINAL_NOTE_TEXT, originalNoteText);
        outState.putString(ORIGINAL_NOTE_COURSE_ID, originalNoteCourseId);
    }

    public void restoreState(Bundle outState) {
        originalNoteTitle = outState.getString(ORIGINAL_NOTE_TITLE);
        originalNoteText = outState.getString(ORIGINAL_NOTE_TEXT);
        originalNoteCourseId = outState.getString(ORIGINAL_NOTE_COURSE_ID);
    }

    public boolean isNewCreated() {
        return newCreated;
    }

    public void setNewCreated(boolean newCreated) {
        this.newCreated = newCreated;
    }
}
