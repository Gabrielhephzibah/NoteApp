package com.cherish.mynoteapp.Kotlin.entityKotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class NoteKt : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    @ColumnInfo(name = "contentkt")
    var content: String? = null


    @ColumnInfo(name = "headingkt")
    var heading: String? = null


    override fun toString(): String {
        return "NoteKt(id=$id, content=$content, heading=$heading)"
    }


}