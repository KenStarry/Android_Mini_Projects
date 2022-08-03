package com.example.circularcheckbox.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.circularcheckbox.dao.NoteDao
import com.example.circularcheckbox.database.NoteDatabase
import com.example.circularcheckbox.entities.Notes
import com.example.circularcheckbox.utils.subscribeOnBackground

class NoteRepository(application: Application) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Notes>>
    private val db = NoteDatabase.getInstance(application)

    init {
        noteDao = db.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insertTask(note: Notes) {
        subscribeOnBackground {
            noteDao.insert(note)
        }
    }

    fun updateTask(note: Notes) {
        subscribeOnBackground {
            noteDao.update(note)
        }
    }

    fun deleteTask(note: Notes) {
        subscribeOnBackground {
            noteDao.delete(note)
        }
    }

    fun getAllTasks(): LiveData<List<Notes>> {
        return allNotes
    }
}














