package com.cherish.mynoteapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.cherish.mynoteapp.MainActivity;
import com.cherish.mynoteapp.R;
import com.cherish.mynoteapp.ViewModel.NewNoteViewModel;
import com.cherish.mynoteapp.entity.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NewNoteFragment extends Fragment {
    EditText heading,content ;
    Button button;
    NewNoteViewModel newNoteViewModel;
    private ViewModelProvider.AndroidViewModelFactory factory;
    private final CompositeDisposable disposables = new CompositeDisposable();
    Note note = new Note();
    String editContent;
    String editHeading;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.new_note_fragment_layout,container,false);
        return view;




    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newNoteViewModel = ViewModelProviders.of(this ).get(NewNoteViewModel.class);

        heading = view.findViewById(R.id.heading);

        content = view.findViewById(R.id.content);

        button = view.findViewById(R.id.button_save);
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
                Log.i("NOTESSSSS", note.toString());
                disposables.add(newNoteViewModel
                        .addNote(note)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.i("SUCCESS", "SUCESSS");
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                Toast.makeText(getActivity(),"Note Saved",Toast.LENGTH_LONG).show();

                            }
                        },throwable -> {
                            Log.i("Error", "ERORRR");
                            Toast.makeText(getActivity()," Error!!!  Note Not Saved", Toast.LENGTH_LONG).show();

                        }));

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
