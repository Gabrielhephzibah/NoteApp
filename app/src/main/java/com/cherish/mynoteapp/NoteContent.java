package com.cherish.mynoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.ViewModel.NoteContentViewModel;
import com.cherish.mynoteapp.entity.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NoteContent extends AppCompatActivity {
    Button edit;
    EditText editHeading, editContent;
    TextView headingContent;
    TextView fullContent;
    Note note;
    private ViewModelProvider.AndroidViewModelFactory factory;
    private NoteContentViewModel noteContentViewModel;
    AlertDialog alert;
    String editTitle;
    String editBody;
    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
        headingContent = findViewById(R.id.heading);
        fullContent = findViewById(R.id.content);
        noteContentViewModel = ViewModelProviders.of(this).get(NoteContentViewModel.class);

        note = (Note) getIntent().getSerializableExtra("note");
        Log.i("Note", note.getContent());
        Log.i("Notesss", note.getHeading());
        loadNote();

        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(NoteContent.this);
                LayoutInflater inflater = NoteContent.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.content_dialog, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                editHeading= dialogView.findViewById(R.id.editHeading);
                editContent = dialogView.findViewById(R.id.editContent);
                editHeading.setText(note.getHeading());
                editContent.setText(note.getContent());
                Button save = dialogView.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         editTitle = editHeading.getText().toString().trim();
                         editBody = editContent.getText().toString().trim();

                         note.setHeading(editTitle);
                         note.setContent(editBody);

                       disposables.add(
                               noteContentViewModel.updateNote(note)
                               .subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(new Action() {
                                   @Override
                                   public void run() throws Exception {
                                       Log.i("SUCCESS", " EDIT SUCCESSFUL");
                                        Toast.makeText(getApplicationContext(),"Note Updated", Toast.LENGTH_LONG).show();                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                   }
                               },throwable -> {
                                   Log.i("Error", "EDIT ERROR");
                                   Toast.makeText(getApplicationContext()," Error!!!  Note Not Updated", Toast.LENGTH_LONG).show();
                               })
                       );

                    }
                });

                Button back = dialogView.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();

                    }
                });
                alert = dialog.create();
                alert.show();
            }
        });


    }

    public void loadNote(){
        headingContent.setText(note.getHeading());
        fullContent.setText(note.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
