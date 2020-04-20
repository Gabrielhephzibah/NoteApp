package com.cherish.mynoteapp.Kotlin.ViewModelKotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cherish.mynoteapp.DAO.DataBaseClient
import com.cherish.mynoteapp.Kotlin.DAOKotlin.DataBaseClientKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.entity.Note
import io.reactivex.Completable
import io.reactivex.Flowable

class NoteViewModelKt(application: Application) : AndroidViewModel(application) {
    val dataBaseClientKt  = DataBaseClientKt(getApplication<Application>().applicationContext)
    fun getNote(): Flowable<List<NoteKt>> {
        return dataBaseClientKt.getInstance(getApplication<Application>().applicationContext)
                .getNoteDataBasekt().dataObjectAccessKt()
                .getAllNoteKt()

    }

    fun deleteNote(noteKt: NoteKt): Completable {
        return dataBaseClientKt.getInstance(getApplication<Application>().applicationContext)
                .getNoteDataBasekt()
                .dataObjectAccessKt()
                .deleteNoteKt(noteKt)
    }

    fun addNewNote(noteKt: NoteKt):Completable{
        return  dataBaseClientKt.getInstance(getApplication<Application>().applicationContext)
                .getNoteDataBasekt()
                .dataObjectAccessKt()
                .addNoteKt(noteKt)
    }

    fun updateNote(noteKt: NoteKt): Completable{
        return  dataBaseClientKt.getInstance(getApplication<Application>().applicationContext)
                .getNoteDataBasekt()
                .dataObjectAccessKt()
                .updateNoteKt(noteKt)
    }





}