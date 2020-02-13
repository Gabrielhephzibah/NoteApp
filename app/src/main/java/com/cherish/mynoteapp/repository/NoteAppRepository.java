package com.cherish.mynoteapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cherish.mynoteapp.DAO.DataAccessObject;
import com.cherish.mynoteapp.DataBase.NoteAppDataBase;
import com.cherish.mynoteapp.entity.NoteItem;

import java.util.List;

public class NoteAppRepository {

    private DataAccessObject accessObject;

    private LiveData<List<NoteItem>> noteItem;

   public NoteAppRepository(Application application){
        NoteAppDataBase dataBase = NoteAppDataBase.getDatabase(application);
        accessObject = dataBase.getdatebase();
        noteItem = accessObject.getItemAlphabetically();
    }
   public LiveData<List<NoteItem>> getAllNoteItem(){
        return noteItem;
    }

   public void insert(final NoteItem noteItem){
       NoteAppDataBase.databaseWriterExecutor.execute(new Runnable() {
           @Override
           public void run() {
               accessObject.insert(noteItem);

           }
       });
    }
}
