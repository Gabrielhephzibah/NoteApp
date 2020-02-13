package com.cherish.mynoteapp.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteItem {

    @PrimaryKey
    @NonNull
    private  String heading;


    private  String content;

    public NoteItem(String heading, String content) {
        this.heading = heading;
        this.content = content;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }











}
