package com.cherish.mynoteapp.DAO;


import android.provider.SyncStateContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cherish.mynoteapp.entity.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface DataObjectAccess {
    @Query("SELECT * FROM  Note ORDER BY id DESC ")
    Flowable<List<Note>> getAllNote();

    @Insert
    Completable addNote(Note note);
//    void addNote(Note note);

    @Update
    Completable updateNote(Note note);
//    void updateNote(Note note);

    @Delete
    Completable deleteNote(Note note);
//    void deleteNote(Note note);



}
