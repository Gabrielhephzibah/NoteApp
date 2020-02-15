package com.cherish.mynoteapp.DAO;


import android.provider.SyncStateContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cherish.mynoteapp.entity.Note;

import java.util.List;

@Dao
public interface DataObjectAccess {
    @Query("SELECT * FROM  Note ")
    List<Note>getAllNote();

    @Insert
    void addNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Delete
    void deletebyID(Note...note);


}
