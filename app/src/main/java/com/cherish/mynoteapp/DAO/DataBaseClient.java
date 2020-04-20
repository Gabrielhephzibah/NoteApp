package com.cherish.mynoteapp.DAO;

import android.content.Context;

import androidx.room.Room;

import com.cherish.mynoteapp.DataBase.NoteDataBase;

public class DataBaseClient {
    private Context context;

    private static DataBaseClient myInstance;

    private NoteDataBase noteDataBase;

    public DataBaseClient(Context context){
        this.context = context;

        noteDataBase = Room.databaseBuilder(context,NoteDataBase.class,"Note_app").build();
    }

    public static synchronized DataBaseClient getInstance(Context context){
        if (myInstance == null){
        myInstance = new DataBaseClient(context);
        }
        return myInstance;
    }

        public NoteDataBase getNoteDataBase(){
        return noteDataBase;
        }
}
