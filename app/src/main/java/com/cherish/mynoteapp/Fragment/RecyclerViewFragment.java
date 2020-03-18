package com.cherish.mynoteapp.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cherish.mynoteapp.MainActivity;
import com.cherish.mynoteapp.NoteAdapter;
import com.cherish.mynoteapp.R;
import com.cherish.mynoteapp.SwipeToDelete;
import com.cherish.mynoteapp.ViewModel.MainActivityViewModel;
import com.cherish.mynoteapp.entity.Note;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class RecyclerViewFragment extends Fragment {
    private final CompositeDisposable disposables = new CompositeDisposable();
    MainActivityViewModel mainActivityViewModel;
    NoteAdapter myAdapter;
    RecyclerView recyclerView;
    SwipeToDelete swipeToDelete;
    int position;
    Note item;
    LinearLayout linearLayout;

    ConstraintLayout constraintLayout;

    public RecyclerViewFragment(){
        //leave
    }

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recycler_view_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        linearLayout =view.findViewById(R.id.linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        constraintLayout = view.findViewById(R.id.constraintLayout);
        disposables.add(
                mainActivityViewModel.getNote()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(notes -> {
                            Log.i("NOTES", notes.toString());
                            myAdapter = new NoteAdapter(getActivity(),notes);
                            recyclerView.setAdapter(myAdapter);
                        },throwable -> {
                            Log.i("ERROR","ERROR");
                            Toast.makeText(getActivity()," Error!!!  Cannot get Note", Toast.LENGTH_LONG).show();
                        }));

        deleteOnSwipe();
    }

    public  void deleteOnSwipe(){
        swipeToDelete = new SwipeToDelete(getActivity()){
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
                                        Snackbar snackbar = Snackbar.make(linearLayout,"Note Removed",Snackbar.LENGTH_LONG);
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
                                    Toast.makeText(getActivity()," Error!!!  Note Not Deleted", Toast.LENGTH_LONG).show();
                                }));
                myAdapter.deleteMyNote(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDelete);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

}
