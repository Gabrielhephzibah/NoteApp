package com.cherish.mynoteapp.Kotlin.DatabaseKt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cherish.mynoteapp.Kotlin.DAOKotlin.DataObjectAccessKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt

@Database(entities = [NoteKt::class], version = 1)
 abstract class NoteDataBaseKt : RoomDatabase() {

 abstract fun  dataObjectAccessKt() : DataObjectAccessKt

 companion object {
  @Volatile private var myInstance: NoteDataBaseKt? = null
  fun getDatabaseInstance(mContext: Context): NoteDataBaseKt = myInstance ?: synchronized(this) {
           myInstance ?: buildDatabaseInstance(mContext).also {
            myInstance = it
           }
          }

  private fun buildDatabaseInstance(mContext: Context) = Room.databaseBuilder(mContext, NoteDataBaseKt::class.java, "Note_app_db").build()

 }






}