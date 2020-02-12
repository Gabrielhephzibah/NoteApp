package com.cherish.mynoteapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cherish.mynoteapp.entity.NoteItem;
import com.cherish.mynoteapp.repository.NoteAppRepository;

import java.util.List;

public class NoteAppViewModel extends AndroidViewModel {
    private NoteAppRepository noteAppRepository;

    private LiveData<List<NoteItem>> noteItem;


    public NoteAppViewModel(@NonNull Application application) {
        super(application);

        noteAppRepository = new NoteAppRepository(application);

        noteItem = noteAppRepository.getAllNoteItem();
    }

    LiveData<List<NoteItem>>  getNoteItem(){
        return noteItem;
    }

    public void insert(NoteItem noteItem){noteAppRepository.insert(noteItem);}


}
