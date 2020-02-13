package com.cherish.mynoteapp.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cherish.mynoteapp.entity.NoteItem;

import java.util.List;


@Dao
public interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteItem noteItem);

    @Query("DELETE FROM note_table")
    void  delete();

    @Query("SELECT * from note_table ORDER BY heading  ASC")
    LiveData<List<NoteItem>> getItemAlphabetically();


//    @Query("SELECT * FROM note_table")
//    LiveData<List<NoteItem>>getallNoteItem();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insert(List<NoteItem>noteItems);




}
