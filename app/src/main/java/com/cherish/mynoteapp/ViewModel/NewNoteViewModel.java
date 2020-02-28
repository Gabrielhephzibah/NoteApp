package com.cherish.mynoteapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;

import io.reactivex.Completable;

public class NewNoteViewModel extends AndroidViewModel {
    public NewNoteViewModel(@NonNull Application application) {
        super(application);
    }

    public Completable addNote(Note note){
        return  DataBaseClient.getInstance(getApplication().getApplicationContext())
                .getNoteDataBase()
                .dataObjectAccess()
                .addNote(note);
    }


}
