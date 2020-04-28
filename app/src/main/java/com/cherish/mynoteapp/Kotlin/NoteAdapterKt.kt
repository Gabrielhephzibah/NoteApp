package com.cherish.mynoteapp.Kotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.R
import kotlinx.android.synthetic.main.note_item_layout.view.*

class NoteAdapterKt(context: FragmentActivity?, noteKt: ArrayList<NoteKt>) : RecyclerView.Adapter<NoteAdapterKt.NotektViewHolder>() {

     var context:FragmentActivity?
    lateinit var noteKt : ArrayList<NoteKt>
    init {
        this.context = context
        this.noteKt = noteKt
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotektViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.note_item_layout, parent, false)
        return NotektViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotektViewHolder, position: Int) {
        val notes  = noteKt[position]
        holder.heading?.text = notes.heading
        holder.content?.text = notes.content


    }

    override fun getItemCount(): Int {
        return noteKt.size
    }



    class NotektViewHolder(view:View) : RecyclerView.ViewHolder(view){
         val heading = view.headingnote
         val content = view.contentnote
    }

    fun deleteNoteKt(position: Int){
        val arrayList = ArrayList<NoteKt>(noteKt)
        noteKt.removeAt(position)
        notifyItemRemoved(position)
    }

    fun undoDelete(notes: NoteKt,position: Int){
        val arrayList = ArrayList<NoteKt>(noteKt)
        noteKt.add(position,notes)
        notifyItemInserted(position)

    }

    fun getData() :List<NoteKt>{
       return noteKt
    }



}