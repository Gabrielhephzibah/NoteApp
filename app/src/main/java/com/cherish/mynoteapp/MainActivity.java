package com.cherish.mynoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.ViewModel.MainActivityViewModel;
import com.cherish.mynoteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity   {


    private ViewModelProvider.AndroidViewModelFactory factory;
    FloatingActionButton fab;
   private MainActivityViewModel mainActivityViewModel;
     RecyclerView recyclerView;
    NoteAdapter myAdapter;
    ConstraintLayout constraintLayout;
     int position;
     Note item;
    SwipeToDelete swipeToDelete;
    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        constraintLayout = findViewById(R.id.constraintLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fab);
        mainActivityViewModel = ViewModelProviders.of(this ).get(MainActivityViewModel.class);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewNoteActivity.class);
                startActivity(intent);
            }
        });
        disposables.add(
            mainActivityViewModel.getNote()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(notes -> {
                        Log.i("NOTES", notes.toString());
                        myAdapter = new NoteAdapter(MainActivity.this,notes);
                        recyclerView.setAdapter(myAdapter);
                    },throwable -> {
                        Log.i("ERROR","ERROR");
                        Toast.makeText(getApplicationContext()," Error!!!  Cannot get Note", Toast.LENGTH_LONG).show();
                    }));

        deleteOnSwipe();



    }


    public  void deleteOnSwipe(){
        swipeToDelete = new SwipeToDelete(this){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                super.onSwiped(viewHolder, direction);

                position = viewHolder.getAdapterPosition();
                 item = myAdapter.getData().get(position);

                disposables.add(mainActivityViewModel.deleteNote(item)
                .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        Log.i("SUCCESS", "DELETE SUCCESSFUL");
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
                                },throwable -> {
                                    Log.i("Error", "ERR0R");
                                    Toast.makeText(getApplicationContext()," Error!!!  Note Not Deleted", Toast.LENGTH_LONG).show();
                                }));
                myAdapter.deleteMyNote(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
