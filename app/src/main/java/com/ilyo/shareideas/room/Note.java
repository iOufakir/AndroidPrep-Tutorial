package com.ilyo.shareideas.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by iLyas Dev on 08/02/2021
 */
@Entity(tableName = Constants.NOTE_TABLE_NAME)
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private String title;

    @ColumnInfo(name = "note_content")
    private String content;


    public Note(@NonNull String title, @NonNull String content) {
        this.title = title;
        this.content = content;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
