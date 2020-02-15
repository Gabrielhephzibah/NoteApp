package com.cherish.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cherish.mynoteapp.DAO.DataBaseClient;
import com.cherish.mynoteapp.entity.Note;

import java.util.List;

public class NewNoteActivity extends AppCompatActivity {

    EditText heading,content ;
    Button button;

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
                onSave();

            }
        });

    }

    public  void onSave(){
        final String editContent =  content .getText().toString().trim();
        final String editHeading =  heading.getText().toString().trim();


        if (editHeading.isEmpty()){
            heading.setError("heading is required");
            heading.requestFocus();
            return;
        }else if (editContent.isEmpty()){
            content.setError("Content is required");
            content.requestFocus();
            return;
        }



        class SaveNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Note note = new Note();
                note.setContent(editContent);
                note.setHeading(editHeading);

                DataBaseClient.getInstance(getApplicationContext()).getNoteDataBase()
                        .dataObjectAccess()
                        .addNote(note);
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_LONG).show();
            }
        }

        SaveNote saveNote = new SaveNote();
        saveNote.execute();
    }
}
