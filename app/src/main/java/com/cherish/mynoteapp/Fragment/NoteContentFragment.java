package com.cherish.mynoteapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cherish.mynoteapp.MainActivity;
import com.cherish.mynoteapp.NoteAdapter;
import com.cherish.mynoteapp.NoteContent;
import com.cherish.mynoteapp.R;
import com.cherish.mynoteapp.ViewModel.NoteContentViewModel;
import com.cherish.mynoteapp.entity.Note;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NoteContentFragment extends Fragment {

    FragmentInterface fragmentInterface;
    View view;
    Button edit;
    EditText editHeading, editContent;
    TextView headingContent;
    TextView fullContent;
    Note note;
    String editTitle;
    String editBody;
    AlertDialog alert;
    LinearLayout noteContent;
    NoteContentViewModel noteContentViewModel;
    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.note_content_fragment_layout, container, false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteContentViewModel = ViewModelProviders.of(this).get(NoteContentViewModel.class);

        noteContent = view.findViewById(R.id.noteContent);
        headingContent = view.findViewById(R.id.heading);
        fullContent = view.findViewById(R.id.content);
        edit = view.findViewById(R.id.edit);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            note = (Note) bundle.getSerializable("note");
            Log.i("NOTEEEEEE", String.valueOf(note));
            Log.i("Heading",note.getContent());
            Log.i("Content",note.getHeading());
            loadNote();
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater =getActivity().getLayoutInflater();
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
                                                Toast.makeText(getActivity(),"Note Updated", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        },throwable -> {
                                            Log.i("Error", "EDIT ERROR");
                                            Toast.makeText(getActivity()," Error!!!  Note Not Updated", Toast.LENGTH_LONG).show();
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
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}



