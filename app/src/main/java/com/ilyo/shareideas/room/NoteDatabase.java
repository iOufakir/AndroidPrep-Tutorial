package com.ilyo.shareideas.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iLyas Dev on 08/02/2021
 *
 * Version number is changed to update the database structure
 */
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDB = null;

    public abstract NoteDao getNoteDao();

    // synchronized for Thread safety
    public static synchronized NoteDatabase getInstance(final Context context) {
        if (noteDB == null) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static NoteDatabase buildDatabaseInstance(final Context context) {
        return Room.databaseBuilder(context,
                NoteDatabase.class,
                Constants.DB_NAME)
                // Migration strategy ******
                // Wipes all the data and rebuilds instead of migrating (DEV)
                .fallbackToDestructiveMigration()
                // for DEV : populate database everytime
                .addCallback(sRoomDatabaseCallback)
                .build();
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDataBase(noteDB).execute();
        }
    };


    private static class PopulateDataBase extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;
        private List<Note> listNotes = new ArrayList<>();

        public PopulateDataBase(final NoteDatabase noteDatabase) {
            this.noteDao = noteDatabase.getNoteDao();
            this.listNotes.add(new Note("Note 1", "Note Content 1"));
            this.listNotes.add(new Note("Note 2", "Note Content 2"));
            this.listNotes.add(new Note("Note 3", "Note Content 3"));
            this.listNotes.add(new Note("Note 4", "Note Content 4"));
            this.listNotes.add(new Note("Note 5", "Note Content 5"));
        }


        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            noteDao.deleteAll();

            // Then insert
            listNotes.stream().forEach(noteDao::insert);

            return null;
        }
    }

}
