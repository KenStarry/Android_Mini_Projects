package com.example.mvvmintro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mvvmintro.view_models.NoteViewModel;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Ask Android for an existing viewModel
    }
}