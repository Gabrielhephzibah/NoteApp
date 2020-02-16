package com.cherish.mynoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.cherish.mynoteapp.entity.Note;

public class NoteContent extends AppCompatActivity {
    Button edit;
    EditText editHeading, editContent;
    TextView headingContent;
    TextView fullContent;
    Note note;
    Button delete;
    AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
        headingContent = findViewById(R.id.heading);
        fullContent = findViewById(R.id.content);

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
                        editNote(note);

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

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(NoteContent.this);
                dialog.setTitle("Delete Note");
                dialog.setMessage("Are you sure you want to delete ");
                dialog.setCancelable(false);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote(note);
                    }
                });

                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

    }

    public void loadNote(){
        headingContent.setText(note.getHeading());
        fullContent.setText(note.getContent());
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
                Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        deleteMyNote deleteTask = new deleteMyNote();
        deleteTask.execute();
    }

    private void editNote(final Note notess){
        final String editTitle = editHeading.getText().toString().trim();
        final String editBody = editContent.getText().toString().trim();


        class EditMyNote extends AsyncTask<Void,Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                notess.setHeading(editTitle);
                notess.setContent(editBody);
                DataBaseClient.getInstance(getApplicationContext())
                        .getNoteDataBase()
                        .dataObjectAccess()
                        .updateNote(notess);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(),"Note Updated", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }

        EditMyNote editMyNote = new EditMyNote();
        editMyNote.execute();

    }
}
