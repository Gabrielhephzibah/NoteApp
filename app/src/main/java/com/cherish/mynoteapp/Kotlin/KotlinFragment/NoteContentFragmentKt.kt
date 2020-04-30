package com.cherish.mynoteapp.Kotlin.KotlinFragment

import android.app.AlertDialog
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
import androidx.lifecycle.ViewModelProviders
import com.cherish.mynoteapp.Kotlin.DatabaseKt.NoteDataBaseKt
import com.cherish.mynoteapp.Kotlin.Main2Activity
import com.cherish.mynoteapp.Kotlin.ViewModelKotlin.NoteViewModelKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.MainActivity
import com.cherish.mynoteapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_dialog.*
import kotlinx.android.synthetic.main.note_content_fragment_layout.*

class NoteContentFragmentKt : Fragment() {
    var noteKt :NoteKt? = null
    private var noteViewModelKt: NoteViewModelKt? = null
    protected val compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteViewModelKt = ViewModelProviders.of(this).get(NoteViewModelKt::class.java)
        var dataBaseInstance = NoteDataBaseKt.getDatabaseInstance(requireContext())
        noteViewModelKt?.setInstanceOfDb(dataBaseInstance)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_content_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bundle: Bundle? = this.arguments
        if (bundle!=null){
             noteKt = bundle.getSerializable("noteItem") as NoteKt
            Log.i("NOTEEEEEE", noteKt.toString())
            Log.i("Heading", noteKt!!.heading)
            Log.i("Content", noteKt!!.content)
        }

        loadNote()

        edit.setOnClickListener{
            Log.i("EDIT","EDIT")

            var alertDialog = AlertDialog.Builder(activity)
            var inflater = activity!!.layoutInflater
            var  dialogView :View = inflater.inflate(R.layout.content_dialog,null)
            alertDialog.setView(dialogView)
            alertDialog.setCancelable(false)
            var editHeading = dialogView.findViewById<EditText>(R.id.editHeading)
            var editContent = dialogView.findViewById<EditText>(R.id.editContent)
            editHeading.setText(noteKt?.heading)
            editContent.setText(noteKt?.content)

            var save = dialogView.findViewById<Button>(R.id.save)
            var back = dialogView.findViewById<Button>(R.id.back)




            var alert : AlertDialog = alertDialog.create()
            alert.show()

            save.setOnClickListener{
               var editTitle:String = editHeading.text.toString().trim()
                var editBody:String = editContent.text.toString().trim()

                noteKt!!.heading = editTitle
                noteKt!!.content = editBody


                noteViewModelKt?.updateNote(noteKt!!)?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({
                            Log.i("SUCCESS", " EDIT SUCCESSFUL")
                            Toast.makeText(activity, "Note Updated", Toast.LENGTH_LONG).show()
                            val intent = Intent(activity, Main2Activity::class.java)
                            startActivity(intent)
                        }, {
                            Log.i("ERROR", "ERROR")
                            Toast.makeText(activity, " Error!!!  Note not updated", Toast.LENGTH_LONG).show()
                        })?.let {
                            compositeDisposable.add(it)
                        }

            }

            back.setOnClickListener{
                alert.dismiss()
            }
        }
    }

    fun loadNote(){
        heading.text = noteKt!!.heading
        content.text = noteKt!!.content

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }




}