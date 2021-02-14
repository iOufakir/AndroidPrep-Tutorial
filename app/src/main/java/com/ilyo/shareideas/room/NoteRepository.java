package com.ilyo.shareideas.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by iLyas Dev on 13/02/2021
 *
 * It provides a clean API to the rest of the app for app data
 */
public class NoteRepository {

    private static final String TAG = NoteRepository.class.getName();

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;


    public NoteRepository(final Context context) {
        NoteDatabase db = NoteDatabase.getInstance(context);
        this.noteDao = db.getNoteDao();
        this.allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /*  Use an AsyncTask to call insert() on a non-UI thread, or your app will crash
     * Room ensures that you don't do any long-running operations on the main thread
     */
    public void insertNote(final Note note) {
        Log.d(TAG, "Call execute method - Thread " + Thread.currentThread().getId());
        new InsertAsyncTask(noteDao).execute(note);
    }


    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        InsertAsyncTask(final NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            Log.d(TAG, "Call doInBackground method - Thread " + Thread.currentThread().getId());
            noteDao.insert(notes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "Call onPostExecute method - Thread " + Thread.currentThread().getId());

        }
    }

}
