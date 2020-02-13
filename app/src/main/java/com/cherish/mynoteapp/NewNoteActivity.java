package com.cherish.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cherish.mynoteapp.entity.NoteItem;

import java.util.List;

public class NewNoteActivity extends AppCompatActivity {



    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    EditText editText;
    Button button;
   private NoteAppViewModel noteAppViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        final NoteItemAdapter adapter = new NoteItemAdapter(this);
        editText = findViewById(R.id.edit_word);
        button = findViewById(R.id.button_save);
        noteAppViewModel = ViewModelProviders.of(this).get(NoteAppViewModel.class);

        noteAppViewModel.getNoteItem().observe(this, new Observer<List<NoteItem>>() {
            @Override
            public void onChanged(List<NoteItem> noteItems) {
               adapter.setWords(noteItems);
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(editText.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    String word = editText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY,word);
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }

        });


    }
}
