package com.cherish.mynoteapp.Kotlin.DAOKotlin

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.cherish.mynoteapp.DataBase.NoteDataBase
import com.cherish.mynoteapp.Kotlin.DatabaseKt.NoteDataBaseKt

class DataBaseClientKt(private val context: Context) {

     var noteDataBaseKt: NoteDataBaseKt

    init {

        noteDataBaseKt = Room.databaseBuilder(context, NoteDataBaseKt::class.java, "Note_app").build()
    }

    companion object {
        @Volatile private var myInstance : DataBaseClientKt? = null

        @Synchronized
        fun getInstance(context: Context): DataBaseClientKt {
            if (myInstance == null) {
                myInstance = DataBaseClientKt(context)
            }
            return myInstance as DataBaseClientKt
        }


    }


}
