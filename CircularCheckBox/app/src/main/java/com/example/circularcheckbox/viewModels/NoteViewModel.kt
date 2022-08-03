package com.example.circularcheckbox.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.circularcheckbox.entities.Notes
import com.example.circularcheckbox.repositories.NoteRepository

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val repository = NoteRepository(application)
    private val allTasks = repository.getAllTasks()

    fun modelInsertTask(note: Notes) {
        repository.insertTask(note)
    }

    fun modelUpdateTask(note: Notes) {
        repository.updateTask(note)
    }

    fun modelDeleteTask(note: Notes) {
        repository.deleteTask(note)
    }

    fun modelGetAllTasks(): LiveData<List<Notes>> {
        return allTasks
    }

    fun modelGetAllCheckedTasks(status: Boolean): LiveData<List<Notes>> {
        return repository.getAllCheckedTasks(status)
    }
}