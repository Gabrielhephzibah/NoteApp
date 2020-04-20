package com.cherish.mynoteapp.Kotlin.DAOKotlin

import android.content.Context
import androidx.room.Room
import com.cherish.mynoteapp.Kotlin.DatabaseKt.NoteDataBaseKt

class DataBaseClientKt(_context: Context) {
  private lateinit var  context : Context

  private lateinit var myInstance : DataBaseClientKt

  private lateinit var noteDataBaseKt :NoteDataBaseKt

   init {
       this.context = _context;
      noteDataBaseKt = Room.databaseBuilder(context,NoteDataBaseKt::class.java,"My_Note_App").build();

   }


   @Synchronized
   fun getInstance(context: Context):DataBaseClientKt{
      if (myInstance == null){
         myInstance = DataBaseClientKt(context);
      }
      return myInstance;
   }

   fun  getNoteDataBasekt() : NoteDataBaseKt{
      return  noteDataBaseKt;
   }

}