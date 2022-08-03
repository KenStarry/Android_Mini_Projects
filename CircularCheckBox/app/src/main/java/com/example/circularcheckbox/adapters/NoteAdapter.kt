package com.example.circularcheckbox.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.circularcheckbox.R
import com.example.circularcheckbox.entities.Notes

class NoteAdapter: ListAdapter<Notes, NoteAdapter.NoteHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {

        return NoteHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.todo_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

        with(getItem(position)) {
            holder.taskTitle.text = noteTitle
        }
    }

    fun getNoteAt(position: Int) = getItem(position)

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {

        var taskTitle: CheckBox = item.findViewById(R.id.checkbox)

    }
}

private val diffCallback = object : DiffUtil.ItemCallback<Notes>() {

    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.noteTitle == newItem.noteTitle
    }
}