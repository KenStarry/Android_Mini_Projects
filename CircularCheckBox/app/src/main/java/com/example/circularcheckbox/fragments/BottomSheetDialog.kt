package com.example.circularcheckbox.fragments

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.example.circularcheckbox.R
import com.example.circularcheckbox.adapters.NoteAdapter
import com.example.circularcheckbox.entities.Notes
import com.example.circularcheckbox.viewModels.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var vm: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        showSoftKeyboard(view)

        vm = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        adapter = NoteAdapter()

        var inputTask: EditText = view.findViewById(R.id.newTaskInput)
        val saveBtn: ImageView = view.findViewById(R.id.sendBtn)

        saveBtn.setOnClickListener {

            if (inputTask.text.toString().trim() != "") {

                vm.modelInsertTask(Notes(inputTask.text.toString().trim(), "Added a new task", true))

                vm.modelGetAllTasks().observe(requireActivity()) {
                    adapter.submitList(it)
                }

                inputTask.setText("")
                inputTask.clearFocus()

                val behaviour = BottomSheetBehavior.from(requireView().parent as View)
                behaviour.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }

        return view
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}






















