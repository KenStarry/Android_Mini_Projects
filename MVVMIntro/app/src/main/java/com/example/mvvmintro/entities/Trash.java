package com.example.mvvmintro.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trash_table")
public class Trash {

    @PrimaryKey(autoGenerate = true)
    private int trash_id;

    private String trash_title;
    private String trash_description;
    private int trash_priority;

    public Trash(String trash_title, String trash_description, int trash_priority) {
        this.trash_title = trash_title;
        this.trash_description = trash_description;
        this.trash_priority = trash_priority;
    }

    public void setTrash_id(int trash_id) {
        this.trash_id = trash_id;
    }

    public int getTrash_id() {
        return trash_id;
    }

    public String getTrash_title() {
        return trash_title;
    }

    public String getTrash_description() {
        return trash_description;
    }

    public int getTrash_priority() {
        return trash_priority;
    }
}
