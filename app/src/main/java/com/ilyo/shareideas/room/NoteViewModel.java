package com.ilyo.shareideas.room;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by iLyas Dev on 13/02/2021
 *
 * ViewModel is responsible for holding and processing all the data needed for the UI.
 * ViewModel holds your app's UI data in a way that survives configuration changes.
 * It also help us cache our data when we rotate the device for example
 */
public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> listNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.noteRepository = new NoteRepository(application);
        this.listNotes = this.noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> getListNotes() {
        return listNotes;
    }

    public void insert(final Note note) {
            noteRepository.insertNote(note);
    }
}
