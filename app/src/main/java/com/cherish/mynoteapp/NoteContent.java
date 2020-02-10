package com.cherish.mynoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class NoteContent extends AppCompatActivity {
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(NoteContent.this);
                LayoutInflater inflater = NoteContent.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.content_dialog, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                Button save = dialogView.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });


    }
}
