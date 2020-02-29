package com.cherish.mynoteapp.ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

    }


  public Flowable<List<Note>>getNote(){
        return DataBaseClient.getInstance(getApplication().getApplicationContext())
                .getNoteDataBase()
                .dataObjectAccess()
                .getAllNote();

   }

   public Completable deleteNote(Note note){
        return DataBaseClient.getInstance(getApplication().getApplicationContext())
                .getNoteDataBase()
                .dataObjectAccess()
                .deleteNote(note);
   }





}
