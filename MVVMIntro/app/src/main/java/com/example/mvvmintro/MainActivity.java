package com.example.mvvmintro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mvvmintro.adapters.NoteAdapter;
import com.example.mvvmintro.entities.Note;
import com.example.mvvmintro.interfaces.OnItemClickListener;
import com.example.mvvmintro.view_models.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 1;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();

                            if (data != null) {

                                //  If we are editing the note or just making a new note
                                if (data.hasExtra(AddEditNoteActivity.EXTRA_ID)) {

                                    String title_extra = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                                    String desc_extra = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                                    int priority_extra = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0);
                                    int id_extra = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

                                    if (id_extra == -1) {
                                        Toast.makeText(MainActivity.this, "note couldn't be updated!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    Note note = new Note(title_extra, desc_extra, priority_extra);
                                    note.setId(id_extra);
                                    noteViewModel.update(note);

                                    Toast.makeText(MainActivity.this, "note updated!", Toast.LENGTH_SHORT).show();

                                } else {

                                    String title_extra = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                                    String desc_extra = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
                                    int priority_extra = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 0);

                                    Note note = new Note(title_extra, desc_extra, priority_extra);
                                    noteViewModel.insert(note);

                                    Toast.makeText(MainActivity.this, "note saved!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                //  We use startActivity for result because we are only getting a result from our activity
                intentActivityResultLauncher.launch(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //  Ask Android for an existing viewModel
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //  Update our recycler view
                adapter.submitList(notes);
            }
        });

        //  Implementing swipe functionality
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

                //  now you can use this note that has been passed from the interface
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());

                intentActivityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteAllMenu:
                noteViewModel.deleteAllNotes();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}







