package com.cherish.mynoteapp.Kotlin.DAOKotlin

import androidx.room.*
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface DataObjectAccessKt{
    @Query("SELECT * FROM  NoteKt ORDER BY id DESC ")
    abstract fun getAllNoteKt(): Flowable<List<NoteKt>>

    @Insert
    fun addNoteKt(noteKt: NoteKt) : Completable

    @Update
    fun updateNoteKt(noteKt: NoteKt): Completable

    @Delete
    fun deleteNoteKt(noteKt: NoteKt) :Completable
}