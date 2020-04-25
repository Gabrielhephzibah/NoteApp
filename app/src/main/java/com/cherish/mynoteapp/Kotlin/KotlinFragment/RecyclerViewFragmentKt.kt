package com.cherish.mynoteapp.Kotlin.KotlinFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cherish.mynoteapp.Fragment.RecyclerViewFragment
import com.cherish.mynoteapp.Kotlin.DatabaseKt.NoteDataBaseKt
import com.cherish.mynoteapp.Kotlin.Main2Activity
import com.cherish.mynoteapp.Kotlin.NoteAdapterKt
import com.cherish.mynoteapp.Kotlin.ViewModelKotlin.NoteViewModelKt
import com.cherish.mynoteapp.Kotlin.entityKotlin.NoteKt
import com.cherish.mynoteapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recycler_view_fragment.*

class RecyclerViewFragmentKt :Fragment() {

    lateinit var noteViewModelKt: NoteViewModelKt
    protected val compositeDisposable = CompositeDisposable()


    fun newInstance(): RecyclerViewFragment {
        return RecyclerViewFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteViewModelKt = ViewModelProviders.of(this).get(NoteViewModelKt::class.java)
        var dataBaseInstance = NoteDataBaseKt.getDatabaseInstance(requireContext())
        noteViewModelKt?.setInstanceOfDb(dataBaseInstance)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerVieww : RecyclerView = view.findViewById(R.id.recyclerView)as RecyclerView

        recyclerVieww.layoutManager = LinearLayoutManager(activity)

        noteViewModelKt?.getAllNote()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    Log.i("Note", it.toString())
                  var  myAdapterKt = NoteAdapterKt(requireActivity(), it)
                  recyclerVieww.adapter = myAdapterKt
                }, {
                    Log.i("ERROR", "ERROR")
                    Toast.makeText(activity, " Error!!!  Cannot Save note", Toast.LENGTH_LONG).show()
                })?.let {
                    compositeDisposable.add(it)
                }

  }

}