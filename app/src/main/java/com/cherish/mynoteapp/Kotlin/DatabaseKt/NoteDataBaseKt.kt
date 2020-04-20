package com.cherish.mynoteapp.Kotlin.DatabaseKt

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cherish.mynoteapp.Kotlin.DAOKotlin.DataObjectAccessKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt

@Database(entities = [NoteKt::class], version = 1)
 abstract class NoteDataBaseKt : RoomDatabase() {

 abstract fun  dataObjectAccessKt() : DataObjectAccessKt

}