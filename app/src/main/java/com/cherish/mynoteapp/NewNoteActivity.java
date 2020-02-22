package com.cherish.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NewNoteActivity extends AppCompatActivity {

    EditText heading,content ;
    Button button;
    private final CompositeDisposable disposables = new CompositeDisposable();
    Note note = new Note();
    String editContent;
    String editHeading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);


        heading = findViewById(R.id.heading);

        content = findViewById(R.id.content);

        button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContent =  content .getText().toString().trim();
                editHeading =  heading.getText().toString().trim();
                if (editHeading.isEmpty()){
                    heading.setError("heading is required");
                    heading.requestFocus();
                    return;
                }else if (editContent.isEmpty()){
                    content.setError("Content is required");
                    content.requestFocus();
                    return;
                }

                Log.i("edit heading", editHeading);
                Log.i("edit content",editContent);

                Note note = new Note();
                note.setHeading(editHeading);
                note.setContent(editContent);
                onSave(note);

            }
        });

    }

    public  void onSave(Note note){
        disposables.add(
                DataBaseClient.getInstance(getApplicationContext())
                        .getNoteDataBase()
                .dataObjectAccess()
                .addNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i("SUCCESS", "SUCESSS");
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_LONG).show();


                    }
                }, throwable -> {
                    Log.i("Error", "ERORRR");
                    Toast.makeText(getApplicationContext()," Error!!!  Note Not Saved", Toast.LENGTH_LONG).show();
                }));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
