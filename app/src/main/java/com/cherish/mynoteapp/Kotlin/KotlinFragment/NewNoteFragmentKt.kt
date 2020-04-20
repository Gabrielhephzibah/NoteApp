package com.cherish.mynoteapp.Kotlin.KotlinFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.cherish.mynoteapp.R

class NewNoteFragmentKt : Fragment() {
  lateinit  var editText :EditText
    lateinit var button: Button





    companion object {
        fun newInstance(): NewNoteFragmentKt {
            return NewNoteFragmentKt()

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.new_note_fragment_layout, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}