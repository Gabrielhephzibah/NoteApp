package com.cherish.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cherish.mynoteapp.DataBase.NoteAppDataBase;
import com.cherish.mynoteapp.entity.NoteItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private NoteItemAdapter  noteItemAdapter;
    private ArrayList<NoteItem> noteItems = new ArrayList<>() ;

    public void showDetails(View view){
        Intent intent = new Intent(getApplicationContext(), NoteContent.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        final NoteItemAdapter adapter = new NoteItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        NoteItemAdapter adapter1 = new NoteItemAdapter(MainActivity.this, noteItems);
//
//        recyclerView.setAdapter(adapter1);

    }

    private  static RoomDatabase.Callback roomDataBase = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriterExecutor
        }
    }


}
