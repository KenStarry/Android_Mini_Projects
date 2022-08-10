package com.example.circularcheckbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.circularcheckbox.adapters.NoteAdapter
import com.example.circularcheckbox.fragments.BottomSheetDialog
import com.example.circularcheckbox.viewModels.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter
    private lateinit var adapterChecked: NoteAdapter
    private lateinit var fabBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.modelGetAllTasks().observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.modelGetAllCheckedTasks(true).observe(this) {
            adapterChecked.submitList(it)
        }

        fabBtn = findViewById(R.id.fab)
        fabBtn.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, "NewTaskDialog")
        }

    }

    private fun setupRecyclerView() {

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val recyclerViewChecked: RecyclerView = findViewById(R.id.recyclerViewChecked)
        adapter = NoteAdapter(this)
        adapterChecked = NoteAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerViewChecked.layoutManager = LinearLayoutManager(this)
        recyclerViewChecked.adapter = adapterChecked
    }
}


























