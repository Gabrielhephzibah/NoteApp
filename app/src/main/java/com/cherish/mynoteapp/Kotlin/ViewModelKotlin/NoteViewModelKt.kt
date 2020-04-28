package com.cherish.mynoteapp.Kotlin.ViewModelKotlin

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.cherish.mynoteapp.DAO.DataBaseClient
import com.cherish.mynoteapp.Kotlin.DAOKotlin.DataBaseClientKt
import com.cherish.mynoteapp.Kotlin.DatabaseKt.NoteDataBaseKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.entity.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoteViewModelKt(application: Application) : AndroidViewModel(application) {

    private var myInstance: NoteDataBaseKt ?= null

    protected val compositeDisposable = CompositeDisposable()

    var notes = MutableLiveData<List<NoteKt>>()

    fun setInstanceOfDb(myInstance: NoteDataBaseKt) {
        this.myInstance = myInstance
    }


    fun addNewNote(noteData: NoteKt) :Completable?{
       return myInstance?.dataObjectAccessKt()?.addNoteKt(noteData)

    }


    fun getAllNote(): Flowable<List<NoteKt>>?{
                return myInstance?.dataObjectAccessKt()?.getAllNoteKt()
            }


    fun deleteNote(note : NoteKt) : Completable?{
        return  myInstance?.dataObjectAccessKt()?.deleteNoteKt(note)
    }


    }







