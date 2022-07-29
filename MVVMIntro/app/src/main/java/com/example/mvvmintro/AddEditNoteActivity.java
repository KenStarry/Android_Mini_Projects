package com.example.mvvmintro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class AddEditNoteActivity extends AppCompatActivity {

    //  Intent extras constants
    public static final String EXTRA_TITLE = "com.example.mvvmintro.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.mvvmintro.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.mvvmintro.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.example.mvvmintro.EXTRA_ID";

    private EditText editTextTitle, editTextDesc;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edittext_title);
        editTextDesc = findViewById(R.id.edittext_description);
        numberPicker = findViewById(R.id.numberPickerPriority);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }

    private void saveNote() {

        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();
        int priority = numberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        //  Set the result as OK
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.add_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveMenu:
                saveNote();
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}