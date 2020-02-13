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
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataAccessObject;
import com.cherish.mynoteapp.DataBase.NoteAppDataBase;
import com.cherish.mynoteapp.entity.NoteItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.cherish.mynoteapp.DataBase.NoteAppDataBase.INSTANCE;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;

    public  static  final  int NEW_ACTIVITY_REQUEST_CODE = 1;
     RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private NoteItemAdapter  noteItemAdapter;
    Intent data;
    NoteAppViewModel noteAppViewModel;
//    private ArrayList<NoteItem> noteItems = new ArrayList<>() ;

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
        fab = findViewById(R.id.fab);
//        onResponse(NEW_ACTIVITY_REQUEST_CODE,RESULT_OK,data);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                startActivityForResult(intent, NEW_ACTIVITY_REQUEST_CODE);
            }
        });


    }

//    public static RoomDatabase.Callback roomDataBase = new RoomDatabase.Callback() {
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

    public  void onResponse(int requestCode, int result, Intent data){
       super.onActivityResult(requestCode, result,data);

       if (requestCode == NEW_ACTIVITY_REQUEST_CODE && result == RESULT_OK){
           String heading = data.getStringExtra(NewNoteActivity.EXTRA_REPLY);
           String content = data.getStringExtra(NewNoteActivity.EXTRA_REPLY);
           NoteItem noteItem = new NoteItem(heading,content);
           noteAppViewModel.insert(noteItem);

       }else {
           Toast.makeText(getApplicationContext(),"WOrd not saved because it is empty",Toast.LENGTH_LONG).show();
       }
    }


}
