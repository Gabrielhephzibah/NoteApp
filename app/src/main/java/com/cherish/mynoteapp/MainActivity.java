package com.cherish.mynoteapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.cherish.mynoteapp.Fragment.NewNoteFragment;
import com.cherish.mynoteapp.Fragment.RecyclerViewFragment;
import com.cherish.mynoteapp.ViewModel.MainActivityViewModel;
import com.cherish.mynoteapp.entity.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity{

    private ViewModelProvider.AndroidViewModelFactory factory;
    FloatingActionButton fab;
   private MainActivityViewModel mainActivityViewModel;
     RecyclerView recyclerView;
    NoteAdapter myAdapter;
    ConstraintLayout constraintLayout;
    LinearLayout newNoteLayout,noteContentLayout,recyclerLayout,lineLayout;
     int position;
     Note item;
    SwipeToDelete swipeToDelete;
    Fragment newNote = new NewNoteFragment();
    Fragment recycler = new RecyclerViewFragment();

    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        constraintLayout = findViewById(R.id.constraintLayout);
        recyclerLayout = findViewById(R.id.recyclerLayout);
        lineLayout = findViewById(R.id.line);
        newNoteLayout = findViewById(R.id.newNoteLayout);
      noteContentLayout = findViewById(R.id.noteContentLayout);
        recyclerLayout =findViewById(R.id.recyclerLayout);

        fab = findViewById(R.id.fab);
        mainActivityViewModel = ViewModelProviders.of(this ).get(MainActivityViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newNoteLayout.getVisibility()==View.GONE){
                    newNoteLayout.setVisibility(View.VISIBLE);
                    recyclerLayout.setVisibility(View.GONE);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.newNoteLayout,newNote);
                    fragmentTransaction.commit();
                }else if (newNoteLayout.getVisibility()==View.VISIBLE){
                    newNoteLayout.setVisibility(View.GONE);
                    recyclerLayout.setVisibility(View.VISIBLE);
                }



            }
        });
        FragmentManager fragmentMa = getSupportFragmentManager();
        FragmentTransaction fragment =fragmentMa.beginTransaction();
        fragment.replace(R.id.recyclerLayout,recycler);
        fragment.commit();


    }



}
