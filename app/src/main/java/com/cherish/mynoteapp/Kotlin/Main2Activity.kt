package com.cherish.mynoteapp.Kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.cherish.mynoteapp.Kotlin.KotlinFragment.NewNoteFragmentKt
import com.cherish.mynoteapp.Kotlin.KotlinFragment.NoteContentFragmentKt
import com.cherish.mynoteapp.Kotlin.KotlinFragment.RecyclerViewFragmentKt
import kotlinx.android.synthetic.main.activity_main2.*
import com.cherish.mynoteapp.R
import com.cherish.mynoteapp.R.id.noteContentLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Main2Activity : AppCompatActivity() {
    lateinit var  factory : ViewModelProvider.AndroidViewModelFactory
  private  val  newNoteFragmentKt = NewNoteFragmentKt()
    private  val recyclerViewFragmentKt = RecyclerViewFragmentKt()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        val fragmentma  = supportFragmentManager
        val transaction = fragmentma.beginTransaction()
        transaction.replace(R.id.recyclerLayout,recyclerViewFragmentKt)
        transaction.commit()

        fab.setOnClickListener {
            if (newNoteLayout.visibility == View.GONE) {
                newNoteLayout.visibility = View.VISIBLE
                recyclerLayout.visibility = View.GONE
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.newNoteLayout, newNoteFragmentKt)
                fragmentTransaction.commit()
            }else if (newNoteLayout.visibility == View.VISIBLE){
                newNoteLayout.visibility = View.GONE
                recyclerLayout.visibility = View.VISIBLE
            }

        }


    }
}
