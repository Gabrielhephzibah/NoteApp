package com.cherish.mynoteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cherish.mynoteapp.entity.NoteItem;

import java.util.List;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteViewHolder> {

    LayoutInflater layoutInflater;
    private List<NoteItem> noteItems;
    Context mcontext;

//    public NoteItemAdapter(Context context, List<NoteItem>noteItems){
//        this.mcontext = context;
//        this.noteItems = noteItems;
//    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView heading;
        TextView content;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            content = itemView.findViewById(R.id.content);



        }
    }

    public NoteItemAdapter(Context context){
         LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.note_item_layout, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (noteItems!=null){
            NoteItem note = noteItems.get(position);
            holder.heading.setText(note.getHeading());

            holder.content.setText(note.getContent());
        }else {
            holder.heading.setText("no heading");
            holder.content.setText("no content");
        }

    }

    void setWords(List<NoteItem> noteItem){
        noteItems = noteItem;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if (noteItems!= null)
            return noteItems.size();
        else return 0;


    }

}
