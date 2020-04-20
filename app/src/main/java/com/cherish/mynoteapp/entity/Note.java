package com.cherish.mynoteapp.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "content")
    private  String content;

    @ColumnInfo(name = "heading")
    private String heading;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", heading='" + heading + '\'' +
                '}';
    }


}
