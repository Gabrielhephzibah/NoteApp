package com.cherish.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity   {

    FloatingActionButton fab;
     RecyclerView recyclerView;
    NoteAdapter myAdapter;
    ConstraintLayout constraintLayout;
     int position;
     Note item;
    SwipeToDelete swipeToDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        constraintLayout = findViewById(R.id.constraintLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                startActivity(intent);
            }
        });

        getNote();
        deleteOnSwipe();



    }



    private void getNote(){
        class GetNote extends AsyncTask <Void, Void, List< Note >>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                   List<Note> notes = DataBaseClient
                        .getInstance(getApplicationContext())
                        .getNoteDataBase()
                        .dataObjectAccess()
                        .getAllNote();
                return notes;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                 myAdapter = new NoteAdapter(MainActivity.this,notes);
                recyclerView.setAdapter(myAdapter);


            }
        }

        GetNote getNote = new GetNote();
        getNote.execute();

    }

    public  void deleteOnSwipe(){
        swipeToDelete = new SwipeToDelete(this){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);

                position = viewHolder.getAdapterPosition();
                 item = myAdapter.getData().get(position);
                deleteNote(item);
                myAdapter.deleteMyNote(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    private  void deleteNote(final Note notes){
        class deleteMyNote extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DataBaseClient.getInstance(getApplicationContext())
                        .getNoteDataBase()
                        .dataObjectAccess()
                        .deleteNote(notes);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Snackbar snackbar = Snackbar.make(constraintLayout,"Note Removed",Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAdapter.undoDelete(item,position);
                        recyclerView.scrollToPosition(position);
                    }

                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();

            }

        }

        deleteMyNote deleteTask = new deleteMyNote();
        deleteTask.execute();
    }



}
