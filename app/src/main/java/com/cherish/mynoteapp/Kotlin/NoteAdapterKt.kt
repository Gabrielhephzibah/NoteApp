package com.cherish.mynoteapp.Kotlin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.cherish.mynoteapp.Fragment.NoteContentFragment
import com.cherish.mynoteapp.Kotlin.KotlinFragment.NoteContentFragmentKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.R
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.note_item_layout.view.*
import kotlinx.android.synthetic.main.recycler_view_fragment.*

class NoteAdapterKt(context: FragmentActivity?, noteKt: MutableList<NoteKt>) : RecyclerView.Adapter<NoteAdapterKt.NotektViewHolder>() {

    var context: FragmentActivity?
    lateinit var noteKt: MutableList<NoteKt>

    init {
        this.context = context
        this.noteKt = noteKt
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotektViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.note_item_layout, parent, false)
        return NotektViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotektViewHolder, position: Int) {
        val notes = noteKt[position]
        holder.heading?.text = notes.heading
        holder.content?.text = notes.content


    }

    override fun getItemCount(): Int {
        return noteKt.size
    }


    var onItemClick: ((pos: Int, view: View) -> Unit)? = null

    private var itemAction: ((NoteKt) -> Unit)? = null

//    fun setItemAction(action: (Item) -> Unit) {
//        this.itemAction = action
//    }


    inner class NotektViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val heading = view.headingnote
        val content = view.contentnote

        init {
            itemView.setOnClickListener(this)
//            var noteKt :NoteKt = .get(adapterPosition)
//          Log.i("Notes", noteKt.toString())
        }

        override fun onClick(v: View?) {
            Log.i("CLICKED", "CLICKED")
            val noteitem = noteKt.get(adapterPosition)
            Log.i("Notes", noteitem.toString())
            val bundle = Bundle()
            bundle.putSerializable("noteItem", noteitem)
            val noteContentFragmentKt = NoteContentFragmentKt()
            noteContentFragmentKt.arguments = bundle
            var activity: Main2Activity = v!!.context as Main2Activity
            if (activity.noteContentLayout.visibility == View.GONE) {
                activity.noteContentLayout.visibility = View.VISIBLE
                activity.line.visibility = View.GONE
                activity.recyclerLayout.visibility = View.GONE
            }
            activity.supportFragmentManager.beginTransaction().replace(R.id.noteContentLayout, noteContentFragmentKt).addToBackStack(null).commit()


        }


    }

    fun deleteNoteKt(position: Int) {
        val arrayList = ArrayList<NoteKt>(noteKt)
        noteKt.removeAt(position)
        notifyItemRemoved(position)
    }

    fun undoDelete(notes: NoteKt, position: Int) {
        val arrayList = ArrayList<NoteKt>(noteKt)
        noteKt.add(position, notes)
        notifyItemInserted(position)

    }

    fun getData(): List<NoteKt> {
        return noteKt
    }


}