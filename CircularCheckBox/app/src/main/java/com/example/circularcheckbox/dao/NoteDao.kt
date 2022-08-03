package com.example.circularcheckbox.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.circularcheckbox.entities.Notes

@Dao
interface NoteDao {

    @Insert
    fun insert(notes: Notes)

    @Update
    fun update(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM notes WHERE note_checked = :status")
    fun getAllCheckedNotes(status: Boolean): LiveData<List<Notes>>

}
























