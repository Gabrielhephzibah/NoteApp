package com.cherish.mynoteapp.DataBase;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cherish.mynoteapp.DAO.DataAccessObject;
import com.cherish.mynoteapp.entity.NoteItem;

import java.lang.annotation.Annotation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



@Database(entities = NoteItem.class,version = 2,exportSchema = false)

public abstract class NoteAppDataBase extends RoomDatabase {

    public abstract DataAccessObject getdatebase();

    public static volatile NoteAppDataBase INSTANCE;

    private static final  int NUMBER_OF_THREAD = 4;

   public static  final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

  public   static  NoteAppDataBase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (NoteAppDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),NoteAppDataBase.class,"noteApp_database")
                           .addCallback(roomDataBase)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private  static RoomDatabase.Callback roomDataBase = new RoomDatabase.Callback(){
      @Override
        public  void onOpen(@NonNull SupportSQLiteDatabase db){
          super.onOpen(db);

          databaseWriterExecutor.execute(new Runnable() {
              @Override
              public void run() {

                  DataAccessObject object = INSTANCE.getdatebase();
                  object.delete();

                  NoteItem item = new NoteItem("MY PRACTICE","My name is hephzibah");
                  object.insert(item);

                  item = new NoteItem("MY TEST", "MY surname is Gabriel");
                  object.insert(item);

              }
          });
      }
    };


//
//    private static RoomDatabase.Callback roomDataBase = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//
//            NoteAppDataBase.databaseWriterExecutor.execute(new Runnable() {
//
//
//                @Override
//                public void run() {
//                    DataAccessObject data = INSTANCE.getdatebase();
//
//                    NoteItem item = new NoteItem("@string/heading", "Here's a twist on the traditional product review post. Instead of just reviewing a product, we're going to do a product.......................");
//                    data.insert(item);
//                    item = new NoteItem("@string/heading", "Here's a twist on the traditional product review post. Instead of just reviewing a product, we're going to do a product.......................");
//                    data.insert(item);
//
//                }
//            });
//        }
//    };

}
