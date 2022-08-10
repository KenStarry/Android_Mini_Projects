package com.example.circularcheckbox.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.circularcheckbox.R
import com.example.circularcheckbox.entities.Notes

class NoteAdapter(
    val context: Context
): ListAdapter<Notes, NoteAdapter.NoteHolder>(diffCallback) {

    val colorArray = intArrayOf(R.color.accent, R.color.purple_700)

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
            CompoundButtonCompat.setButtonTintList(holder.taskTitle, ColorStateList.valueOf(
                ContextCompat.getColor(context, colorArray[1])))
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