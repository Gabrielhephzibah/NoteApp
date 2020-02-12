package com.cherish.mynoteapp.DataBase;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cherish.mynoteapp.DAO.DataAccessObject;
import com.cherish.mynoteapp.entity.NoteItem;

import java.lang.annotation.Annotation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = NoteItem.class,version = 1,exportSchema = false)

public abstract class NoteAppDataBase extends RoomDatabase {



    public abstract DataAccessObject dataAccessObject();

    private static volatile NoteAppDataBase INSTANCE;

    private static final  int NUMBER_OF_THREAD = 4;

   public static  final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

  public   static  NoteAppDataBase getDatebase(final Context context){
        if (INSTANCE == null){
            synchronized (NoteAppDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),NoteAppDataBase.class,"noteApp_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
