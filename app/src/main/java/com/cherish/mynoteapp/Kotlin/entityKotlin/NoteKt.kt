package com.cherish.mynoteapp.Kotlin.entityKotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class NoteKt(@PrimaryKey(autoGenerate = true) var id: Int, @ColumnInfo(name = "contentkt") var content: String?, @ColumnInfo(name = "headingkt") var heading: String? = null) : Serializable {


    override fun toString(): String {
        return "NoteKt(id=$id, content=$content, heading=$heading)"
    }


}