package com.example.circularcheckbox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.circularcheckbox.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.bottom_sheet_layout, container, false)



        return view
    }
}






















