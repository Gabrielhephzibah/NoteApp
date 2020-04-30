package com.cherish.mynoteapp.Kotlin.KotlinFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cherish.mynoteapp.Kotlin.DatabaseKt.NoteDataBaseKt
import com.cherish.mynoteapp.Kotlin.Main2Activity
import com.cherish.mynoteapp.Kotlin.NoteAdapterKt
import com.cherish.mynoteapp.Kotlin.ViewModelKotlin.NoteViewModelKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.MainActivity
import com.cherish.mynoteapp.R
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.new_note_fragment_layout.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recycler_view_fragment.*

class NewNoteFragmentKt : Fragment() {

    private var noteViewModelKt: NoteViewModelKt? = null
    lateinit var editContent: String
    lateinit var editHeading: String
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteViewModelKt = ViewModelProviders.of(this).get(NoteViewModelKt::class.java)
        var dataBaseInstance = NoteDataBaseKt.getDatabaseInstance(requireContext())
        noteViewModelKt?.setInstanceOfDb(dataBaseInstance)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_note_fragment_layout, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_save.setOnClickListener {

            editHeading = heading.text.toString()
            editContent = content.text.toString()
            if (editHeading.isEmpty()) {
                heading.error = "Headingt cannot be empty"
                heading.requestFocus()
                return@setOnClickListener
            } else if (editContent.isEmpty()) {
                content.error = "Content cannot be empty"
                content.requestFocus()
                return@setOnClickListener
            }

            Log.i("edit heading", editHeading)
            Log.i("edit content", editContent)

            var noteKt: NoteKt = NoteKt(heading = editHeading, content = editContent, id = 0)


            Log.i("NOTESSSSS", noteKt.toString())
            noteViewModelKt?.addNewNote(noteKt)?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({
                        Log.i("Success", "Note Saved")
                        Toast.makeText(activity, "Note Saved", Toast.LENGTH_LONG).show()
                        startActivity(Intent(activity, Main2Activity::class.java))
                    }, {
                        Log.i("ERROR", "ERROR")
                        Toast.makeText(activity, " Error!!!  Cannot Save note", Toast.LENGTH_LONG).show()
                    })?.let {
                        compositeDisposable.add(it)
                    }


        }
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}