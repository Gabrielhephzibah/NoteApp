package com.cherish.mynoteapp;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cherish.mynoteapp.Fragment.NoteContentFragment;
import com.cherish.mynoteapp.entity.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public Context context;
    private List<Note> note;
    public NoteAdapter(Context context, List<Note>note){
        this.context = context;
        this.note = note;

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item_layout, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.NoteViewHolder holder, int position) {
            Note notes = note.get(position);
            holder.content.setText(notes.getContent());
            holder.heading.setText(notes.getHeading());

    }

    @Override
    public int getItemCount() {
        return note.size();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView heading, content;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.headingnote);
            content = itemView.findViewById(R.id.contentnote);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Note noteItem = note.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable("note", noteItem);
            NoteContentFragment noteFragment = new NoteContentFragment();
            noteFragment.setArguments(bundle);
            MainActivity activity = (MainActivity) v.getContext();
            if (activity.noteContentLayout.getVisibility()==View.GONE){
                activity.noteContentLayout.setVisibility(View.VISIBLE);
                activity.lineLayout.setVisibility(View.GONE);
                activity.recyclerLayout.setVisibility(View.GONE);
            }

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.noteContentLayout,noteFragment).addToBackStack(null).commit();
        }
    }

    public  void  deleteMyNote(int position){
        note.remove(position);
        notifyItemRemoved(position);
    }

    public void undoDelete(Note notes,  int position){
        note.add(position, notes);
        notifyItemInserted(position);
    }


    public List<Note> getData() {
        return note;
    }

}
