package com.cherish.mynoteapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;

import io.reactivex.Completable;

public class NoteContentViewModel extends AndroidViewModel {
    public NoteContentViewModel(@NonNull Application application) {
        super(application);
    }

    public Completable updateNote(Note note){
        return DataBaseClient.getInstance(getApplication().getApplicationContext())
                .getNoteDataBase()
                .dataObjectAccess()
                .updateNote(note);
    }
}
