package com.cherish.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;


     RecyclerView recyclerView;

    public void showDetails(View view){
        Intent intent = new Intent(getApplicationContext(), NoteContent.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
    }
    private void getNote(){
        class GetNote extends AsyncTask <Void, Void, List< Note >>{

            @Override
            protected List<Note> doInBackground(Void... voids) {
                List<Note>notes = DataBaseClient
                        .getInstance(getApplicationContext())
                        .getNoteDataBase()
                        .dataObjectAccess()
                        .getAllNote();
                return notes;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                NoteAdapter myAdapter = new NoteAdapter(MainActivity.this,notes);
                recyclerView.setAdapter(myAdapter);


            }
        }

        GetNote getNote = new GetNote();
        getNote.execute();

    }





}
