package com.ilyo.shareideas.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by iLyas Dev on 08/02/2021
 *
 * Dao Is where you specify your queries
 */
@Dao
public interface NoteDao {

    // Using LiveData to Load data from the background thread
    // + automatically notify the LiveData when database updated
    @Query("SELECT * FROM "+Constants.NOTE_TABLE_NAME+" ORDER BY title ASC")
    LiveData<List<Note>> getAllNotes();


    @Query("DELETE FROM " +Constants.NOTE_TABLE_NAME)
    void deleteAll();

    @Insert
    void insert(Note note);

    @Update
    void update(Note repos);

    @Delete
    void delete(Note note);

}
