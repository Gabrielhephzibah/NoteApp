package com.cherish.mynoteapp.DataBase;


import android.content.Context;
import android.provider.SyncStateContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.cherish.mynoteapp.DAO.DataObjectAccess;
import com.cherish.mynoteapp.entity.Note;

@Database(entities = Note.class,version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    public abstract DataObjectAccess dataObjectAccess();

}
